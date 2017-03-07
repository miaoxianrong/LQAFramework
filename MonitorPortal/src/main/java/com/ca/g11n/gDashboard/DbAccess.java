package com.ca.g11n.gDashboard;

public class DbAccess {

// private fields

	private static final ClarityDbAccess m_clarityDbAccess = new ClarityDbAccess();
	private static final GDashboardDbAccess m_gDashboardDbAccess = new GDashboardDbAccess();


// public methods

	public static ClarityDbAccess getClarityDbAccess() {
		return m_clarityDbAccess;
	}
	public static GDashboardDbAccess getGDashboardDbAccess() {
		return m_gDashboardDbAccess;
	}

}
