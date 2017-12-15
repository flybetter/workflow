package com.calix.bseries.gui.tool;

import com.calix.bseries.gui.model.EMSBseriesRangeDiscoveryAction;
import com.calix.system.gui.components.dialogs.CalixCancelProgressDialog;
import com.calix.system.gui.model.CalixCit;
import com.calix.system.gui.util.IpAddressUtils;
import com.objectsavvy.base.persistence.OSBaseAction;
import com.objectsavvy.base.persistence.TxPersistEvent;
import com.objectsavvy.base.persistence.exceptions.PersistenceException;
import com.objectsavvy.base.persistence.meta.TreeValue;
import com.objectsavvy.base.persistence.meta.TypeRegistry;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RangeDiscoverySender {
    ExecutorService threadPool;
    int threadNum;
    CountDownLatch latch;
    int discoveredNo;
    int noResponseNo;
    int remainningNo;
    int alreadyExistNo;
    
    public RangeDiscoverySender(int threadNum) throws PersistenceException {
        this.threadNum = threadNum;
        threadPool = Executors.newFixedThreadPool(threadNum);
    }
    
    public void cleanUp() throws PersistenceException{
        if (threadPool != null){
            threadPool.shutdown();
            Code.warning("thread pool will close when all of tasks are finished");
        }
    }
    
    public void process(final ArrayList<TxPersistEvent> events, final String readCommunity, final Properties pro) throws Exception{
        final CalixCancelProgressDialog progressDialog = new CalixCancelProgressDialog(false, null);
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                try {
                    int eventNo = events.size();
                    progressDialog.onEvent(new TxPersistEvent(eventNo , this, TxPersistEvent.EVENT_ENTITY_COUNT, events));
                    remainningNo = eventNo;
                    //ticket 49371
                    int countInOnePart = (int)(eventNo*1.0/threadNum);
                    OSBaseAction[] actionObjs = new OSBaseAction[threadNum];
                    IDatabase[] databases = new IDatabase[threadNum];
                    for (int j = 0; j < threadNum; j++){
                        actionObjs[j] = (OSBaseAction)TypeRegistry.getInstance().getRecordType("EMSBseriesRangeDiscoveryAction").newInstance();
                        actionObjs[j].setIdentityValue(CalixCit.getCalixCitInstance().getNetworkIdentity());
                        //CMS-11081 by hzhang
//                        if ( !"public".equals(readCommunity) ){
                        	actionObjs[j].setAttributeValue(EMSBseriesRangeDiscoveryAction.ATTR_COMMUNITY, TypeRegistry.getInstance().getType("OSString").convertFrom(readCommunity, null));
//                        }
                        if( pro!= null && "false".equals(pro.get(EMSBseriesRangeDiscoveryAction.ATTR_AUTOCHASSIS))){
                        	actionObjs[j].setAttributeValue(EMSBseriesRangeDiscoveryAction.ATTR_AUTOCHASSIS, TypeRegistry.getInstance().getType("CalixU32").convertFrom(new Integer(0), null));                        	
                        }else{
                        	actionObjs[j].setAttributeValue(EMSBseriesRangeDiscoveryAction.ATTR_AUTOCHASSIS, TypeRegistry.getInstance().getType("CalixU32").convertFrom(new Integer(1), null));
                        }

                        
                        if(pro!= null && "false".equals(pro.get(EMSBseriesRangeDiscoveryAction.ATTR_ALLOWBSERIES))){
                        	actionObjs[j].setAttributeValue(EMSBseriesRangeDiscoveryAction.ATTR_ALLOWBSERIES, TypeRegistry.getInstance().getType("CalixU32").convertFrom(new Integer(0), null));                        	
                        }else{
                        	actionObjs[j].setAttributeValue(EMSBseriesRangeDiscoveryAction.ATTR_ALLOWBSERIES, TypeRegistry.getInstance().getType("CalixU32").convertFrom(new Integer(1), null));
                        }
                        
                        if(pro!= null && "false".equals(pro.get(EMSBseriesRangeDiscoveryAction.ATTR_ALLOWEXA))){
                        	actionObjs[j].setAttributeValue(EMSBseriesRangeDiscoveryAction.ATTR_ALLOWEXA, TypeRegistry.getInstance().getType("CalixU32").convertFrom(new Integer(0), null));                        	
                        }else{
                        	actionObjs[j].setAttributeValue(EMSBseriesRangeDiscoveryAction.ATTR_ALLOWEXA, TypeRegistry.getInstance().getType("CalixU32").convertFrom(new Integer(1), null));
                        }
                        
                        databases[j] = CalixCit.getCalixCitInstance().getDatabase();
                        databases[j].begin();
                    }
                    latch = new CountDownLatch(threadNum);
                    int fromIndex = 0;
                    int j = 0;
                    //ticket 49371
                    for (; j < threadNum-1; fromIndex += countInOnePart){
                        threadPool.execute(createTask(events.subList(fromIndex, fromIndex + countInOnePart), actionObjs[j], databases[j], progressDialog, latch) );
                        j++;
                    }
                    threadPool.execute(createTask(events.subList(fromIndex, eventNo),  actionObjs[j], databases[j], progressDialog, latch) );
                    latch.await();
                }  catch (Exception e) {
                	Code.warning(e.getMessage(),e);
                } finally{
                    progressDialog.done("Discovered: " + discoveredNo + ", Existing: " + alreadyExistNo + ", No response: " + noResponseNo + ", Remaining: " + remainningNo);
                }
            }
        };
        new Thread(runnable).start();
        progressDialog.pack();
        progressDialog.setVisible(true);
    }
    
    private Runnable createTask(final List<TxPersistEvent> subEvents, final OSBaseAction actionObj, final IDatabase db, 
            final CalixCancelProgressDialog progressDialog, final CountDownLatch latch){
        return new Runnable(){
            @Override
            public void run() {
                progressDialog.setMessage("Discovered: " + discoveredNo + ", Existing: " + alreadyExistNo + ", No response: " + noResponseNo + ", Remaining: " + remainningNo);
                try{
                    for (TxPersistEvent event : subEvents){
                        try {
                            if (progressDialog.isCancelled()){
                                event.setStatus(TxPersistEvent.STATUS_CANCELLED);
                                progressDialog.onEvent(event);
                                continue;
                            }
                            event.setStatus(TxPersistEvent.STATUS_INPROGRESS);
                            progressDialog.onEvent(event);
                            actionObj.setAttributeValue("IP", IpAddressUtils.getIpAddressAsString(((TreeValue)event.getIdentity()).getComponent("ipaddr")));
                            db.sendAction(actionObj);
                            event.setStatus(TxPersistEvent.STATUS_COMPLETED);
                            discoveredNo++;
                            progressDialog.onEvent(event);
                        } catch (Exception e){
                            if (e.getMessage().contains("exist")){
                                alreadyExistNo++;
                                event.setStatus(TxPersistEvent.STATUS_WARNING);
                                event.setInfoMessage(e.getMessage());
                            } else {
                                noResponseNo++;
                                event.setStatus(e);
                            }
                            progressDialog.onEvent(event);
                        } finally{
                        	remainningNo--;
                            progressDialog.setMessage("Discovered: " + discoveredNo + ", Existing: " + alreadyExistNo + ", No response: " + noResponseNo + ", Remaining: " + remainningNo);
                        }
                    }
                } finally{
                    Code.warning("thread is done: " + Thread.currentThread().getName());
                    latch.countDown();
                    if (db != null){
                        try {
                            db.close();
                            Code.warning("database is closed in the thread: " + Thread.currentThread().getName());
                        } catch (PersistenceException e) {
                            Code.warning(e.getMessage(),e);
                        }
                    }
                }
            }
        };
    }
    
}
