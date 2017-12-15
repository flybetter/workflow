package com.calix.bseries.gui.panels.layout;

/**
 * @author azhang
 * Date: JUne 8, 2007
 * Time: 8:08:59 PM
 */

import com.calix.bseries.gui.BseriesTopologyGraphComponent;
import com.calix.ems.EMSInit;
import com.calix.ems.gui.TopologyGraphComponent;
import com.calix.ems.gui.panels.layout.GmapBrowserPanel;
import com.calix.ems.gui.panels.layout.NetworkEntityLink;
import com.calix.ems.gui.panels.layout.SimpleNetworkEntity;
import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRegion;
import com.calix.ems.model.EMSRoot;
import com.calix.system.common.constants.DomainValues;
import com.calix.system.gui.components.menu.CalixMenuMgr;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.tlv.TLVUtils;
import com.calix.system.gui.util.AidUtils;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.*;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class BseriesChassisGmapBrowserPanel extends GmapBrowserPanel
{
    private CalixB6Chassis m_curChassis = null;    
    private ArrayList<BaseEMSDevice> m_devices = null;    
    private HashMap<String, NamedOEgc> m_entityGeoCodeMap = null;//(keystr,namedOEgc) used for fill in map link data.
    protected BseriesTopologyGraphComponent m_graphWidget = null;
    /*AbstractAction m_expandNtwkAction = null;
    AbstractAction m_shrinkNtwkAction = null;*/
    public static final String ATTR_PROFILE = "profile";
    public static final String ATTR_DEVICENAME = "DeviceName";
    public static String ATTR_MODELTYPE ="Model";


    public BseriesChassisGmapBrowserPanel(BseriesTopologyGraphComponent graphWidget) {
        super();        
        m_devices = new ArrayList<BaseEMSDevice>();        
        m_entityGeoCodeMap = new HashMap<String,NamedOEgc>();
        m_graphWidget = graphWidget;
    }

    /**
     * Setup the GMap related objects.
     * This is the main processing method when user navigates to/refreshes the page
     *
     * @param objects
     */
    @Override
    public void setupObjects(Collection<?> objects) {
        if(objects==null || objects.size()<=0) return;
        m_ompi = null;
        networkData.reset();//clear data reload.
        loadData((CalixB6Chassis)objects.toArray()[0]);
        m_graphWidget.setDatabase(getPanelController().getDatabase());
        //loadNetworks();
    }

    /**
     * Find corresponding NamedOEgc by SimpleNetworkEntity sne.
     * return null if not found.
     *
     * @param sne
     */
    @Override
    protected NamedOEgc findNOEgcBySNE(SimpleNetworkEntity sne) {
        NamedOEgc ngc = null;
        String key = assembleEgcKeyStrFromSne(sne);
            if(key!=null) ngc = m_entityGeoCodeMap.get(key);
        return ngc;
    }

    /**
     * Assemble  EntityGeoCode Key String from mapdata bean sne.
     *
     * @param sne
     * @return
     */
    @Override
    protected String assembleEgcKeyStrFromSne(SimpleNetworkEntity sne) {
        String ret = null;
        if(sne.getEntityType().equalsIgnoreCase(S_DT_REG) || sne.getEntityType().equalsIgnoreCase(S_DT_NG) ) {
            ret = S_REG_PREFIX +  sne.getName();
        }
        else { // All others assumed as BaseCMSNetwork type or Node, so use NTWK Prefix is correct.
            ret = S_NTWK_PREFIX + sne.getName();
        }
    	if (ret.indexOf(AEONT_LABEL_SEPARATER) > -1) {
    		ret = ret.substring(0, ret.indexOf(AEONT_LABEL_SEPARATER));
    	}
        return ret;
    }


    protected String getRegionKeyStr() {
        return grabEmsAidKeyStr(m_curChassis.getIdentityValue());
    }

    @Override
    public IValue getMapPositionInfoIdentity() {
        try {
            IValueType idType = S_RT_MapPositionInfo.getIdentityAttribute().getFirstType();
            IValue ntwkComponent = ((TreeValue) (m_curChassis.getIdentityValue())).getAllComponents()[0];
            IValueType type2 = OSStringType.TYPE;
            String regname = getRegionKeyStr();
            IValue component2 = type2.convertFrom(regname, null);
            IValue[] rootIdentity = {ntwkComponent, component2};
            IValue rootAid = idType.convertFrom(rootIdentity, null);
            return rootAid;
        } catch (Exception ex) {
            Code.warning(ex);
        }
        return null;
    }
    protected void clear() {        
        m_devices.clear();       
        m_entityGeoCodeMap.clear();
    }

    /**
     * Fill in NodeIdNameMap, and fill in Node List for Map.
     */
    void loadData( CalixB6Chassis chassis ) {
          try {
              clear();
              EMSRoot root = EMSInit.getReadonlyEMSRoot(getDatabase());
              m_curChassis = (CalixB6Chassis)getDatabase().load((RecordType)chassis.getType(),chassis.getIdentityValue(), IDatabase.ReadOnly);
              m_pnObject = m_curChassis;
              //load MPI info
              loadSetMapPosInfoFromSvr(getRegionKeyStr());
              //load egc
              Collection<? extends OSBaseObject> egcs = loadEntityGeoCodeList();
              
              for(Iterator<BaseEMSDevice> networks = m_curChassis.getNetworks(root).iterator();networks.hasNext();) {
                  BaseEMSDevice cnetwork = networks.next();
                  cnetwork = (BaseEMSDevice)getDatabase().load((RecordType)cnetwork.getType(),cnetwork.getIdentityValue());
                  m_devices.add(cnetwork);
                  if (needExpand2Nodes(cnetwork)) {
                      //cnetwork.getNodes(). reflection style.
                      @SuppressWarnings({ "unchecked", "rawtypes" })
                      Collection<OSBaseObject> nodes = (Collection) cnetwork.getClass().getMethod("getNodes", new Class[]{}).invoke(cnetwork, (Object) null);
                      if (nodes != null) {
                          Iterator<OSBaseObject> iter = nodes.iterator();
                          while (iter.hasNext()) {
                              OSBaseObject tmp_node = iter.next();
                              setupEgc(tmp_node, egcs);
                          }
                      }
                  } else {
                      setupEgc(cnetwork, egcs);
                  }
              }
             

              Collection<OSBaseObject> slinks = m_curChassis.getAttributeValueCollection("Static Links");
              if (slinks != null) {
                  for (Iterator<OSBaseObject> sls = slinks.iterator(); sls.hasNext();) {
                      OSBaseObject slink = sls.next();
                      NetworkEntityLink nelk = NELinkFromStaticLink(slink);
                      if (nelk != null) networkData.getNeLinkList().add(nelk);
                  }
              }

          } catch(Exception ex) {
                  Code.warning(ex);
          }
        }

    /**
     * Get NELinkList from CalixLink obj
     * If link already converted a similar one, return null(get around C7Links return 2 times issue).
     *
     * @param obj Must be CalixLink object
     */
    protected NetworkEntityLink NELinkFromStaticLink(OSBaseObject obj) {
        NetworkEntityLink nel = null;
        try {
            if (obj != null && obj.getTypeName().equalsIgnoreCase(S_NESTATICLINK)) {
                nel = new NetworkEntityLink();
                String lkname = "";
                boolean isUserCreated =(obj.getAttributeValue("IsUserCreated").convertTo(Integer.class,null)).intValue()>0;
                if( isUserCreated ) {
                    lkname = AidUtils.getTL1AddrString(obj.getAttributeValue("ID"));
                }
                nel.setName(lkname );
                String end1Name = (obj.getAttributeValue("Ne1Name").convertTo(String.class, null)).substring(S_NTWK_PREFIX.length());
                String end2Name = (obj.getAttributeValue("Ne2Name").convertTo(String.class, null)).substring(S_NTWK_PREFIX.length());
                nel.setNe1(end1Name);
                nel.setNe2(end2Name);
                nel.setNe1PortDesc(AidUtils.getTL1AddrString(obj.getAttributeValue("Ne1Port")));
                nel.setNe2PortDesc(AidUtils.getTL1AddrString(obj.getAttributeValue("Ne2Port")));
                if (m_expandedNetwork2Nodes) {
                    //When in expand mode, if PortDesc follow Nn-n-nnn pattern, and if found EGC by added NodeId,
                    //Then it is a C7Network being expanded, so link to node level. rather than Network Level.

                    //check and update ne1 first.
                    NamedOEgc ngc = null;
                    String testKey = assembleC7NodeEGCKeyByNEPortDesc(nel.getNe1(), nel.getNe1PortDesc());
                    if (testKey != null) ngc = m_entityGeoCodeMap.get(testKey);
                    if (ngc != null) nel.setNe1(testKey.substring(S_NTWK_PREFIX.length()));
                    //then check and update ne2 .
                    ngc=null;
                    testKey = assembleC7NodeEGCKeyByNEPortDesc(nel.getNe2(), nel.getNe2PortDesc());
                    if (testKey != null) ngc = m_entityGeoCodeMap.get(testKey);
                    if (ngc != null) nel.setNe2(testKey.substring(S_NTWK_PREFIX.length()));
                }
            }
        } catch (Exception e) {
            Code.warning(e);
        }
        return nel;
    }

    /**
     * assemble EGC Key based on NE name( SNE type, no prefix), and PortDesc string.
     * If PortDesc follow C7 pattern like N1-1-1, then return assembled C7Node EGC Key Str.
     * Otherwise, return null.
     *
     * @param sneName
     * @param portDesc
     * @return
     */
    private String assembleC7NodeEGCKeyByNEPortDesc(String sneName, String portDesc) {
        String ret = null;
        if (isPortDescFollowC7Style(portDesc)) {
            ret = S_NTWK_PREFIX + sneName + ID_SEPERATOR +
                    portDesc.substring(0, portDesc.indexOf(ID_SEPERATOR));
        }
        return ret;
    }

    private boolean isPortDescFollowC7Style(String portDesc) {
        return portDesc!=null && portDesc.matches("N\\d+-\\d+-.*");
    }

    private boolean needExpand2Nodes(BaseEMSDevice cnetwork) throws MappingException {
        return m_expandedNetwork2Nodes && cnetwork.getType().getTypeName().equalsIgnoreCase(S_EMSNetwork) &&  isConnectedC7(cnetwork);
    }

    private boolean isConnectedC7(BaseEMSDevice cnetwork) throws MappingException {
        return !(cnetwork.getAttributeValue("ConnectionState").convertTo(String.class,"TL1")).equalsIgnoreCase("Disconnected");
    }

    private void setupEgc(OSBaseObject cregion, Collection<? extends OSBaseObject> egcs) {
        try {
            NamedOEgc ngc = assembleNoEgcFromEGCs(cregion, egcs);
            if (ngc.getOgc() != null) {
                ngc.getOgc().setPersistent(getDatabase()); //for existing GeoCode
            }
            m_entityGeoCodeMap.put(assembleGCKey(cregion), ngc);
            networkData.getNeList().add(createSNE(cregion, ngc));
        } catch (Exception ex) {
            Code.warning(ex);
        }
    }

    private NamedOEgc assembleNoEgcFromEGCs(OSBaseObject entity, Collection<? extends OSBaseObject> gcs) {
        NamedOEgc ngc = null;
        if (entity != null) {
            ngc = new NamedOEgc(assembleNGCName(entity));
            OSBaseObject ogc = findOGeoCode(assembleGCKey(entity), gcs);
            ngc.setOgc(ogc);
        }
        return ngc;
    }

    /**
     * assemble SNE name in marker list. equal to marker.name
     * @param entity
     * @return
     */
    private String assembleNGCName(OSBaseObject entity) {
        String ret = null;
       if(entity.getTypeName().equalsIgnoreCase(S_CALIXNODE) ) {//assemble key like ORION-N1.
           ret = assembleNtwkNodeKeyStr(entity).substring(S_NTWK_PREFIX.length());
       }  else {
         ret = grabEmsAidKeyStr(entity.getIdentityValue());
        if(ret!=null && ret.indexOf(ID_SEPERATOR)>0 ) {
            ret = ret.substring(ret.indexOf(ID_SEPERATOR) +1);
        }
       }
        return ret;
    }

    private String assembleNtwkNodeKeyStr(OSBaseObject entity) {
        String ret;
        ret = TLVUtils.extractNetworkComponentString(entity.getIdentityValue()) ;
        ret = ret + ID_SEPERATOR + S_NODE_PREFIX + grabEmsAidKeyStr(entity.getIdentityValue());
        return ret;
    }

    private String assembleGCKey(OSBaseObject entity) {
        if(entity.getTypeName().equalsIgnoreCase(S_CALIXNODE) ) {//assemble key like ORION-N1.
            return assembleNtwkNodeKeyStr(entity);
        }  else {
            return grabEmsAidKeyStr(entity.getIdentityValue());
        }
    }

    @Override
    protected String assembleParentGroupName() {
        String network = grabEmsAidKeyStr(m_pnObject.getIdentityValue());
        return network;
    }

    /**
     * Create SimpleNetworkEntity for Node. based on calixNode and Geocode OSBaseObject
     *
     * @param obj
     * @param ngc
     * @return
     * @throws MappingException
     */
    @Override
    protected SimpleNetworkEntity createSNE(OSBaseObject obj, NamedOEgc ngc) throws MappingException {
        SimpleNetworkEntity sne = new SimpleNetworkEntity();
        if (obj instanceof BaseEMSDevice) {
            sne.setEntityType(S_DT_NTWK);
            if (obj.getTypeName().equalsIgnoreCase(S_B6) ) {
                OSBaseObject profileObj = EMSInit.getReadonlyEMSRoot(getPanelController().getDatabase()).getInternalSNMPProfile("B6");
                String dvcName =profileObj.getAttributeValue(ATTR_DEVICENAME, String.class, DomainValues.DOMAIN_TL1);
                sne.setEntityType(dvcName);
                String dvcType =obj.getAttributeValue("DeviceType",String.class,DomainValues.DOMAIN_TL1);
                if(dvcType!=null && dvcType.startsWith("B6")) sne.setEntityType(dvcType);
            }
            //sne.setEqptType(node.getMountType());
           //todo:?? Add specific Device type?
        }
        sne.setName(assembleNGCName(obj));
        sne.setName(ngc.getName());
        
        sne.setLat(S_UNASSIGNED_LMT);
        sne.setLng(S_UNASSIGNED_LMT);
        if (ngc.getOgc() != null) {
            double lat = Double.parseDouble(ngc.getOgc().getAttributeValue("LatStr").convertTo(String.class, null));
            double lng = Double.parseDouble(ngc.getOgc().getAttributeValue("LngStr").convertTo(String.class, null));
            sne.setLat(lat);
            sne.setLng(lng);
            String addr = (null==ngc.getOgc().getAttributeValue("AddrStr"))? null:
                   (String)ngc.getOgc().getAttributeValue("AddrStr").convertTo(String.class, null);
            sne.setAddr(addr);
            String status = (null==ngc.getOgc().getAttributeValue("Status"))?null:
                   (String)ngc.getOgc().getAttributeValue("Status").convertTo(String.class, DomainValues.DOMAIN_GUI);
            sne.setStatus(status);
        }
        assignAlarmCounts(obj, sne);
        return sne;
    }
     @Override
     protected boolean FillInExtraDataFromFields(){
       m_expandedNetwork2Nodes=false;
       return removeDeletedStaticLinks();
    }

    public boolean removeDeletedStaticLinks() {
        boolean ret = false;
        try {
            Collection<IValue> slinks = m_curChassis.getAttributeValueCollection("Static Links");
            if (slinks != null) {
                for (Iterator<IValue> sls = slinks.iterator(); sls.hasNext();) {
                    OSBaseObject slink = (OSBaseObject) sls.next();
                    boolean isUserCreated = (slink.getAttributeValue("IsUserCreated").convertTo(Integer.class, null)).intValue() > 0;
                    String sid = AidUtils.getTL1AddrString(slink.getAttributeValue("ID"));
                    if (isUserCreated) {
                        boolean deleted = true;
                        for (NetworkEntityLink nel : networkData.getNeLinkList()) {
                            if (sid.equalsIgnoreCase(nel.getName())) {
                                deleted = false;
                                break;
                            }
                        }
                        if (deleted) {
                            getDatabase().remove(slink);
                        }
                    }
                }
                ret = true;
            }
        } catch (Exception ex) {
            Code.warning(ex);
        }
        return ret;
    }

    @Override
    public ArrayList<JMenuItem> buildContextCreateMenuItems() {
        ArrayList<JMenuItem> menuItems = super.buildContextCreateMenuItems();
        menuItems.addAll(m_graphWidget.buildContextCreateMenuItems());
        return menuItems;
    }

    @Override
    public ArrayList<JMenuItem> buildDeleteMenuItems() {
        ArrayList<JMenuItem> menuItems = super.buildDeleteMenuItems();
        menuItems.addAll(m_graphWidget.buildDeleteMenuItems());
        return menuItems;
    }

    @Override
    public ArrayList<JMenuItem> buildActionMenuItems() {
        ArrayList<JMenuItem> menuItems = super.buildActionMenuItems();
        /*// setup expand/shrink network to Nodes action
        if (m_curChassis != null) {
            m_expandNtwkAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (! m_expandedNetwork2Nodes) {
                        m_isMenuAct = true;
                        m_expandedNetwork2Nodes = true;
                        m_expandNtwkAction.setEnabled(false);
                        m_shrinkNtwkAction.setEnabled(true);
                        resetDataRedrawMapOverlay();
                        m_isMenuAct = false;
                    }
                }
            };
            m_expandNtwkAction.putValue(Action.NAME, "Expand");
            m_expandNtwkAction.putValue(Action.ACTION_COMMAND_KEY, "expand");

            m_shrinkNtwkAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (m_expandedNetwork2Nodes) {
                        m_isMenuAct = true;
                        m_expandedNetwork2Nodes = false;
                        m_expandNtwkAction.setEnabled(true);
                        m_shrinkNtwkAction.setEnabled(false);
                        resetDataRedrawMapOverlay();
                        m_isMenuAct = false;
                    }
                }
            };
            m_shrinkNtwkAction.putValue(Action.NAME, "Collapse");
            m_shrinkNtwkAction.putValue(Action.ACTION_COMMAND_KEY, "collapse");
            m_expandNtwkAction.setEnabled(!m_expandedNetwork2Nodes);
            m_shrinkNtwkAction.setEnabled(m_expandedNetwork2Nodes);
        }
*/
        if (CalixMenuMgr.haveSelections(this)) {
            menuItems.addAll(m_graphWidget.buildActionMenuItems());
            /*if (m_curChassis != null) {
                menuItems.add(new JMenuItem(m_expandNtwkAction));
                menuItems.add(new JMenuItem(m_shrinkNtwkAction));
            }*/
        }

        return menuItems;
    }

}
