package com.calix.bseries.gui.model.persistence.tlv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import com.calix.system.common.constants.DomainValues;
import com.calix.system.common.protocol.tlv.ResponseSignal;
import com.calix.system.gui.model.persistence.TLVPersistence;
import com.objectsavvy.base.persistence.OSBaseObject;
import com.objectsavvy.base.persistence.TransactionContext;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.meta.RecordType;

/**
 * 
 * @author zgao
 *
 */

public class TLVBSeriesCCLConnectionPersistence extends TLVPersistence {
    public TLVBSeriesCCLConnectionPersistence(RecordType pType) {
        super(pType);
    }

    protected Collection parseActionResults(Collection pResSignals, IValue pIdentity, TransactionContext pContext) throws MappingException, PersistenceException {
        if ((pResSignals == null) || pResSignals.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        Collection responses = new ArrayList();
        for (Iterator i = pResSignals.iterator(); i.hasNext();) {
            ResponseSignal resSignal = (ResponseSignal) i.next();
            OSBaseObject resObject = (OSBaseObject)m_type.convertFrom(resSignal.getObjectDesc(), DomainValues.DOMAIN_TLV);
            if(resObject == null)
                continue;
            responses.add(resObject);
        }
        return (responses);
    }
}
