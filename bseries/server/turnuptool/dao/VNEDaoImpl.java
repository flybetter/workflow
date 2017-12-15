package com.calix.bseries.server.turnuptool.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.calix.bseries.server.dbmodel.CalixB6Device;
import com.calix.ems.database.C7Database;
import com.calix.ems.database.CMSDatabase;
import com.calix.ems.database.DatabaseManager;
import com.calix.ems.database.DbTransaction;
import com.calix.ems.exception.EMSException;
import com.calix.ems.server.cache.CMSCacheManager;
import com.calix.system.server.dbmodel.BaseInventoryNetwork;

public class VNEDaoImpl {
	private final static Logger logger = Logger.getLogger(VNEDaoImpl.class);
	private static final VNEDaoImpl instance = new VNEDaoImpl();

	public static VNEDaoImpl getInstance() {
		return instance;
	}

	public CalixB6Device queryCMSObjectByName(String networkName) {
		CalixB6Device b6 = null;
		C7Database db = C7Database.getInstance();
		List networks = null;
		try {
			db.beginTransaction();
			networks = db
					.getSession()
					.createQuery(
							" from CalixB6Device networks where networks.dbIdentity='"
									+ networkName + "'").list();
			if (networks != null && networks.size() > 0) {
				b6 = (CalixB6Device) networks.get(0);
			}
		} catch (Exception ex) {
			logger.error("Errot to query networks", ex);
		} finally {
			db.close();
		}
		return b6;
	}

	public CalixB6Device queryCMSObjectByIpAddress1(String ip) {
		CalixB6Device b6 = null;
		C7Database db = C7Database.getInstance();
		List networks = null;
		try {
			db.beginTransaction();
			networks = db
					.getSession()
					.createQuery(
							" from CalixB6Device networks where networks.IPAddress1='"
									+ ip + "'").list();
			if (networks != null && networks.size() > 0) {
				b6 = (CalixB6Device) networks.get(0);
			}
		} catch (Exception ex) {
			logger.error("Errot to query networks", ex);
		} finally {
			db.close();
		}
		return b6;
	}

	public String createCMSObject(CalixB6Device b6) {
		DbTransaction txn = null;
		try {
			CMSDatabase database = DatabaseManager.getCMSDatabase();
			txn = database.getTransaction("ems", DbTransaction.UPDATE);
			txn.begin();
			b6.doCreate(txn);
			txn.commit();
			txn = null;
		} catch (Exception e) {
			logger.warn(
					"Fail to create CalixB6Devicee. Error: " + e.getMessage(),
					e);
			return e.getMessage();
		} finally {
			if (txn != null) {
				txn.abort();
				txn = null;
			}
		}
		return "success";
	}

	public String deleteCMSObject(CalixB6Device b6) {
		C7Database db = C7Database.getInstance();
		try {
			db.beginTransaction();
			db.deleteCMSObject(b6);
			BaseInventoryNetwork inventory = new BaseInventoryNetwork();
			inventory.setDbIdentity(b6.getDbIdentity());
			db.deleteObject(inventory);
			db.commitTransaction();
			db.close();
	        CMSCacheManager.getCacheManager().removeEMSObject(b6);
		} catch (Exception ex) {
			logger.warn(
					"Fail to delete CalixB6Devicee. Error: " + ex.getMessage(),
					ex);
			return ex.getMessage();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return "success";
	}

}
