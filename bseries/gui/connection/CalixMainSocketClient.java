package com.calix.bseries.gui.connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import com.calix.bseries.gui.model.CCLConnectionAction;
import com.calix.ems.EMSInit;
import com.calix.ems.model.EMSRoot;
import com.calix.system.gui.exceptions.TLVPersistenceException;
import com.calix.system.gui.model.CalixCit;
import com.objectsavvy.base.persistence.exceptions.MappingException;
import com.objectsavvy.base.persistence.meta.IValue;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;
import com.occam.ems.client.util.ClientUtil;
import com.occam.ems.client.util.gui.WaitDialog;
import com.occam.ems.common.proxy.startclient.MainSocketClient;
import com.occam.ems.common.proxy.startclient.SocketConnection;

public class CalixMainSocketClient extends MainSocketClient {
	HashMap<String,SocketConnection> socketConnectionMap; 
	private final static int maxLengthBinary = 20000; // keep it consistent with CCLConnectionAction
	private ThreadPoolExecutor asynThreadPool;
    private static int reqId = 1;
	/**
	 * All clients sessions implementing the SocketConnection Interface will register for responses here with the session id the client will use for communicating with the server. The id should unique among all the sessions as its used to identify the appropriate session during communication. 
	 * @param sockClient
	 * @param id
	 * @return id to be used for communication with the Server. This is same as the id passed as parameter.
	 */
	public String registerForResponses(SocketConnection sockClient,String id){
		if(socketConnectionMap == null) {
			socketConnectionMap = new HashMap<String,SocketConnection>();
			asynThreadPool = new ThreadPoolExecutor(50, 50, 300L, TimeUnit.SECONDS,
	                new ArrayBlockingQueue(300), new ThreadPoolExecutor.DiscardPolicy());
		}
		synchronized(socketConnectionMap) {
			if(!socketConnectionMap.containsKey(id))
				socketConnectionMap.put(id, sockClient);
		}
		return id;
	}
	
	public void deRegisterForResponses(String id) {
		if(socketConnectionMap != null && socketConnectionMap.containsKey(id))
			socketConnectionMap.remove(id);
	}
	
