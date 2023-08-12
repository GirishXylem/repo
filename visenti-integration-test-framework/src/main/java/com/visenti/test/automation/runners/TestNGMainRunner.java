package com.visenti.test.automation.runners;
import com.visenti.test.automation.helpers.TestListener;
import org.testng.TestNG;


public class TestNGMainRunner {

    public static void main(String args[]){
        TestNG testSuite = new TestNG();
        testSuite.setTestClasses(new Class[] {TestCommonRunner.class });
        testSuite.addListener(new TestListener());
        testSuite.setDefaultSuiteName("Visenti Automation Test Suite");
        testSuite.setDefaultTestName("Visenti Test Framework");
        testSuite.setOutputDirectory("target/testng-reports");
        testSuite.run();
    	
    }

}
