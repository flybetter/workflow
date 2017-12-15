package com.calix.bseries.server.task;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/21/11
 * Time: 3:29 PM
 *
 * B6/2300 AE ONT discovery response signal
 */
public class BSeriesLinkDiscoveryResponseSignal extends BSeriesTaskSignal {
    private String ne1Name;
    private String ne2Name;
    private String ne1Port;
    private String ne2Port;    
    private String linkType;
    
    public String getNe1Name() {
        return ne1Name;
    }

    public void setNe1Name(String ne1Name) {
        this.ne1Name = ne1Name;
    }

    public String getNe2Name() {
        return ne2Name;
    }

    public void setNe2Name(String ne2Name) {
        this.ne2Name = ne2Name;
    }

    public String getNe1Port() {
        return ne1Port;
    }

    public void setNe1Port(String ne1Port) {
        this.ne1Port = ne1Port;
    }

    public String getNe2Port() {
        return ne2Port;
    }

    public void setNe2Port(String ne2Port) {
        this.ne2Port = ne2Port;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    } 

   
}
