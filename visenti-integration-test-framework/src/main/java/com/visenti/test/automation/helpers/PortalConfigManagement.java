package com.visenti.test.automation.helpers;

import com.visenti.test.automation.helpers.FileReaderManager;

public class PortalConfigManagement {
	private static String portal;
	private static String portalPrefix;
	private static String portalDomain;

	private static String umsPortalDomain;
	private static String portalUMSPrefix;

	public static String getPortal() {
		return portal;
	}

	public static void setPortal(String portal) {
		PortalConfigManagement.portal = portal;
	}

	public static String getPortalPrefix() {
		return portalPrefix;
	}

	public static void setPortalPrefix(String portal) {

		PortalConfigManagement.portalPrefix = FileReaderManager.getInstance().getConfigReader()
				.getPortalURLPrefix(portal);
	}

	public static String getPortalDomain() {
		return portalDomain;
	}

	public static void setPortalDomain(String portal) {

		PortalConfigManagement.portalDomain = FileReaderManager.getInstance().getConfigReader()
				.getPortalURLDomain(portal);
	}

	public static String getUMSPortalDomain() {
		return umsPortalDomain;
	}

	public static void setUMSPortalDomain(String portal) {
		PortalConfigManagement.umsPortalDomain = FileReaderManager.getInstance().getConfigReader()
				.getUMSPortalURLDomain(portal);
	}

	public static void setUMSPortaPrefix(String portal) {
		PortalConfigManagement.portalUMSPrefix = FileReaderManager.getInstance().getConfigReader()
				.getUMSPortalPrefix(portal);
	}

	public static String getPortalUMSPrefix() {
		return portalUMSPrefix;
	}

}
