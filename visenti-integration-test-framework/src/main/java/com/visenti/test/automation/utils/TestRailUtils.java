package com.visenti.test.automation.utils;

import static com.visenti.test.automation.constants.TestRailConstants.COMMENT_BLOCK;
import static com.visenti.test.automation.constants.TestRailConstants.COMMENT_FAIL;
import static com.visenti.test.automation.constants.TestRailConstants.COMMENT_PASS;
import static com.visenti.test.automation.constants.TestRailConstants.STATUS_BLOCK;
import static com.visenti.test.automation.constants.TestRailConstants.STATUS_FAIL;
import static com.visenti.test.automation.constants.TestRailConstants.STATUS_PASS;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.testrail.APIClient;
import com.visenti.test.automation.testrail.APIException;

public class TestRailUtils {

	private static APIClient client = new APIClient(FileReaderManager.getInstance().getConfigReader().getTestRailURL());
	private static String user = FileReaderManager.getInstance().getConfigReader().getTestRailUser();
	private static String password = FileReaderManager.getInstance().getConfigReader().getTestRailPassword();

	public static void updateTestCaseResultPass(int run, int caseid) throws IOException, APIException {

		setCredentialsToAPIClient();
		Map<String, Object> data = new HashMap<>();
		data.put("status_id", STATUS_PASS);
		data.put("comment", COMMENT_PASS);
		JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + run + "/" + caseid, data);
		System.out.println(r);

	}

	public static void updateTestCaseResultFail(int run, int caseid) throws IOException, APIException {

		setCredentialsToAPIClient();
		Map<String, Object> data = new HashMap<>();
		data.put("status_id", STATUS_FAIL);
		data.put("comment", COMMENT_FAIL);
		JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + run + "/" + caseid, data);
		System.out.println(r);

	}

	public static void updateTestScenarioResultPass(int run, int caseid, String comment)
			throws IOException, APIException {

		setCredentialsToAPIClient();
		Map<String, Object> data = new HashMap<>();
		data.put("status_id", STATUS_PASS);
		data.put("comment", comment);
		JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + run + "/" + caseid, data);
		System.out.println(r);

	}

	public static void updateTestScenarioResultFail(int run, int caseid, String comment)
			throws IOException, APIException {

		setCredentialsToAPIClient();
		Map<String, Object> data = new HashMap<>();
		data.put("status_id", STATUS_FAIL);
		data.put("comment", comment);
		JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + run + "/" + caseid, data);
		System.out.println(r);

	}

	public static void updateTestCaseResultSkipped(int run, int caseid) throws IOException, APIException {

		setCredentialsToAPIClient();
		Map<String, Object> data = new HashMap<>();
		data.put("status_id", STATUS_BLOCK);
		data.put("comment", COMMENT_BLOCK);
		JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + run + "/" + caseid, data);
		System.out.println(r);

	}

	public static void getTestCaseResult(int run, int caseid) throws IOException, APIException {
		setCredentialsToAPIClient();
		JSONArray status2 = (JSONArray) client.sendGet("get_results_for_case/" + run + "/" + caseid);
		for (Object jsonValue2 : status2) {
			System.out.println(jsonValue2);
		}

	}

	public static void setCredentialsToAPIClient() {
		client.setUser(user);
		client.setPassword(password);
	}

}
