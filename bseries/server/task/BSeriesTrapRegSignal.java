package com.calix.bseries.server.task;

/**
 * Created by IntelliJ IDEA.
 * User: sshi
 * Date: 7/21/11
 * Time: 4:18 PM
 *
 * B6/AE ONT trap registration signal
 */
public class BSeriesTrapRegSignal extends BSeriesTaskSignal {
    private boolean isReg;

    public boolean isReg() {
        return isReg;
    }

    public void setReg(boolean reg) {
        isReg = reg;
    }
}
