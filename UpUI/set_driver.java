package UpUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class set_driver {
	static ChromeDriver chromedriver;
	static InternetExplorerDriver iedriver;
	DesiredCapabilities caps;
	LoggingPreferences logPrefs = new LoggingPreferences();
	static JSONObject testDescriptor = null;
	static wpt_proc proc = new wpt_proc();
	static test_util tutil = new test_util();
	int log_on = 0;
	static String browsertype = null;

	public ChromeDriver set_ChromeDriver(String wpt_yn, String browser_type) throws Exception {
		browsertype = browser_type;
		// ChromeDriverService chrome_svc = new
		// ChromeDriverService.Builder().usingDriverExecutable(new
		// File("P:/Windows/System32/chromedriver.exe")).usingAnyFreePort().build();
		// chrome_svc.start();
		Logger tlog = tutil.get_logger();
		// System.setProperty("webdriver.chrome.driver",
		// "P:/TestLog/ref/chromedriver.exe");

		System.setProperty("webdriver.chrome.driver", tutil.get_props("chrdrvpath"));
		System.setProperty("webdriver.chrome.logfile", tutil.get_props("chrdrvlogpath"));
		if (tutil.get_props("chromedriverlog") == null || tutil.get_props("chromedriverlog").equals("N")) {
			System.setProperty("webdriver.chrome.args", "--disable-logging");
			System.setProperty("webdriver.chrome.silentOutput", "true");
		}
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("no-sandbox");
		caps = DesiredCapabilities.chrome();
		if (browsertype.equals("W")) {

			// caps.setCapability(ChromeOptions.CAPABILITY, options);

			options.setBinary(tutil.get_props("wbrowserpath"));
			if (tutil.get_props("wbrowser_args") != null && !tutil.get_props("wbrowser_args").equals("")) {
				options.addArguments(tutil.get_props("wbrowser_args"));
			}
			// options.addArguments("//MR//W-Browser");
			caps.setCapability(ChromeOptions.CAPABILITY, options);
		} else if (browsertype.equals("C")) {
			if (tutil.get_props("chromebrowser_args") != null && !tutil.get_props("chromebrowser_args").equals("")) {
				options.addArguments(tutil.get_props("chromebrowser_args"));
			}
		}

		if (!wpt_yn.equals("N")) {
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			log_on = 1;
		} else {

			logPrefs.enable(LogType.BROWSER, Level.OFF);
			logPrefs.enable(LogType.PERFORMANCE, Level.OFF);
			logPrefs.enable(LogType.CLIENT, Level.OFF);
			logPrefs.enable(LogType.DRIVER, Level.OFF);
			// logPrefs.enable(LogType.PROFILER, Level.OFF);
			// logPrefs.enable(LogType.SERVER, Level.OFF);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

		}

		chromedriver = new ChromeDriver(caps);

		Capabilities actualCaps = ((HasCapabilities) chromedriver).getCapabilities();
		// System.out.println("[set_Driver_log] Actual caps: " + actualCaps);
		tlog.info("================================================");
		tlog.info("[Driver setting Info] Actual caps: " + actualCaps);
		tlog.info("================================================");
		return chromedriver;
	}

	public InternetExplorerDriver set_IeDriver(String wpt_yn, String browser_type) throws Exception {
		browsertype = browser_type;
		// ChromeDriverService chrome_svc = new
		// ChromeDriverService.Builder().usingDriverExecutable(new
		// File("P:/Windows/System32/chromedriver.exe")).usingAnyFreePort().build();
		// chrome_svc.start();
		Logger tlog = tutil.get_logger();
		// System.setProperty("webdriver.chrome.driver",
		// "P:/TestLog/ref/chromedriver.exe");

		System.setProperty("webdriver.ie.driver", tutil.get_props("iedrvpath"));
		System.setProperty("webdriver.ie.logfile", tutil.get_props("iedrvlogpath"));

		System.setProperty("webdriver.ie.args", "--disable-logging");
		System.setProperty("webdriver.ie.silentOutput", "true");

		caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		
		/* 64 bit 시 추가 필요 (32bit 드라이버 사용시 불필요)
		* caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		* caps.setCapability("requireWindowFocus", true);
		*/
		
		if (!wpt_yn.equals("N")) {
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			log_on = 1;
		} else {
			logPrefs.enable(LogType.BROWSER, Level.OFF);
			logPrefs.enable(LogType.PERFORMANCE, Level.OFF);
			logPrefs.enable(LogType.CLIENT, Level.OFF);
			logPrefs.enable(LogType.DRIVER, Level.OFF);
			// logPrefs.enable(LogType.PROFILER, Level.OFF);
			// logPrefs.enable(LogType.SERVER, Level.OFF);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

		}

		iedriver = new InternetExplorerDriver(caps);

		Capabilities actualCaps = ((HasCapabilities) iedriver).getCapabilities();
		// System.out.println("[set_Driver_log] Actual caps: " + actualCaps);
		tlog.info("================================================");
		tlog.info("[Driver setting Info] Actual caps: " + actualCaps);
		tlog.info("================================================");
		return iedriver;
	}

	public ChromeDriver set_chromeDriverInProc() throws Exception {
		ChromeDriver bchromedriver;
		System.setProperty("webdriver.chrome.driver", tutil.get_props("chrdrvpath"));
		System.setProperty("webdriver.chrome.logfile", tutil.get_props("chrdrvlogpath"));

		System.setProperty("webdriver.chrome.args", "--disable-logging");
		System.setProperty("webdriver.chrome.silentOutput", "true");

		DesiredCapabilities bcaps = DesiredCapabilities.chrome();
		LoggingPreferences blogPrefs = new LoggingPreferences();
		blogPrefs.enable(LogType.BROWSER, Level.OFF);
		blogPrefs.enable(LogType.PERFORMANCE, Level.OFF);
		blogPrefs.enable(LogType.CLIENT, Level.OFF);
		blogPrefs.enable(LogType.DRIVER, Level.OFF);
		bcaps.setCapability(CapabilityType.LOGGING_PREFS, blogPrefs);

		bchromedriver = new ChromeDriver(bcaps);
		return bchromedriver;
	}

	public InternetExplorerDriver set_ieDriverInProc() throws Exception {
		InternetExplorerDriver biedriver;
		DesiredCapabilities bcaps = DesiredCapabilities.chrome();
		LoggingPreferences blogPrefs = new LoggingPreferences();

		System.setProperty("webdriver.ie.driver", tutil.get_props("iedrvpath"));
		System.setProperty("webdriver.ie.logfile", tutil.get_props("iedrvlogpath"));

		System.setProperty("webdriver.ie.args", "--disable-logging");
		System.setProperty("webdriver.ie.silentOutput", "true");

		bcaps = DesiredCapabilities.internetExplorer();
		bcaps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);

		blogPrefs.enable(LogType.BROWSER, Level.OFF);
		blogPrefs.enable(LogType.PERFORMANCE, Level.OFF);
		blogPrefs.enable(LogType.CLIENT, Level.OFF);
		blogPrefs.enable(LogType.DRIVER, Level.OFF);
		bcaps.setCapability(CapabilityType.LOGGING_PREFS, blogPrefs);

		biedriver = new InternetExplorerDriver(caps);

		return biedriver;
	}

	public JSONObject creat_test_result(String testurl, int test_cnt) throws Exception {
		testDescriptor = proc.createwpTest("Test", testurl, test_cnt);
		// System.out.println("#############: "+testDescriptor.toString());
		return testDescriptor;
	}

	public void quit_driver() {
		if (browsertype.equals("I")) {
			iedriver.close();
			iedriver.quit();
			Process findpc = null;
			try {
				findpc = Runtime.getRuntime().exec("tasklist");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(findpc.getInputStream()));
			String line;
			int rtn = 0;
			try {
				while ((line = reader.readLine()) != null) {
					if (line.contains("IEDriverServer")) {
						rtn = 1;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (rtn == 1) {
				Process killpc;
				try {
					killpc = new ProcessBuilder("cmd", "/c", "taskkill /F /IM IEDriverServer*").start();
					killpc.waitFor();
					killpc.destroyForcibly();
					killpc.waitFor();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			chromedriver.close();
			chromedriver.quit();

			Process findpc = null;
			try {
				findpc = Runtime.getRuntime().exec("tasklist");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(findpc.getInputStream()));
			String line;
			int rtn = 0;
			try {
				while ((line = reader.readLine()) != null) {

					if (line.contains("chromedriver")) {
						rtn = 1;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (rtn == 1) {
				Process killpc;
				try {
					killpc = new ProcessBuilder("cmd", "/c", "taskkill /F /IM chromedriver*").start();
					killpc.waitFor();
					killpc.destroyForcibly();
					killpc.waitFor();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void quit_chromedriverInProc(ChromeDriver bchromedriver) throws Exception {
		bchromedriver.close();
		bchromedriver.quit();
	}

	public void quit_iedriverInProc(InternetExplorerDriver biedriver) throws Exception {
		biedriver.close();
		biedriver.quit();
	}

	public void proc_test_result(int cnt) {
		// public void proc_test_result() throws Exception {
		Logger tlog = tutil.get_logger();
		Logs logs = null;
		if (browsertype.equals("I")) {
			logs = iedriver.manage().logs();
		} else {
			logs = chromedriver.manage().logs();
		}

		// System.out.println("Log types: " + logs.getAvailableLogTypes());
		if (cnt < 0) {
			tlog.info("================================================");
			tlog.info(" *에러 Result");
		} else if (cnt == 0) {
			tlog.info("================================================");
			tlog.info(" *초기 Result");
		} else {
			tlog.info("================================================");
			tlog.info(" *" + cnt + " 차  Result");
		}
		try {
			if (tutil.get_props("browserlogtypeYN").equals("Y"))
				printLog(LogType.BROWSER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// submitPerformanceResult(logs.get(LogType.PERFORMANCE).getAll());
		try {
			submitPerformanceResult(logs.get(LogType.PERFORMANCE).getAll(), cnt);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void proc_test_noresult() throws Exception {
		// public void proc_test_result() throws Exception {
		Logs logs = null;
		if (browsertype.equals("I")) {
			logs = iedriver.manage().logs();
		} else {
			logs = chromedriver.manage().logs();
		}
		logs.get(LogType.PERFORMANCE).getAll();
		logs.get(LogType.BROWSER).getAll();
	}

	static void printLog(String type) {
		Logger tlog = tutil.get_logger();
		List<LogEntry> entries = null;
		if (browsertype.equals("I")) {
			entries = iedriver.manage().logs().get(type).getAll();
		} else {
			entries = chromedriver.manage().logs().get(type).getAll();
		}

		tlog.info(" ** BROWSER log entries : " + entries.size());
		tlog.info("------------------------------------------------");
		// System.out.println(entries.size() + " " + type + " log entries
		// found");
		for (LogEntry entry : entries) {
			tlog.info("   -" + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
			// System.out.println(
			// new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " +
			// entry.getMessage());
		}
	}

	public void submitPerformanceResult(List<LogEntry> perfLogEntries, int cnt)
			// public void submitPerformanceResult(List<LogEntry>
			// perfLogEntries)
			throws IOException, JSONException {
		Logger tlog = tutil.get_logger();
		JSONArray devToolsLog = new JSONArray();
		tlog.info(" *submitPerformanceResult");
		tlog.info(" ** performance log entries : " + perfLogEntries.size());
		tlog.info("================================================");
		// System.out.println(perfLogEntries.size() + " performance log entries
		// found");

		for (LogEntry entry : perfLogEntries) {
			JSONObject message = new JSONObject(entry.getMessage());
			JSONObject devToolsMessage = message.getJSONObject("message");
			devToolsLog.put(devToolsMessage);
			if (cnt < 0) {
				tlog.info("   -" + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
			}
		}

		int test_cnt = (cnt <= 0) ? 1 : cnt;
		byte[] screenshot = null;
		if (browsertype.equals("I")) {
			screenshot = ((TakesScreenshot) iedriver).getScreenshotAs(OutputType.BYTES);
		} else {
			screenshot = ((TakesScreenshot) chromedriver).getScreenshotAs(OutputType.BYTES);
		}
		proc.postResult(testDescriptor, devToolsLog, screenshot, test_cnt);
		// proc.postResult(testDescriptor,devToolsLog, screenshot);
		// driver.manage().logs().get(LogType.PERFORMANCE).getAll().removeAll(driver.manage().logs().get(LogType.PERFORMANCE).getAll());
	}

	public String get_rst_url() throws Exception {
		String res = "RESULT URL : " + testDescriptor.getJSONObject("data").getString("userUrl");
		return res;
	}

	public void err_res(String testName) {

		String TestLogPath = null;
		try {
			TestLogPath = tutil.get_props("testlogpath");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (log_on == 1) {
			JSONArray devToolsLog = new JSONArray();
			List<LogEntry> perfLogEntries = null;
			if (browsertype.equals("I")) {
				perfLogEntries = iedriver.manage().logs().get(LogType.PERFORMANCE).getAll();
			} else {
				perfLogEntries = chromedriver.manage().logs().get(LogType.PERFORMANCE).getAll();
			}
			Logger tlog = tutil.get_logger();
			for (LogEntry entry : perfLogEntries) {
				JSONObject message = new JSONObject(entry.getMessage());
				JSONObject devToolsMessage = message.getJSONObject("message");
				devToolsLog.put(devToolsMessage);
				tlog.info("   -" + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
			}
			FileWriter fw = null;
			try {
				fw = new FileWriter(TestLogPath + testName + "/err_perflog_" + testName + ".json");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			devToolsLog.write(fw);
		}
		byte[] screenshot = null;
		try {
			if (browsertype.equals("I")) {
				screenshot = ((TakesScreenshot) iedriver).getScreenshotAs(OutputType.BYTES);
			} else {
				screenshot = ((TakesScreenshot) chromedriver).getScreenshotAs(OutputType.BYTES);
			}
		} catch (Exception e) {

		}
		if (screenshot != null) {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(TestLogPath + testName + "/err_screenshot_" + testName + ".png");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.write(screenshot);
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("err_rst_test_end");
	}

}
