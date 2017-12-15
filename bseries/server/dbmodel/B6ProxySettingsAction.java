/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.ANAService;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSException;
import com.calix.ems.server.dbmodel.BaseEMSAction;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6ProxySettingsAction.
 * 
 * @version $Revision$ $Date$
 */
public class B6ProxySettingsAction extends BaseEMSAction {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Action Type
	 */
	public String m_ActionType;

	/**
	 * Field TYPE_NAME
	 */
	public static String TYPE_NAME = "B6ProxySettingsAction";

	/**
	 * Field flowID
	 */
	public static final int flowID = 1;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public B6ProxySettingsAction() {
		super();
	} // -- com.calix.bseries.server.dbmodel.B6ProxySettingsAction()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method copyFields
	 * 
	 * @param obj1
	 */
	protected void copyFields(CMSObject obj1) {
		if (obj1 instanceof B6ProxySettingsAction) {
			super.copyFields(obj1);
			B6ProxySettingsAction obj = (B6ProxySettingsAction) obj1;
			setActionType((String) Helper.copy(obj.getActionType()));
		}
	} // -- void copyFields(CMSObject)

	/**
	 * Method getActionType
	 */
	public String getActionType() {
		return this.m_ActionType;
	} // -- String getActionType()

	/**
	 * Method getTlvType
	 */
	public int getTlvType() {
		return BseriesTlvConstants.B6ProxySettingsAction;
	} // -- int getTlvType()

	/**
	 * Method getTypeName
	 */
	public String getTypeName() {
		return TYPE_NAME;
	} // -- String getTypeName()

	/**
	 * Method populateAttributeFromTLV
	 * 
	 * @param tlv
	 * @param from_version
	 */
	public void populateAttributeFromTLV(TLV tlv, SwVersionNo from_version) {
		if (tlv == null)
			return;
		switch (tlv.getType()) {
		case 0x3557:
			if (m_ActionType == null) {
				m_ActionType = TLVHelper.getStringValueOfTLV(tlv);
				return;
			}
			break;
		}
		super.populateAttributeFromTLV(tlv, from_version);
	} // -- void populateAttributeFromTLV(TLV, SwVersionNo)

	/**
	 * Method populateTLVFromAttributes
	 * 
	 * @param tlv
	 * @param to_version
	 */
	public void populateTLVFromAttributes(TLV tlv, SwVersionNo to_version) {
		if (tlv == null)
			return;
		super.populateTLVFromAttributes(tlv, to_version);
		TLVHelper.addEmbeddedTLV(tlv, 0x3557, m_ActionType);
	} // -- void populateTLVFromAttributes(TLV, SwVersionNo)

	/**
	 * Method setActionType
	 * 
	 * @param ActionType
	 */
	public void setActionType(String ActionType) {
		this.m_ActionType = ActionType;
	} // -- void setActionType(String)

	/**
	 * Method setTypeName
	 * 
	 * @param typeName
	 */
	public boolean setTypeName(String typeName) {
		return false;
	} // -- boolean setTypeName(String)

	/**
	 * Method updateFields
	 * 
	 * @param obj1
	 */
	protected void updateFields(CMSObject obj1) {
		if (obj1 instanceof B6ProxySettingsAction) {
			super.updateFields(obj1);
			B6ProxySettingsAction obj = (B6ProxySettingsAction) obj1;
			if (obj.getActionType() != null)
				setActionType((String) Helper.copy(obj.getActionType()));
		}
	} // -- void updateFields(CMSObject)
		// BEGIN CODE

	private static final Logger log = Logger
			.getLogger(B6ProxySettingsAction.class);

	public void doExecute(DbTransaction tx, Integer nbSessionID)
			throws EMSException {
		super.doExecute(tx, nbSessionID);

		Thread thread = new Thread(new Runnable() {
			public void run() {
				log.info("Start execute SYNCDEVICE actions ");
				ANAService.syncToProxy(null);
			}
		});
		thread.start();
	}
	// END CODE
}
