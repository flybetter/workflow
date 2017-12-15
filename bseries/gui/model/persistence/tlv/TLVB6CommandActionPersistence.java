package com.calix.bseries.gui.model.persistence.tlv;

import com.calix.system.gui.model.persistence.TLVPersistence;
import com.calix.system.gui.tlv.TLVUtils;
import com.calix.system.common.protocol.tlv.TLV;
import com.calix.system.common.constants.DomainValues;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.exceptions.QueryException;
import com.objectsavvy.base.persistence.model.IQuery;
import com.objectsavvy.base.persistence.model.IQueryScope;
import com.objectsavvy.base.persistence.CacheEngine;

public class TLVB6CommandActionPersistence extends TLVPersistence{
    public TLVB6CommandActionPersistence(RecordType pType) {
        super(pType);
    }
    protected TLV createAddressRangeTLV(IValue pIdentity) throws MappingException {
        return TLVUtils.createEmptyAddressRangeTLV();
    }

    public IQuery createQuery(IQueryScope pScope) throws QueryException {
        if (m_handler == null) {
            m_handler = CacheEngine.getRecordHandler(DomainValues.DOMAIN_TLV, m_type);
        }
        return new TLVB6CommandActionQuery(m_handler, pScope);
    }
}
