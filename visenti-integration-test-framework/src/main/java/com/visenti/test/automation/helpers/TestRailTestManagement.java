package com.visenti.test.automation.helpers;

public class TestRailTestManagement {
    private static int testRun;
    private static int testCase;
    private static String testComment="";
    private static String API;
    private static String Action;

    public static void setAPI(String API) {
        TestRailTestManagement.API = API;
    }

    public static void setAction(String action) {
        Action = action;
    }

    public static String getAPI() {
        return API;
    }

    public static String getAction() {
        return Action;
    }

    public static int getTestRun(){
     return testRun;
    }

    public static void setTestRun(int testrun){
        TestRailTestManagement.testRun = testrun;
    }

    public static int getTestCase(){
        return testCase;
    }

    public static void setTestCase(int testcase){
        TestRailTestManagement.testCase = testcase;
    }

    public static String getTestComment(){
        return testComment;
    }

    public static void setTestComment(String testComment){
        TestRailTestManagement.testComment = testComment;
    }

    public static void setErrorTestComment(String testComment){
        TestRailTestManagement.testComment = testComment + System.lineSeparator() +TestRailTestManagement.testComment;
    }

    public static void setCompleteTestComment(String testComment){
        TestRailTestManagement.testComment = testComment + System.lineSeparator() +TestRailTestManagement.testComment;
    }
}
