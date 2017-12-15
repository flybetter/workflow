package com.calix.bseries.gui.visual.application;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import com.calix.ems.model.CalixB6Chassis;
import com.calix.system.gui.constants.GUIResource;
import com.calix.system.gui.lookandfeel.GraphicsMgr;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.util.AidUtils;
import com.calix.system.gui.visual.IView;
import com.calix.system.gui.visual.VUtils;
import com.calix.system.gui.visual.application.DeviceTopologyElement;
import com.calix.system.gui.visual.components.IOrnament;
import com.objectsavvy.base.persistence.OSBaseObject;

public class BseriesTopologyElement extends DeviceTopologyElement{

	public BseriesTopologyElement(BaseEMSDevice pDevice, Image pIcon, String pUserString){
		super(pDevice, pIcon, pUserString);
	}
	
	@Override
    public String getName() {
		if (userObject instanceof CalixB6Chassis){
			return "CHASSIS-" + ((CalixB6Chassis)userObject).getDisplayName();
		}
        return AidUtils.getTL1AddrString(((OSBaseObject) userObject).getIdentityValue());
    }
	
	@Override
    public void postRender(IView view) {
        Graphics2D g = view.getGraphics2D();
        g.addRenderingHints(GraphicsMgr.getRenderingHints());
        if (view.getProperty(GUIResource.VIEW_PROPERTY_ORNAMENTS) != null) {
            for (IOrnament ornament : getOrnaments(view)) {
                ornament.paint(g, rect.x, rect.y, view);
            }
        }
        
        renderName(g, view.getScale());
        
        if (view.getScale() >= 0.33d) {
        	String name = getName();
        	if (!getName().startsWith("CHASSIS-")){
        		name = name.substring(5);
        	}
            Rectangle2D bounds = VUtils.getStringSize(g, name);
            int x = getCenter(view).x - (int) bounds.getWidth() / 2;
            int y = getLocation(view).y + getSize(view).height + (int) bounds.getHeight() + 5;
            VUtils.drawOutlineString(g, name, x, y);
        }
    }
}
