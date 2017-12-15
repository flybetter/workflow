package com.calix.bseries.gui.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.calix.system.gui.navtree.CalixTreeModelHandler;
import com.calix.system.gui.navtree.IDeviceNavController;
import com.calix.system.gui.navtree.IDeviceSelectionFilter;
import com.calix.system.gui.navtree.ITreeModel;
import com.calix.system.gui.navtree.NavigationController;
import com.calix.system.gui.panels.CalixPanelController;
import com.calix.system.gui.panels.IDevicePanelControl;
import com.calix.system.gui.util.DeviceModuleInitImpl;

public class OcmDeviceModuleInitImpl extends DeviceModuleInitImpl {

	public static String DEVICE_TYPE_OBJ = "OCM";
	
	@Override
	public IDeviceNavController getNavControlImpl(NavigationController ctrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDeviceSelectionFilter getSelectionFilterImpl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITreeModel getNavTreeModelImpl(CalixTreeModelHandler ctrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDevicePanelControl getPanelControlImpl(CalixPanelController ctrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDeviceType() {
		return DEVICE_TYPE_OBJ;
	}

	@Override
	protected void addTLVAddressUtils() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<String> getDevicePlatforms() {
		    Collection<String> platforms = new ArrayList<String>(2);
	        platforms.add(DEVICE_TYPE_OBJ);	       
	        return platforms;
	}

}
