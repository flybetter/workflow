package com.calix.bseries.gui.visual.components;

import com.calix.system.gui.visual.components.VResource;
import com.objectsavvy.base.gui.util.OSImageUtils;
import com.objectsavvy.base.util.debug.Code;

import java.awt.Image;
import java.io.IOException;

public class BseriesResource extends VResource {
    static public final String BSERIES_IMAGE_BASE = "/com/calix/bseries/icons/";
    static public Image bseriesDeviceImage;
    static public Image bseriesChassisImage;
    static {
        try {
            bseriesDeviceImage = OSImageUtils.getImageResource(BSERIES_IMAGE_BASE + "B6.GIF").getImage();
            bseriesChassisImage = OSImageUtils.getImageResource(BSERIES_IMAGE_BASE + "chassis.png").getImage();
        } catch (IOException e) {
            Code.warning(e);
        }
    }
}
