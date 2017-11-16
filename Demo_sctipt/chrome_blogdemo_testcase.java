package Demo;


import java.util.Date;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import UpUI.set_driver;
import UpUI.test_util;



public class chrome_blogdemo_testcase  {

	private Actions actions;
	private WebDriverWait wait;
	
	private WebElement we;
	private Date loadsttm;
	private Date loadendtm;
	private Date eventsttm;
	private Date eventendtm;
	private Date logstdate;
	private Date logeddate;
	private String wpt_yn;
	private String test_name;
	
	static ChromeDriver driver;
	static test_util tutil = new test_util();
	static set_driver set = new set_driver();
	
	public void test_st(String testnm, String wptyn, ChromeDriver testdriver) throws Exception {
		test_name = testnm;
		String testbaseUrl = tutil.get_init_parm(test_name, "testbaseUrl");
		
		wpt_yn = wptyn;
		driver = testdriver;
		actions = new Actions(driver);
		wait = new WebDriverWait(driver, 30);
		
		driver.get(testbaseUrl);
		driver.manage().window().maximize();
		
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("daumContent")));
		/*
		driver.switchTo().frame("loginForm");
	    driver.findElement(By.id("id")).clear();
	    driver.findElement(By.id("id")).sendKeys(tutil.get_init_parm(test_name, "id"));
	    driver.findElement(By.id("inputPwd")).clear();

	    driver.findElement(By.id("inputPwd")).sendKeys(tutil.get_init_parm(test_name, "pw"));
	    driver.findElement(By.id("loginSubmit")).click();
	    driver.switchTo().defaultContent();
	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("daumWrap")));
		*/
	    wait.until(ExpectedConditions.elementToBeClickable(By.id("q")));
	    driver.findElement(By.id("q")).clear();
	    driver.findElement(By.id("q")).sendKeys("enwide");
	    driver.findElement(By.cssSelector("button.ico_pctop.btn_search")).click();
	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("daumContent")));


	    
	}

	public void test_blogdemo(int cnt, int test_cnt, int i, int t_cnt, String user_url) throws Exception  {
			
			logstdate = tutil.get_time();
			tutil.cnt_logging_start(t_cnt, logstdate);
			
			
			
			if(t_cnt == 1) {
				loadsttm = tutil.get_time();
				driver.findElement(By.linkText("자연&사람")).click();
				String parentWindow = driver.getWindowHandle();
				
				Set<String> handles =  driver.getWindowHandles();
				
				for(String winHandle : handles){
					if(!winHandle.equals(parentWindow))
				          {
				          driver.switchTo().window(winHandle);
				          }
				}
			}else {
				 we = driver.findElement(By.linkText("홈"));
				 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
				 loadsttm = tutil.get_time();
				 actions.click(we).perform();
				
			}
			 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("wrapMid")));
			 loadendtm = tutil.get_time();
			 
			 tutil.cnt_logging_text(" 조회 내용 : "+tutil.get_parm(test_name, t_cnt, "linkname"));
			 we = driver.findElement(By.partialLinkText(tutil.get_parm(test_name, t_cnt, "linkname")));
		
			 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);

			 Thread.sleep(500);
			 eventsttm= tutil.get_time();
			 actions.click(we).perform();
			 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("content")));
			 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("daum_like_wrapper")));

			 eventendtm =tutil.get_time();
			// Thread.sleep(500);
			 

			logeddate  = tutil.get_time();
			
			tutil.cnt_logging_end(logeddate);
			
			/* 스크립트 종료 */
			if(wpt_yn.equals("Y")){
				set.proc_test_result(i);
				tutil.Write_csvfile_y("", t_cnt, cnt+1,  i, loadsttm, loadendtm, eventsttm, eventendtm, logstdate, logeddate, user_url);
			}else{
				tutil.Write_csvfile_n("", t_cnt, loadsttm, loadendtm, eventsttm, eventendtm, logstdate, logeddate);
			}

			 we = driver.findElement(By.className("daum_like_wrapper"));
			 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
			// Thread.sleep(500);
			
	}
	
}

