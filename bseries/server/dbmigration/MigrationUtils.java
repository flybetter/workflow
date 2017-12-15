package com.calix.bseries.server.dbmigration;

public class MigrationUtils {

	public static String reConfigureRegion(String name) {
		if (name != null && name.contains("-")) {
			name = name.replaceAll("-", "_");
			return name;
		}
		return name;
	}
}
