package softwaretestingmaterial;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Check11 {
	
	ExtentReports extent;
	ExtentTest logger;
	WebDriver driver;
	
	@BeforeTest
	public void StartReport() {
		extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/11report.html", true);
		extent
		.addSystemInfo("Template", "Software Testing")
		.addSystemInfo("Environment", "Automation Testing")
		.addSystemInfo("User Name", "Kate Nowak");
		
		extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config2.xml"));
	}
		
	@Test
	public void getPage() {
		
		logger = extent.startTest("getPage");
		
		System.setProperty("webdriver.chrome.driver", "C:\\Automation\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get("http://automationpractice.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath("//*[@id=\\'block_top_menu\\']/ul/li[1]/a"));
		action.moveToElement(we).perform();
		
		driver.findElement(By.xpath("//*[@id=\\'block_top_menu\\']/ul/li[1]/ul/li[1]/ul/li[1]/a")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		
		
		//driver.findElement(By.xpath("//*[@id=\'block_top_menu\']/ul/li[1]/a")).click();
		//driver.findElement(By.xpath("//*[@id=\'block_top_menu\']/ul/li[1]/ul/li[1]/ul/li[1]/a")).click();
		driver.navigate().back();
		driver.close();

		
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
