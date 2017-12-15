package com.calix.bseries.gui.utils;

import com.calix.ems.EMSInit;
import com.calix.system.gui.model.CalixCit;
import com.objectsavvy.base.persistence.exceptions.TransactionNotInProgressException;
import com.objectsavvy.base.persistence.model.IDatabase;
import com.objectsavvy.base.util.debug.Code;

public class B6GUIUtils {

	public static final int B6_SECURITY_GUEST = 0;
	public static final int B6_SECURITY_PRIVILEGED = 1;
	public static final int B6_SECURITY_ADMINISTRATOR = 2;

	public static final String B6_SECURITY_ADMINISTRATOR_USERNAME = "Administrator";
	public static final String B6_SECURITY_GUEST_USERNAME = "Guest";
	public static final String B6_SECURITY_PRIVILEGED_USERNAME = "Priviledged";

	public static final String B6_SECURITY_ADMINISTRATOR_PASSWD = "razor123";
	public static final String B6_SECURITY_GUEST_PASSWD = "ipvideo";
	public static final String B6_SECURITY_PRIVILEGED_PASSWD = "3playblc";

	public static int getB6Priviledge() {

		int b6Priviledge = 0;

		IDatabase db = CalixCit.getCalixCitInstance().getReadOnlyDatabase();
		boolean isActivited = false;
		try {
			if (!db.isActive()) {
				db.begin();
				isActivited = true;
			}
			b6Priviledge = B6SecurityHelper.getPriviledge(EMSInit
					.getEMSRoot(db));
		} catch (Exception e) {
			Code.warning(
					"Fail to load B6 secutiry level for curernt user. Error: "
							+ e.getMessage(), e);
		} finally {
			if (db.isActive() && isActivited == true) {
				try {
					db.rollback();
				} catch (TransactionNotInProgressException e) {
					// ignore
				}
			}
		}

		return b6Priviledge;

	}
}
