/*
 * $Id$
 */

package com.calix.bseries.server.dbmodel;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.apache.log4j.Logger;

import com.calix.bseries.server.ana.ANAService;
import com.calix.ems.core.CMSobjectRequestSignal;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSDatabaseException;
import com.calix.ems.util.TLVHelper;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.server.dbmodel.*;

/**
 * Class B6NewProxySettings.
 * 
 * @version $Revision$ $Date$
 */
public class B6NewProxySettings extends BaseEMSObject {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field m_B6NewProxySettingsAID
	 */
	public com.calix.system.server.dbmodel.ICMSAid m_B6NewProxySettingsAID;

	/**
	 * PortA
	 */
	public String m_PortA;

	/**
	 * PortB
	 */
	public String m_PortB;

	/**
	 * ProxyServerA
	 */
	public String m_ProxyServerA;

	/**
	 * ProxyServerB
	 */
	public String m_ProxyServerB;

	/**
	 * StatusA
	 */
	public String m_StatusA;

	/**
	 * Status2
	 */
	public String m_StatusB;

	/**
	 * Field TYPE_NAME
	 */
	public static String TYPE_NAME = "B6NewProxySettings";

	/**
	 * new int[]{empty networkid, B6NewProxySettings}
	 */
	public static int[] m_hierarchy = new int[] { 0, 13656 };

	/**
	 * Field flowID
	 */
	public static final int flowID = 1;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public B6NewProxySettings() {
		super();
	} // -- com.calix.bseries.server.dbmodel.B6NewProxySettings()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method copyFields
	 * 
	 * @param obj1
	 */
	protected void copyFields(CMSObject obj1) {
		if (obj1 instanceof B6NewProxySettings) {
			super.copyFields(obj1);
			B6NewProxySettings obj = (B6NewProxySettings) obj1;
			setB6NewProxySettingsAID((com.calix.system.server.dbmodel.ICMSAid) Helper
					.copy(obj.getB6NewProxySettingsAID()));
			setPortA((String) Helper.copy(obj.getPortA()));
			setPortB((String) Helper.copy(obj.getPortB()));
			setProxyServerA((String) Helper.copy(obj.getProxyServerA()));
			setProxyServerB((String) Helper.copy(obj.getProxyServerB()));
			setStatusA((String) Helper.copy(obj.getStatusA()));
			setStatusB((String) Helper.copy(obj.getStatusB()));
			setIdentityValue((com.calix.system.server.dbmodel.ICMSAid) Helper
					.copy(obj.getIdentityValue()));
		}
	} // -- void copyFields(CMSObject)

	/**
	 * Method getB6NewProxySettingsAID
	 */
	public com.calix.system.server.dbmodel.ICMSAid getB6NewProxySettingsAID() {
		return this.m_B6NewProxySettingsAID;
	} // -- com.calix.system.server.dbmodel.ICMSAid getB6NewProxySettingsAID()

	/**
	 * Method getHierarchy
	 */
	public int[] getHierarchy() {
		return m_hierarchy;
	} // -- int[] getHierarchy()

	/**
	 * Method getIdentityValue
	 */
	public com.calix.system.server.dbmodel.ICMSAid getIdentityValue() {
		return this.m_B6NewProxySettingsAID;
	} // -- com.calix.system.server.dbmodel.ICMSAid getIdentityValue()

	/**
	 * Method getPortA
	 */
	public String getPortA() {
		return this.m_PortA;
	} // -- String getPortA()

	/**
	 * Method getPortB
	 */
	public String getPortB() {
		return this.m_PortB;
	} // -- String getPortB()

	/**
	 * Method getProxyServerA
	 */
	public String getProxyServerA() {
		return this.m_ProxyServerA;
	} // -- String getProxyServerA()

	/**
	 * Method getProxyServerB
	 */
	public String getProxyServerB() {
		return this.m_ProxyServerB;
	} // -- String getProxyServerB()

	/**
	 * Method getStatusA
	 */
	public String getStatusA() {
		return this.m_StatusA;
	} // -- String getStatusA()

	/**
	 * Method getStatusB
	 */
	public String getStatusB() {
		return this.m_StatusB;
	} // -- String getStatusB()

	/**
	 * Method getTlvType
	 */
	public int getTlvType() {
		return BseriesTlvConstants.B6NewProxySettings;
	} // -- int getTlvType()

	/**
	 * Method getTypeName
	 */
	public String getTypeName() {
		return TYPE_NAME;
	} // -- String getTypeName()

	/**
	 * Method isEMSCacheSupported
	 */
	public boolean isEMSCacheSupported() {
		return true;
	} // -- boolean isEMSCacheSupported()

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
		case 0x355A:
			// More than one attributes with same tlv type!
			// These will be processed in the order they arrive
			if (m_ProxyServerA == null) {
				m_ProxyServerA = TLVHelper.getStringValueOfTLV(tlv);
				return;
			}
			break;
		case 0x355B:
			if (m_PortA == null) {
				m_PortA = TLVHelper.getStringValueOfTLV(tlv);
				return;
			}
			break;
		case 0x355C:
			if (m_StatusA == null) {
				m_StatusA = TLVHelper.getStringValueOfTLV(tlv);
				return;
			}
			break;
		case 0x355D:
			if (m_ProxyServerB == null) {
				m_ProxyServerB = TLVHelper.getStringValueOfTLV(tlv);
				return;
			}
			break;
		case 0x355E:
			if (m_PortB == null) {
				m_PortB = TLVHelper.getStringValueOfTLV(tlv);
				return;
			}
			break;
		case 0x355F:
			if (m_StatusB == null) {
				m_StatusB = TLVHelper.getStringValueOfTLV(tlv);
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
		TLVHelper.addEmbeddedTLV(tlv, 0x355B, m_PortA);
		TLVHelper.addEmbeddedTLV(tlv, 0x355E, m_PortB);
		TLVHelper.addEmbeddedTLV(tlv, 0x355A, m_ProxyServerA);
		TLVHelper.addEmbeddedTLV(tlv, 0x355D, m_ProxyServerB);
		TLVHelper.addEmbeddedTLV(tlv, 0x355C, m_StatusA);
		TLVHelper.addEmbeddedTLV(tlv, 0x355F, m_StatusB);
	} // -- void populateTLVFromAttributes(TLV, SwVersionNo)

