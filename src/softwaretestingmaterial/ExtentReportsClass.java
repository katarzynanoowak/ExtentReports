package softwaretestingmaterial;


import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.types.resources.comparators.Date;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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


public class ExtentReportsClass {
	
	
	
	ExtentReports extent;
	ExtentTest logger;
	WebDriver driver;
	

	
	@BeforeTest
	public void startReport(){
		extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/KNExtentReport.html", true);
		extent
		.addSystemInfo("Host Name", "Software Testing Material")
		.addSystemInfo("Environment", "Automation Testing")
		.addSystemInfo("User Name", "Kate Nowak");
		
		extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config2.xml"));

	}
	
	public static String getScreenshot(WebDriver driver) throws Exception {
		
		// String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		 TakesScreenshot ts = (TakesScreenshot) driver;
		 
		 File src = ts.getScreenshotAs(OutputType.FILE);
		 String path = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+/*dateName+*/".png";
		 
		 
		 File destination = new File(path);
		 FileUtils.copyFile(src, destination);
		 return path;
		 }
	
	
	@Test
	public void passTest() {
		
		logger = extent.startTest("passTest");
		System.setProperty("webdriver.chrome.driver", "C:\\Automation\\chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.get("https://courses.ultimateqa.com/users/sign_in");
		
		Assert.assertTrue(true);		
		}
	
	
	@Test
	public void myTest() throws IOException {
		
		logger = extent.startTest("myTest");
		System.setProperty("webdriver.chrome.driver", "C:\\Automation\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://google.com");
		System.out.println("title is "+driver.getTitle());
		
		Assert.assertTrue(driver.getTitle().contains("Damn"));
	
		}
		
		
		
		//Assert.assertTrue(false);
		//logger.log(LogStatus.FAIL, "Test case (failTest) Status is passed");*/
	
	
	/*@Test
	public void skipTest() {
		logger = extent.startTest("skipTest");
		throw new SkipException("Skipping - This is not ready for testing");
	}*/
	
	@AfterMethod(alwaysRun=true)
	public void getResult(ITestResult result) throws Exception{

		try {
			if(result.getStatus() == ITestResult.FAILURE) {
				logger.log(LogStatus.FAIL,  "Test Case Failed is "+ result.getName());
				logger.log(LogStatus.FAIL, "Test Case Failed is "+ result.getThrowable());
				String path=ExtentReportsClass.getScreenshot(driver);
				String image= logger.addScreenCapture(path);
				logger.log(LogStatus.FAIL, "Title verification", image);

			}
			else if(result.getStatus() == ITestResult.SKIP){
				logger.log(LogStatus.SKIP, "Test Case skipped is "+result.getName());
			
			}
			else if(result.getStatus()==ITestResult.SUCCESS){
				logger.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

				String path=ExtentReportsClass.getScreenshot(driver);
				String image= logger.addScreenCapture(path);
				logger.log(LogStatus.PASS, "Title ver", image);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		extent.endTest(logger);
		
		
	}
	
	@AfterTest(alwaysRun=true)
	public void endReport() {
		
		extent.flush();
		extent.close();
		driver.close();
	
		
	}
    

}
