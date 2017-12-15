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


public class TLVB6TemplateQuery extends TLVRangeAllQuery {

    public TLVB6TemplateQuery(BaseObjectHandler pHandler) {
        super(pHandler);
    }

    public TLVB6TemplateQuery(BaseObjectHandler pHandler, IQueryScope pScope) {
        super(pHandler, pScope);
    }

    protected int getSignalCode(BaseObjectHandler pHandler) {
        return BaseTlv._McsGetSig_;
    }

    protected TLV createObjectDescTLV(TLVConnection conn, TransactionContext pContext) throws PersistenceException, MappingException {


    	
       OSBaseObject queryObj = null;
        RecordType rType = TypeRegistry.getInstance().getRecordType("GetB6TemplateResult");
        try {
            queryObj = rType.newInstance();
            if ((m_scope != null) && (m_scope instanceof TLVB6TemplateQueryScope)) {
                queryObj.setAttributeValue("IPAddress1", ((TLVB6TemplateQueryScope) m_scope).getIPAddress1());
                queryObj.setAttributeValue("TemplateSource", ((TLVB6TemplateQueryScope) m_scope).getTemplateSource());
                queryObj.setAttributeValue("TemplateRecordType", ((TLVB6TemplateQueryScope) m_scope).getTemplateRecordType());
                queryObj.setAttributeValue("TemplateId", ((TLVB6TemplateQueryScope) m_scope).getTemplateId());
            }
        } catch (Exception ex) {
            Code.warning(ex);
            throw new PersistenceException(ex);
        }

        BaseObjectHandler handler = CacheEngine.getRecordHandler(DomainValues.DOMAIN_TLV, rType);
        IValue[] values = new IValue[handler.getAttributes().length];
        handler.copyInto(queryObj, values);

        TLV objectTLV = new TLV(Short.parseShort(Integer.decode(TypeRegistry.getInstance().getAliasValue("_TlvGetB6TemplateResult_")).toString()), TLVUtils.populateTLVFromObjectFields(values, handler));
        return objectTLV;
                
    }
    

}
