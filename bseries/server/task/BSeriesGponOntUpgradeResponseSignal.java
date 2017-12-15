package com.calix.bseries.server.task;

import com.occam.ems.common.dataclasses.OccamProtocolRequestResponse;
import java.util.HashMap;
import java.util.Vector;
/**
 * BSeries Network Upgrade Response Signal in CMS
 * @author jawang
 *
 */
public class BSeriesGponOntUpgradeResponseSignal extends BSeriesNetworkUpgradeResponseSignal{
    
    private String gponOntListToUpgrade;
    private String gponOntUpgradeType;
    
    private String gponActivateAction;
    private String gponDownloadAction;
    private HashMap gponOntObjs;
    private OccamProtocolRequestResponse resObj;
    
    public String getGponOntListToUpgrade() {
        return gponOntListToUpgrade;
    }
    public void setGponOntListToUpgrade(String gponOntListToUpgrade) {
        this.gponOntListToUpgrade = gponOntListToUpgrade;
    }
    public String getGponOntUpgradeType() {
        return gponOntUpgradeType;
    }
    public void setGponOntUpgradeType(String gponOntUpgradeType) {
        this.gponOntUpgradeType = gponOntUpgradeType;
    }
    public String getGponActivateAction() {
        return gponActivateAction;
    }
    public void setGponActivateAction(String gponActivateAction) {
        this.gponActivateAction = gponActivateAction;
    }
    
    public String getGponDownloadAction() {
        return gponDownloadAction;
    }
    public void setGponDownloadAction(String gponDownloadAction) {
        this.gponDownloadAction = gponDownloadAction;
    }
    
    public HashMap getGponOntObjs() {
        return gponOntObjs;
    }
    
    public void setGponOntObjs(HashMap gponOntObjs) {
        this.gponOntObjs = gponOntObjs;
    }

    public OccamProtocolRequestResponse getResObj() {
        return resObj;
    }

    public void setResObj(OccamProtocolRequestResponse resObj) {
        this.resObj = resObj;
    }
    
}