	/**
	 * Method setB6NewProxySettingsAID
	 * 
	 * @param B6NewProxySettingsAID
	 */
	public void setB6NewProxySettingsAID(
			com.calix.system.server.dbmodel.ICMSAid B6NewProxySettingsAID) {
		this.m_B6NewProxySettingsAID = B6NewProxySettingsAID;
	} // -- void
		// setB6NewProxySettingsAID(com.calix.system.server.dbmodel.ICMSAid)

	/**
	 * Method setIdentityValue
	 * 
	 * @param B6NewProxySettingsAID
	 */
	public boolean setIdentityValue(
			com.calix.system.server.dbmodel.ICMSAid B6NewProxySettingsAID) {
		this.m_B6NewProxySettingsAID = (com.calix.system.server.dbmodel.ICMSAid) B6NewProxySettingsAID;
		return true;
	} // -- boolean setIdentityValue(com.calix.system.server.dbmodel.ICMSAid)

	/**
	 * Method setPortA
	 * 
	 * @param PortA
	 */
	public void setPortA(String PortA) {
		this.m_PortA = PortA;
	} // -- void setPortA(String)

	/**
	 * Method setPortB
	 * 
	 * @param PortB
	 */
	public void setPortB(String PortB) {
		this.m_PortB = PortB;
	} // -- void setPortB(String)

	/**
	 * Method setProxyServerA
	 * 
	 * @param ProxyServerA
	 */
	public void setProxyServerA(String ProxyServerA) {
		this.m_ProxyServerA = ProxyServerA;
	} // -- void setProxyServerA(String)

	/**
	 * Method setProxyServerB
	 * 
	 * @param ProxyServerB
	 */
	public void setProxyServerB(String ProxyServerB) {
		this.m_ProxyServerB = ProxyServerB;
	} // -- void setProxyServerB(String)

	/**
	 * Method setStatusA
	 * 
	 * @param StatusA
	 */
	public void setStatusA(String StatusA) {
		this.m_StatusA = StatusA;
	} // -- void setStatusA(String)

	/**
	 * Method setStatusB
	 * 
	 * @param StatusB
	 */
	public void setStatusB(String StatusB) {
		this.m_StatusB = StatusB;
	} // -- void setStatusB(String)

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
		if (obj1 instanceof B6NewProxySettings) {
			super.updateFields(obj1);
			B6NewProxySettings obj = (B6NewProxySettings) obj1;
			if (obj.getB6NewProxySettingsAID() != null)
				setB6NewProxySettingsAID((com.calix.system.server.dbmodel.ICMSAid) Helper
						.copy(obj.getB6NewProxySettingsAID()));
			if (obj.getPortA() != null)
				setPortA((String) Helper.copy(obj.getPortA()));
			if (obj.getPortB() != null)
				setPortB((String) Helper.copy(obj.getPortB()));
			if (obj.getProxyServerA() != null)
				setProxyServerA((String) Helper.copy(obj.getProxyServerA()));
			if (obj.getProxyServerB() != null)
				setProxyServerB((String) Helper.copy(obj.getProxyServerB()));
			if (obj.getStatusA() != null)
				setStatusA((String) Helper.copy(obj.getStatusA()));
			if (obj.getStatusB() != null)
				setStatusB((String) Helper.copy(obj.getStatusB()));
			if (obj.getIdentityValue() != null)
				setIdentityValue((com.calix.system.server.dbmodel.ICMSAid) Helper
						.copy(obj.getIdentityValue()));
		}
	} // -- void updateFields(CMSObject)
		// BEGIN CODE

	private static final Logger log = Logger
			.getLogger(B6NewProxySettings.class);

	public void dbUpdate(DbTransaction tx) throws EMSDatabaseException {
		super.dbUpdate(tx);
		final B6NewProxySettings updateData = this;
		Thread updateThread = new Thread(new Runnable() {
			public void run() {
				log.info("Start execute APPLY proxy setting ");
				ANAService.syncToProxy(updateData);
			}
		});
		updateThread.start();
	}
	
	public void dbUpdateANA(DbTransaction tx) throws EMSDatabaseException {
		super.dbUpdate(tx);
	}

	@Override
	public void postRequest(int requestType) {
		super.postRequest(requestType);
		switch (requestType) {

		case CMSobjectRequestSignal.CREATE:
			final B6NewProxySettings createData = this;
			Thread createThread = new Thread(new Runnable() {
				public void run() {
					log.info("Start execute APPLY proxy setting ");
					ANAService.syncToProxy(createData);
				}
			});
			createThread.start();
			break;
//remove update, otherwise it will cause infinite loops. 
//		case CMSobjectRequestSignal.UPDATE:
//			final B6NewProxySettings updateData = this;
//			Thread updateThread = new Thread(new Runnable() {
//				public void run() {
//					log.info("Start execute APPLY proxy setting ");
//					ANAService.syncToProxy(updateData);
//				}
//			});
//			updateThread.start();
//			break;
		}
	}
	// END CODE

}
