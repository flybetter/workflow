package com.calix.bseries.gui.model.persistence.tlv;

import com.objectsavvy.base.persistence.BaseObjectHandler;
import com.objectsavvy.base.persistence.TransactionContext;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.CacheEngine;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.model.IQueryScope;
import com.objectsavvy.base.util.debug.Code;
import com.calix.system.common.protocol.tlv.BaseTlv;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.gui.database.TLVConnection;
import com.calix.system.gui.tlv.TLVUtils;
import com.calix.system.gui.model.persistence.TLVRangeAllQuery;
import com.calix.system.common.constants.DomainValues;


public class TLVB6CommandActionQuery extends TLVRangeAllQuery {

    public TLVB6CommandActionQuery(BaseObjectHandler pHandler) {
        super(pHandler);
    }

    public TLVB6CommandActionQuery(BaseObjectHandler pHandler, IQueryScope pScope) {
        super(pHandler, pScope);
    }

    protected int getSignalCode(BaseObjectHandler pHandler) {
        return BaseTlv._McsGetSig_;
    }

    protected TLV createObjectDescTLV(TLVConnection conn, TransactionContext pContext) throws PersistenceException, MappingException {


    	
       OSBaseObject queryObj = null;
        RecordType rType = TypeRegistry.getInstance().getRecordType("B6CommandAction");
        try {
            queryObj = rType.newInstance();
            if ((m_scope != null) && (m_scope instanceof TLVB6CommandActionQueryScope)) {
                queryObj.setAttributeValue("CommandType", ((TLVB6CommandActionQueryScope) m_scope).getCommandType());
                queryObj.setAttributeValue("NetworkName", ((TLVB6CommandActionQueryScope) m_scope).getNetworkName());
                queryObj.setAttributeValue("NetworkIP", ((TLVB6CommandActionQueryScope) m_scope).getNetworkIP());
                queryObj.setAttributeValue("Port", ((TLVB6CommandActionQueryScope) m_scope).getPort());
                queryObj.setAttributeValue("Vlan", ((TLVB6CommandActionQueryScope) m_scope).getVlan());

            }
        } catch (Exception ex) {
            Code.warning(ex);
            throw new PersistenceException(ex);
        }

        BaseObjectHandler handler = CacheEngine.getRecordHandler(DomainValues.DOMAIN_TLV, rType);
        IValue[] values = new IValue[handler.getAttributes().length];
        handler.copyInto(queryObj, values);

        TLV objectTLV = new TLV(Short.parseShort(Integer.decode(TypeRegistry.getInstance().getAliasValue("_TlvEMSB6CommandAction_")).toString()), TLVUtils.populateTLVFromObjectFields(values, handler));
        return objectTLV;
                
    }
    

}
