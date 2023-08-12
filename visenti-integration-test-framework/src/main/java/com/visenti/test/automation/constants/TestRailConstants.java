package com.visenti.test.automation.constants;

/**
 * This class holds all the Test Rail Constants
 *
 */
public class TestRailConstants {

	private TestRailConstants() {
		throw new IllegalStateException("Test Constants Class");
	}

	// TestRail Constants
	public static final int STATUS_PASS = 1;
	public static final int STATUS_FAIL = 5;
	public static final int STATUS_BLOCK = 2;
	public static final String COMMENT_PASS = "This test passed! Updated by Automation Framework";
	public static final String COMMENT_FAIL = "This test failed! Updated by Automation Framework";
	public static final String COMMENT_BLOCK = "This test skipped and marked as blocked! Updated by Automation Framework";

}
