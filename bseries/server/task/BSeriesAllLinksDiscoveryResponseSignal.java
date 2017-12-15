package com.calix.bseries.server.task;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/21/11
 * Time: 3:29 PM
 *
 * B6/2300 AE ONT discovery response signal
 */
public class BSeriesAllLinksDiscoveryResponseSignal extends BSeriesTaskSignal {
   private List<BSeriesLinkDiscoveryResponseSignal> epsInfoLst = new ArrayList();

    public List getEpsInfoLst() {
        return epsInfoLst;
    }

    public void setEpsInfoLst(BSeriesLinkDiscoveryResponseSignal signal) {
        epsInfoLst.add(signal);
    }
}