	/**
	 * Sends the data to the server and returns only after a response is received for this particular request i.e., the data is transferred synchronously. The method will timeout if there is no response from the server for more than the default time out duration (20 secs). 
	 * @param id
	 * @param data
	 * @return
	 */
	public byte[] syncSend(String id,
            byte[] data){
		byte[] result = null;
		try{
			ArrayList<CCLConnectionAction> returnCCLActionList = (ArrayList<CCLConnectionAction>)sendToCms(id,data);
			
			if(returnCCLActionList.get(0) != null) {
	        	IValue packageData = returnCCLActionList.get(0).getAttributeValue(CCLConnectionAction.ACTION_BINARYPACKAGE);
	        	if(packageData != null) {
	        		result = packageData.convertTo(byte[].class, null);
	        	}			
			}
		} catch (Exception pex) {
            Code.warning(pex);
        }
        return result;
	}
	/**
	 * This method sends the data asynchronously to the Server.ie., this method returns immediately after placing the request in a request queue. The data passed to this method will be later processed via scheduling mechanism. 
	 * @param id
	 * @param data
	 * @return
	 */
	public boolean send(final String id,
            final byte[] data)	{
		//Should use AbstractConnection to send action asynchronously.Use below way for prototype to saving time.
		if(socketConnectionMap == null || !socketConnectionMap.containsKey(id)) {
			Code.warning(id + "haven't be registed.");
			return false;
		}
		asynThreadPool.execute(new Runnable() {
            public void run() {
        		try{
					ArrayList<CCLConnectionAction> returnCCLActionList = (ArrayList<CCLConnectionAction>) sendToCms(id, data);
					int size = returnCCLActionList.size();
					ArrayList<byte[]> binaryArray = new ArrayList<byte[]>();

					// Get the socket connection. 
					SocketConnection sockCon = null;
					if (size >= 1) {
						CCLConnectionAction action = returnCCLActionList.get(0);
						if (action != null) {
							IValue idValue = action
									.getAttributeValue(CCLConnectionAction.ATTR_REQUESTID);
							if (idValue != null) {
								String id = idValue.convertTo(String.class, null);
								sockCon = socketConnectionMap.get(id);
							}
						}
					}
					if (sockCon != null) {
						// get array for binary data for assembling.
						int allBinaryLength = 0;
						for (int i = 0; i < size; i++) {
							CCLConnectionAction action = returnCCLActionList.get(i);
							if (action != null) {
								IValue packageData = action.getAttributeValue(CCLConnectionAction.ACTION_BINARYPACKAGE);
								if (packageData != null) {
									byte[] res = packageData.convertTo(byte[].class, null);
									allBinaryLength += res.length;
									binaryArray.add(res);
								}
							}
						}
						// assemble all the binaries together.
						byte[] result = new byte[allBinaryLength];
						for (int i = 0; i < binaryArray.size(); i++) {
							System.arraycopy(binaryArray.get(i), 0, result, i * maxLengthBinary,
									binaryArray.get(i).length);
						}
						if (result.length > 0) {
							sockCon.receive(result);
						} else {
							Code.warning("Discard package for nothing returned, session: " + id);
							WaitDialog.getInstance().disposeDialog();
							ClientUtil.showPopUpMessage( "Response Time out", "Timeout when waiting for response from server.");
						}
					} else {
						Code.warning("Discard package for deregisted session:" + id);
						WaitDialog.getInstance().disposeDialog();
						ClientUtil.showPopUpMessage( "Response Time out", "Couldn't create socket with CMS server.");
					}
				} catch(TLVPersistenceException e){ 
					//CMS-7616 catch security check failed to prevent showing up to many warning message.
		            Code.warning(e);
				}catch (Exception pex) {
					Code.warning(pex);
					WaitDialog.getInstance().disposeDialog();
				}       	
            }
        });
		return true;
	}
	private Collection sendToCms(String id,
			byte[] data) throws Exception {		
        IDatabase db = CalixCit.getCalixCitInstance().getDatabase();
        boolean activated = false;
        Collection res = new ArrayList();
        try {
            if (!db.isActive()) {
                db.begin();
                activated = true;
            }
            EMSRoot root = EMSInit.getReadonlyEMSRoot(db);
            int arrayLength = data.length;
			if(arrayLength > maxLengthBinary){
				int totalNum = -1;
					
				CCLConnectionAction subAction = CCLConnectionAction.createAction(root);
            	totalNum = arrayLength/maxLengthBinary;
            	Integer reqKey = generateTagId();
				for (int j = 0; j < totalNum; j++) {
					byte[] tmpByte = new byte[maxLengthBinary];
					System.arraycopy(data, j * maxLengthBinary, tmpByte,
							0, maxLengthBinary);
					setAttributeValue(subAction, id, tmpByte, new Integer(CCLConnectionAction.ACTION_ISMULTIPLE_YES), new Integer(totalNum), new Integer(j), reqKey);
		            res = db.sendAction(subAction);
		            if (res.size() < 1) {
		            	Code.warning("No results returned in num: " + j + " of total number: " + totalNum + ". ");
		            }
				}
				int choppedLength = arrayLength % maxLengthBinary;
				if(choppedLength > 0){
					byte[] lastByte = new byte[choppedLength];
					System.arraycopy(data, (arrayLength / maxLengthBinary)
							* maxLengthBinary, lastByte, 0, choppedLength);
					setAttributeValue(subAction, id, lastByte, new Integer(CCLConnectionAction.ACTION_ISMULTIPLE_YES), new Integer(totalNum), new Integer(totalNum), reqKey);
		            res = db.sendAction(subAction);
				}
            }else{
                CCLConnectionAction action = CCLConnectionAction.createAction(root);
				setAttributeValue(action, id, data, new Integer(CCLConnectionAction.ACTION_ISMULTIPLE_NO), 0, 0, 0);
                res = db.sendAction(action);
            }

            if (res.size() < 1) {
            	Code.warning("No results returned in test");
            }
        } catch (TLVPersistenceException ex){
        	if(ex.getMessage() != null && ex.getMessage().contains("SecurityCheck failed")){
        		throw ex;
        	}
        }catch (Exception pex) {
            Code.warning(pex);
        }finally {
            if (activated)
                try {
                    db.rollback();
                } catch (Exception ignore) {
                }
        }
        return res;		
	}

	private void setAttributeValue(CCLConnectionAction subAction, String id, byte[] tmpByte,
			Integer isMualtiple, Integer totalNum, Integer num, Integer reqKey) throws MappingException {
		subAction.setAttributeValue(CCLConnectionAction.ATTR_REQUESTID, id);
		subAction.setAttributeValue(CCLConnectionAction.ACTION_BINARYPACKAGE, tmpByte);
		subAction.setAttributeValue(CCLConnectionAction.ACTION_ISMULTIPLE, isMualtiple);
		subAction.setAttributeValue(CCLConnectionAction.ACTION_TOTALNUM, totalNum);
		subAction.setAttributeValue(CCLConnectionAction.ACTION_NUM, num);
		subAction.setAttributeValue(CCLConnectionAction.ACTION_REQKEY, reqKey);
	}

	private static synchronized int generateTagId() {
    	if(reqId < Integer.MAX_VALUE)
    		reqId += 1;
    	else
    		reqId = 1;
    	return reqId;
    }
}
