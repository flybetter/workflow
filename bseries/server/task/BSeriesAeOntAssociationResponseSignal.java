package com.calix.bseries.server.task;

import java.util.ArrayList;
import java.util.List;

/**
 * User: hzhang
 * Date: 1/8/13
 *
 * B6/2300 AE ONT Association discovery response signal
 */
public class BSeriesAeOntAssociationResponseSignal extends BSeriesTaskSignal {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> ipList = new ArrayList();

    public List getIpList() {
        return ipList;
    }

    public void setIpList(String str) {
    	ipList.add(str);
    }

    public void setIpList(List<String> str) {
    	ipList = str;
    } 
}
