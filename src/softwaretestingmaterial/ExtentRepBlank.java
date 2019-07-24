package softwaretestingmaterial;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentRepBlank {
	ExtentReports extent;
	ExtentTest logger;

	
	@BeforeTest
	public void startReport(){
		extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/BLANKExtentReport.html", true);
		extent
		.addSystemInfo("Template", "Software Testing")
		.addSystemInfo("Environment", "Automation Testing")
		.addSystemInfo("User Name", "Kate Nowak");
		
		extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config2.xml"));
	}
	@Test
	public void passTest() {
		
		logger = extent.startTest("passTest");
		Assert.assertTrue(true);	
		logger.log(LogStatus.PASS, "Test Case Passed is passTest");
		}

	@Test
	public void failTest(){

		logger = extent.startTest("failTest");

		Assert.assertTrue(false);
		logger.log(LogStatus.FAIL, "Test Case (failTest) Status is FAIL");

	}

	@Test
	public void skipTest() {
		logger = extent.startTest("skipTest");
		throw new SkipException("Skipping - This is not ready for testing");
	}
	
	@AfterMethod(alwaysRun=true)
	public void getResult(ITestResult result) {
	
		
				if(result.getStatus() == ITestResult.FAILURE) {
					logger.log(LogStatus.FAIL,  "Test Case Failed is "+ result.getName());
					logger.log(LogStatus.FAIL, "Test Case Failed is "+ result.getThrowable());
				}
				else if(result.getStatus() == ITestResult.SKIP){
					logger.log(LogStatus.SKIP, "Test Case skipped is "+result.getName());
				}
				else if(result.getStatus()==ITestResult.SUCCESS){
					logger.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

				}
		
		extent.endTest(logger);
	}
	
	@AfterTest
	public void endReport(){
		extent.flush();
		extent.close();
	}
	
}



