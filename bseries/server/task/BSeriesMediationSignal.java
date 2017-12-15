package com.calix.bseries.server.task;

import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 8/19/11
 * Time: 5:06 PM
 */
public class BSeriesMediationSignal extends BSeriesTaskSignal {
    private OccamProtocolRequestResponse occamProtocolRequestResponse;
    private long requestId;

    public OccamProtocolRequestResponse getOccamProtocolRequestResponse() {
        return occamProtocolRequestResponse;
    }

    public void setOccamProtocolRequestResponse(OccamProtocolRequestResponse occamProtocolRequestResponse) {
        this.occamProtocolRequestResponse = occamProtocolRequestResponse;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
}
