package Demo;


import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import UpUI.set_driver;
import UpUI.test_util;



public class iedemo_testcase  {

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
	
	static InternetExplorerDriver driver;
	static test_util tutil = new test_util();
	static set_driver set = new set_driver();
	
	public void test_st(String testnm, String wptyn, InternetExplorerDriver testdriver) throws Exception {
		test_name = testnm;
		String testbaseUrl = tutil.get_init_parm(test_name, "testbaseUrl");
		
		wpt_yn = wptyn;
		driver = testdriver;
		actions = new Actions(driver);
		wait = new WebDriverWait(driver, 30);
		
		driver.get(testbaseUrl);
		//driver.manage().window().maximize();
		
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("PM_ID_ct")));
	    driver.findElement(By.id("id")).clear();
	  
	    tutil.cnt_logging_text("id : "+tutil.get_init_parm(test_name, "id"));
	    driver.findElement(By.id("id")).sendKeys(tutil.get_init_parm(test_name, "id"));
	    driver.findElement(By.id("pw")).clear();
	    tutil.cnt_logging_text("pw : "+tutil.get_init_parm(test_name, "pw"));
	    driver.findElement(By.id("pw")).sendKeys(tutil.get_init_parm(test_name, "pw"));
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //driver.findElement(By.xpath("//input[@value='로그인']")).click();
	}
	                  //    ( 현재 10자리 수 k, 10나머지1자리총수 cnt, 현재 10이하 자리 i, 테스트 수행수 t_cnt, user_url);
	public void test_demo(int cnt, int test_cnt, int i, int t_cnt, String user_url) throws Exception  {
			
			logstdate = tutil.get_time();
			tutil.cnt_logging_start(t_cnt, logstdate);
			loadsttm = tutil.get_time();
			
			
			if(t_cnt != 1) {
				driver.navigate().back();
			}
			
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("PM_ID_ct")));
			loadendtm = tutil.get_time();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("query")));
			driver.findElement(By.id("query")).clear();
		    driver.findElement(By.id("query")).sendKeys(tutil.get_parm(test_name, t_cnt, "query"));
		    driver.findElement(By.id("search_btn")).click();
		    eventsttm= tutil.get_time();
		    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("container")));
			eventendtm =tutil.get_time();
			
			logeddate  = tutil.get_time();
			
			tutil.cnt_logging_text("int cnt, int test_cnt, int i, int t_cnt : "+cnt+","+ test_cnt+","+i+","+ t_cnt);
			tutil.cnt_logging_end(logeddate);
			
			Thread.sleep(1000);
			/* 스크립트 종료 */
			if(wpt_yn.equals("Y")){
				set.proc_test_result(i);
				Thread.sleep(1000);
				tutil.Write_csvfile_y(test_name, t_cnt, cnt+1,  i, loadsttm, loadendtm, eventsttm, eventendtm, logstdate, logeddate, user_url);
			}else{
				tutil.Write_csvfile_n(test_name, t_cnt, loadsttm, loadendtm, eventsttm, eventendtm, logstdate, logeddate);
			}
			
			
	}
	
}

