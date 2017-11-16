package UpUI;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class test_util {
	private static Logger logg;
	private static Logger csvlogg;
	private static FileHandler fh;
	private static FileHandler csvfh;
	private HashMap<String, Integer> parm_nm_map = new HashMap<String, Integer>();
	private int chk_test = 0;
	private String datafileName = null;
	private ArrayList<String> parm_ln = new ArrayList<String>();
	private BufferedReader br = null;
	private HashMap<String, String> init_parm_nm_map = new HashMap<String, String>();

	private Process pc = null;
	private static JTextArea rst_Area;
	private static JProgressBar pBar_ing;
	private File testDir;
	static Properties prop = null;

	public String get_props(String Key) throws IOException {
		if (prop == null) {
			prop = new Properties();
			FileInputStream fis = new FileInputStream("set.properties");
			prop.load(new BufferedInputStream(fis));
		}
		return prop.getProperty(Key);
	}

	public void Set_logfile(String testname, JTextArea textArea, JProgressBar progressBar_ing)
			throws SecurityException, IOException {
		rst_Area = textArea;
		pBar_ing = progressBar_ing;
		logg = Logger.getLogger("logfile");
		String testlogpath = get_props("testlogpath");
		File testlogDir = new File(testlogpath);
		if (!testlogDir.exists())
			testlogDir.mkdir();
		testDir = new File(testlogpath + testname);
		if (!testDir.exists())
			testDir.mkdir();

		fh = new FileHandler(testlogpath + testname + "/TestLog_" + testname + ".log", true);
		logg.addHandler(fh);
		CustomFromatter fm = new CustomFromatter();
		fh.setFormatter(fm);
		logg.setUseParentHandlers(false);

	}

	public void Set_csvfile(String testname) throws SecurityException, IOException {
		csvlogg = Logger.getLogger("csvfile");
		String testlogpath = get_props("testlogpath");
		// testDir = new File(testlogpath+testname);

		// if(!testDir.exists()) testDir.mkdir();

		csvfh = new FileHandler(testlogpath + testname + "/TestLog_" + testname + ".csv", true);
		csvlogg.addHandler(csvfh);
		CustomFromatter fm = new CustomFromatter();
		csvfh.setFormatter(fm);
		csvlogg.setUseParentHandlers(false);
	}

	public Logger get_logger() {
		return logg;
	}

	public Logger getcsv_logger() {
		return csvlogg;
	}

	public void Write_csvfile_y(String test_name, int t_cnt, int cnt, int i, Date loadsttm, Date loadendtm,
			Date eventsttm, Date eventendtm, Date logstdate, Date logeddate, String user_url)
			throws SecurityException, IOException {

		Logger tcsv;

		if (t_cnt == 1) {
			tcsv = getcsv_logger();
			tcsv.info("테스트명,차수,테스트타임,로드타임,이벤트타임,계,테스트시작,테스트종료,로드시작,로드종료,이벤트시작,이벤트종료,WPT 차수,WPT run,WPT URL");
		} else {
			tcsv = getcsv_logger();
		}

		SimpleDateFormat tsd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String logsttime = null;
		String logedtime = null;
		String loadsttime = null;
		String loadendtime = null;
		String eventsttime = null;
		String eventendtime = null;
		try {
			logsttime = tsd.format(logstdate);
		} catch (Throwable e1) {
			System.out.println(t_cnt + " loadsttm Time is Null");
		}
		try {
			logedtime = tsd.format(logeddate);
		} catch (Throwable e2) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e2;
		}
		try {
			loadsttime = tsd.format(loadsttm);
		} catch (Throwable e3) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e3;
		}
		try {
			loadendtime = tsd.format(loadendtm);
		} catch (Throwable e4) {
			System.out.println(t_cnt + " loadendtime Time is Null");
			throw e4;
		}
		try {
			eventsttime = tsd.format(eventsttm);
		} catch (Throwable e5) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e5;
		}
		try {
			eventendtime = tsd.format(eventendtm);
		} catch (Throwable e6) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e6;
		}

		Long logtime = logeddate.getTime() - logstdate.getTime();
		Long loadtime = loadendtm.getTime() - loadsttm.getTime();
		Long eventtime = eventendtm.getTime() - eventsttm.getTime();
		Long totval = loadtime + eventtime;

		totval = loadtime + eventtime;
		tcsv.info(test_name + "," + t_cnt + "," + logtime + "," + loadtime + "," + eventtime + "," + totval + ","
				+ logsttime + "," + logedtime + "," + loadsttime + "," + loadendtime + "," + eventsttime + ","
				+ eventendtime + "," + cnt + "회," + i + "차," + user_url);
	}

	public void Write_csvfile_n(String test_name, int t_cnt, Date loadsttm, Date loadendtm, Date eventsttm,
			Date eventendtm, Date logstdate, Date logeddate) throws Exception {
		Logger tcsv;

		if (t_cnt == 1) {
			tcsv = getcsv_logger();
			tcsv.info("테스트명,차수,테스트타임,로드타임,이벤트타임,계,테스트시작,테스트종료,로드시작,로드종료,이벤트시작,이벤트종료");
		} else {
			tcsv = getcsv_logger();
		}

		SimpleDateFormat tsd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String logsttime = null;
		String logedtime = null;
		String loadsttime = null;
		String loadendtime = null;
		String eventsttime = null;
		String eventendtime = null;
		try {
			logsttime = tsd.format(logstdate);
		} catch (Throwable e1) {
			System.out.println(t_cnt + " loadsttm Time is Null");
		}
		try {
			logedtime = tsd.format(logeddate);
		} catch (Throwable e2) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e2;
		}
		try {
			loadsttime = tsd.format(loadsttm);
		} catch (Throwable e3) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e3;
		}
		try {
			loadendtime = tsd.format(loadendtm);
		} catch (Throwable e4) {
			System.out.println(t_cnt + " loadendtime Time is Null");
			throw e4;
		}
		try {
			eventsttime = tsd.format(eventsttm);
		} catch (Throwable e5) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e5;
		}
		try {
			eventendtime = tsd.format(eventendtm);
		} catch (Throwable e6) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e6;
		}

		Long logtime = logeddate.getTime() - logstdate.getTime();
		Long loadtime = loadendtm.getTime() - loadsttm.getTime();
		Long eventtime = eventendtm.getTime() - eventsttm.getTime();
		Long totval = loadtime + eventtime;

		tcsv.info(test_name + "," + t_cnt + "," + logtime + "," + loadtime + "," + eventtime + "," + totval + ","
				+ logsttime + "," + logedtime + "," + loadsttime + "," + loadendtime + "," + eventsttime + ","
				+ eventendtime);
	}

	public void Write_csvfile2_y(String test_name, int t_cnt, int cnt, int i, Date loadsttm, Date loadendtm,
			Date eventsttm1, Date eventendtm1, Date eventsttm2, Date eventendtm2, Date logstdate, Date logeddate,
			String user_url) throws SecurityException, IOException {

		Logger tcsv;

		if (t_cnt == 1) {
			tcsv = getcsv_logger();
			tcsv.info(
					"테스트명,차수,테스트타임,로드타임,이벤트타임1,이벤트타임2,계,테스트시작,테스트종료,로드시작,로드종료,이벤트1시작,이벤트1종료,이벤트2시작,이벤트2종료,WPT 차수,WPT run,WPT URL");
		} else {
			tcsv = getcsv_logger();
		}

		SimpleDateFormat tsd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String logsttime = null;
		String logedtime = null;
		String loadsttime = null;
		String loadendtime = null;
		String eventsttime1 = null;
		String eventendtime1 = null;
		String eventsttime2 = null;
		String eventendtime2 = null;
		try {
			logsttime = tsd.format(logstdate);
		} catch (Throwable e1) {
			System.out.println(t_cnt + " loadsttm Time is Null");
		}
		try {
			logedtime = tsd.format(logeddate);
		} catch (Throwable e2) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e2;
		}
		try {
			loadsttime = tsd.format(loadsttm);
		} catch (Throwable e3) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e3;
		}
		try {
			loadendtime = tsd.format(loadendtm);
		} catch (Throwable e4) {
			System.out.println(t_cnt + " loadendtime Time is Null");
			throw e4;
		}
		try {
			eventsttime1 = tsd.format(eventsttm1);
		} catch (Throwable e5) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e5;
		}
		try {
			eventendtime1 = tsd.format(eventendtm1);
		} catch (Throwable e6) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e6;
		}
		try {
			eventsttime2 = tsd.format(eventsttm2);
		} catch (Throwable e7) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e7;
		}
		try {
			eventendtime2 = tsd.format(eventendtm2);
		} catch (Throwable e8) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e8;
		}

		Long logtime = logeddate.getTime() - logstdate.getTime();
		Long loadtime = loadendtm.getTime() - loadsttm.getTime();
		Long eventtime1 = eventendtm1.getTime() - eventsttm1.getTime();
		Long eventtime2 = eventendtm2.getTime() - eventsttm2.getTime();
		Long totval = loadtime + eventtime1 + eventtime2;

		// totval = loadtime + eventtime;
		tcsv.info(test_name + "," + t_cnt + "," + logtime + "," + loadtime + "," + eventtime1 + "," + eventtime2 + ","
				+ totval + "," + logsttime + "," + logedtime + "," + loadsttime + "," + loadendtime + "," + eventsttime1
				+ "," + eventendtime1 + "," + eventsttime2 + "," + eventendtime2 + "," + cnt + "회," + i + "차,"
				+ user_url);
	}

	public void Write_csvfile2_n(String test_name, int t_cnt, Date loadsttm, Date loadendtm, Date eventsttm1,
			Date eventendtm1, Date eventsttm2, Date eventendtm2, Date logstdate, Date logeddate)
			throws SecurityException, IOException {
		Logger tcsv;

		if (t_cnt == 1) {
			tcsv = getcsv_logger();
			tcsv.info("테스트명,차수,테스트타임,로드타임,이벤트타임1,이벤트타임2,계,테스트시작,테스트종료,로드시작,로드종료,이벤트시작1,이벤트종료1,이벤트시작2,이벤트종료2");
		} else {
			tcsv = getcsv_logger();
		}

		SimpleDateFormat tsd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String logsttime = null;
		String logedtime = null;
		String loadsttime = null;
		String loadendtime = null;
		String eventsttime1 = null;
		String eventendtime1 = null;
		String eventsttime2 = null;
		String eventendtime2 = null;
		try {
			logsttime = tsd.format(logstdate);
		} catch (Throwable e1) {
			System.out.println(t_cnt + " loadsttm Time is Null");
		}
		try {
			logedtime = tsd.format(logeddate);
		} catch (Throwable e2) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e2;
		}
		try {
			loadsttime = tsd.format(loadsttm);
		} catch (Throwable e3) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e3;
		}
		try {
			loadendtime = tsd.format(loadendtm);
		} catch (Throwable e4) {
			System.out.println(t_cnt + " loadendtime Time is Null");
			throw e4;
		}
		try {
			eventsttime1 = tsd.format(eventsttm1);
		} catch (Throwable e5) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e5;
		}
		try {
			eventendtime1 = tsd.format(eventendtm1);
		} catch (Throwable e6) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e6;
		}
		try {
			eventsttime2 = tsd.format(eventsttm2);
		} catch (Throwable e7) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e7;
		}
		try {
			eventendtime2 = tsd.format(eventendtm2);
		} catch (Throwable e8) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e8;
		}

		Long logtime = logeddate.getTime() - logstdate.getTime();
		Long loadtime = loadendtm.getTime() - loadsttm.getTime();
		Long eventtime1 = eventendtm1.getTime() - eventsttm1.getTime();
		Long eventtime2 = eventendtm2.getTime() - eventsttm2.getTime();
		Long totval = loadtime + eventtime1 + eventtime2;

		tcsv.info(test_name + "," + t_cnt + "," + logtime + "," + loadtime + "," + eventtime1 + "," + eventtime2 + ","
				+ totval + "," + logsttime + "," + logedtime + "," + loadsttime + "," + loadendtime + "," + eventsttime1
				+ "," + eventendtime1 + "," + eventsttime2 + "," + eventendtime2);
	}

	public void Write_csvfile3_y(String test_name, int t_cnt, int cnt, int i, Date loadsttm, Date loadendtm,
			Date eventsttm1, Date eventendtm1, Date eventsttm2, Date eventendtm2, Date eventsttm3, Date eventendtm3,
			Date logstdate, Date logeddate, String user_url) throws SecurityException, IOException {

		Logger tcsv;

		if (t_cnt == 1) {
			tcsv = getcsv_logger();
			tcsv.info(
					"테스트명,차수,테스트타임,로드타임,이벤트타임1,이벤트타임2,이벤트타임3,계,테스트시작,테스트종료,로드시작,로드종료,이벤트1시작,이벤트1종료,이벤트2시작,이벤트2종료,이벤트3시작,이벤트3종료,WPT 차수,WPT run,WPT URL");
		} else {
			tcsv = getcsv_logger();
		}

		SimpleDateFormat tsd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String logsttime = null;
		String logedtime = null;
		String loadsttime = null;
		String loadendtime = null;
		String eventsttime1 = null;
		String eventendtime1 = null;
		String eventsttime2 = null;
		String eventendtime2 = null;
		String eventsttime3 = null;
		String eventendtime3 = null;

		try {
			logsttime = tsd.format(logstdate);
		} catch (Throwable e1) {
			System.out.println(t_cnt + " loadsttm Time is Null");
		}
		try {
			logedtime = tsd.format(logeddate);
		} catch (Throwable e2) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e2;
		}
		try {
			loadsttime = tsd.format(loadsttm);
		} catch (Throwable e3) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e3;
		}
		try {
			loadendtime = tsd.format(loadendtm);
		} catch (Throwable e4) {
			System.out.println(t_cnt + " loadendtime Time is Null");
			throw e4;
		}
		try {
			eventsttime1 = tsd.format(eventsttm1);
		} catch (Throwable e5) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e5;
		}
		try {
			eventendtime1 = tsd.format(eventendtm1);
		} catch (Throwable e6) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e6;
		}
		try {
			eventsttime2 = tsd.format(eventsttm2);
		} catch (Throwable e7) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e7;
		}
		try {
			eventendtime2 = tsd.format(eventendtm2);
		} catch (Throwable e8) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e8;
		}
		try {
			eventsttime3 = tsd.format(eventsttm3);
		} catch (Throwable e9) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e9;
		}
		try {
			eventendtime3 = tsd.format(eventendtm3);
		} catch (Throwable e10) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e10;
		}

		Long logtime = logeddate.getTime() - logstdate.getTime();
		Long loadtime = loadendtm.getTime() - loadsttm.getTime();
		Long eventtime1 = eventendtm1.getTime() - eventsttm1.getTime();
		Long eventtime2 = eventendtm2.getTime() - eventsttm2.getTime();
		Long eventtime3 = eventendtm3.getTime() - eventsttm3.getTime();
		Long totval = loadtime + eventtime1 + eventtime2 + eventtime3;

		// totval = loadtime + eventtime;
		tcsv.info(test_name + "," + t_cnt + "," + logtime + "," + loadtime + "," + eventtime1 + "," + eventtime2 + ","
				+ eventtime3 + "," + totval + "," + logsttime + "," + logedtime + "," + loadsttime + "," + loadendtime
				+ "," + eventsttime1 + "," + eventendtime1 + "," + eventsttime2 + "," + eventendtime2 + ","
				+ eventsttime3 + "," + eventendtime3 + "," + cnt + "회," + i + "차," + user_url);
	}

	public void Write_csvfile3_n(String test_name, int t_cnt, Date loadsttm, Date loadendtm, Date eventsttm1,
			Date eventendtm1, Date eventsttm2, Date eventendtm2, Date eventsttm3, Date eventendtm3, Date logstdate,
			Date logeddate) throws SecurityException, IOException {

		Logger tcsv;

		if (t_cnt == 1) {
			tcsv = getcsv_logger();
			tcsv.info(
					"테스트명,차수,테스트타임,로드타임,이벤트타임1,이벤트타임2,이벤트타임3,계,테스트시작,테스트종료,로드시작,로드종료,이벤트시작1,이벤트종료1,이벤트시작2,이벤트종료2,이벤트시작3,이벤트종료3");
		} else {
			tcsv = getcsv_logger();
		}

		SimpleDateFormat tsd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String logsttime = null;
		String logedtime = null;
		String loadsttime = null;
		String loadendtime = null;
		String eventsttime1 = null;
		String eventendtime1 = null;
		String eventsttime2 = null;
		String eventendtime2 = null;
		String eventsttime3 = null;
		String eventendtime3 = null;

		try {
			logsttime = tsd.format(logstdate);
		} catch (Throwable e1) {
			System.out.println(t_cnt + " loadsttm Time is Null");
		}
		try {
			logedtime = tsd.format(logeddate);
		} catch (Throwable e2) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e2;
		}
		try {
			loadsttime = tsd.format(loadsttm);
		} catch (Throwable e3) {
			System.out.println(t_cnt + " loadsttime Time is Null");
			throw e3;
		}
		try {
			loadendtime = tsd.format(loadendtm);
		} catch (Throwable e4) {
			System.out.println(t_cnt + " loadendtime Time is Null");
			throw e4;
		}
		try {
			eventsttime1 = tsd.format(eventsttm1);
		} catch (Throwable e5) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e5;
		}
		try {
			eventendtime1 = tsd.format(eventendtm1);
		} catch (Throwable e6) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e6;
		}
		try {
			eventsttime2 = tsd.format(eventsttm2);
		} catch (Throwable e7) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e7;
		}
		try {
			eventendtime2 = tsd.format(eventendtm2);
		} catch (Throwable e8) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e8;
		}
		try {
			eventsttime3 = tsd.format(eventsttm3);
		} catch (Throwable e9) {
			System.out.println(t_cnt + " eventsttime Time is Null");
			throw e9;
		}
		try {
			eventendtime3 = tsd.format(eventendtm3);
		} catch (Throwable e10) {
			System.out.println(t_cnt + " enentendtime Time is Null");
			throw e10;
		}

		Long logtime = logeddate.getTime() - logstdate.getTime();
		Long loadtime = loadendtm.getTime() - loadsttm.getTime();
		Long eventtime1 = eventendtm1.getTime() - eventsttm1.getTime();
		Long eventtime2 = eventendtm2.getTime() - eventsttm2.getTime();
		Long eventtime3 = eventendtm3.getTime() - eventsttm3.getTime();
		Long totval = loadtime + eventtime1 + eventtime2 + eventtime3;

		tcsv.info(test_name + "," + t_cnt + "," + logtime + "," + loadtime + "," + eventtime1 + "," + eventtime2 + ","
				+ eventtime3 + "," + totval + "," + logsttime + "," + logedtime + "," + loadsttime + "," + loadendtime
				+ "," + eventsttime1 + "," + eventendtime1 + "," + eventsttime2 + "," + eventendtime2 + ","
				+ eventsttime3 + "," + eventendtime3);
	}

	public void Write_csvfile_list_y(String test_name, int t_cnt, int cnt, int i, ArrayList<Date> timelist,
			Date logstdate, Date logeddate, String user_url) throws SecurityException, IOException {

		Logger tcsv;
		int listcnt = timelist.size();

		if (t_cnt == 1) {
			String csv_title1 = "";
			String csv_title2 = "";
			tcsv = getcsv_logger();
			csv_title1 = "테스트명,차수,테스트타임";

			for (int c = 1; c <= listcnt / 2; c++) {
				csv_title1 = csv_title1 + ",이벤트타임" + c;
				csv_title2 = csv_title2 + ",이벤트시작" + c + ",이벤트종료" + c;
			}
			csv_title1 = csv_title1 + ",계,테스트시작,테스트종료" + csv_title2 + ",WPT 차수,WPT run,WPT URL";
			tcsv.info(csv_title1);
		} else {
			tcsv = getcsv_logger();
		}

		SimpleDateFormat tsd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String logsttime = "";
		String logedtime = "";
		String sttime = "";
		String endtime = "";
		Long elptime;
		Long totval = (long) 0;

		String csv_eltime = "";
		String csv_time = "";

		logsttime = tsd.format(logstdate);
		logedtime = tsd.format(logeddate);
		Long logtime = logeddate.getTime() - logstdate.getTime();
		for (int c = 0; c < listcnt; c = c + 2) {
			elptime = timelist.get(c + 1).getTime() - timelist.get(c).getTime();
			sttime = tsd.format(timelist.get(c));
			endtime = tsd.format(timelist.get(c + 1));
			csv_eltime = csv_eltime + "," + elptime.toString();
			csv_time = csv_time + "," + sttime + "," + endtime;
			totval = totval + elptime;
		}

		tcsv.info(test_name + "," + t_cnt + "," + logtime + csv_eltime + "," + totval + "," + logsttime + ","
				+ logedtime + csv_time + "," + cnt + "회," + i + "차," + user_url);
	}

	public void Write_csvfile_list_n(String test_name, int t_cnt, ArrayList<Date> timelist, Date logstdate,
			Date logeddate) throws SecurityException, IOException {

		Logger tcsv;
		int listcnt = timelist.size();

		if (t_cnt == 1) {
			String csv_title1 = "";
			String csv_title2 = "";
			tcsv = getcsv_logger();
			csv_title1 = "테스트명,차수,테스트타임";

			for (int c = 1; c <= listcnt / 2; c++) {
				csv_title1 = csv_title1 + ",이벤트타임" + c;
				csv_title2 = csv_title2 + ",이벤트시작" + c + ",이벤트종료" + c;
			}
			csv_title1 = csv_title1 + ",계,테스트시작,테스트종료" + csv_title2;
			tcsv.info(csv_title1);
		} else {
			tcsv = getcsv_logger();
		}

		SimpleDateFormat tsd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String logsttime = "";
		String logedtime = "";
		String sttime = "";
		String endtime = "";
		Long elptime;
		Long totval = (long) 0;

		String csv_eltime = "";
		String csv_time = "";

		logsttime = tsd.format(logstdate);
		logedtime = tsd.format(logeddate);
		Long logtime = logeddate.getTime() - logstdate.getTime();
		for (int c = 0; c < listcnt; c = c + 2) {
			elptime = timelist.get(c + 1).getTime() - timelist.get(c).getTime();
			sttime = tsd.format(timelist.get(c));
			endtime = tsd.format(timelist.get(c + 1));
			csv_eltime = csv_eltime + "," + elptime.toString();
			csv_time = csv_time + "," + sttime + "," + endtime;
			totval = totval + elptime;
		}

		tcsv.info(test_name + "," + t_cnt + "," + logtime + csv_eltime + "," + totval + "," + logsttime + ","
				+ logedtime + csv_time);
	}

	public Date get_time() {
		Date testdt = new Date();
		return testdt;
	}

	public void Error_logging(Exception e) {
		Logger tlog = get_logger();
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));

		SimpleDateFormat tsd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		tsd.format(get_time());

		tlog.info("!!!!!!!!!!!!!!!!!! 수행오류 [" + tsd.format(get_time()) + "]");
		tlog.info(sw.toString());
		tlog.info("!!!!!!!!!!!!!!!!!!");
		rst_Area.append("!!!!!!!!!!!!!!!!!! 수행오류 [" + tsd.format(get_time()) + "]\n");
		rst_Area.setCaretPosition(rst_Area.getDocument().getLength());
		rst_Area.append(sw.toString() + "\n");
		rst_Area.append("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");

	}

	public void Start_logging(String test_name, int test_cnt) {
		Date logdt = new Date();
		String teststdt;
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		teststdt = sd.format(logdt);

		Logger tlog = get_logger();
		tlog.info("################################################");
		tlog.info(test_name + " 테스트 시작 ");
		tlog.info(" * 테스트 시작 : " + teststdt);
		tlog.info(" * 테스트 건수 : " + test_cnt);
		tlog.info("################################################");

		rst_Area.append("################################################\n");
		rst_Area.append(test_name + " 테스트 시작 \n");
		rst_Area.append(" * 테스트 시작 : " + teststdt + "\n");
		rst_Area.append(" * 테스트 건수 : " + test_cnt + "\n");
		rst_Area.append("################################################\n");
		rst_Area.setCaretPosition(rst_Area.getDocument().getLength());

	}

	public void rst_logging(String rst_url) {
		Logger tlog = get_logger();
		tlog.info("################################################");
		tlog.info(" * " + rst_url);
		tlog.info("################################################");
		rst_Area.append("################################################\n");
		rst_Area.append(" * " + rst_url + "\n");
		rst_Area.append("################################################\n");
		rst_Area.setCaretPosition(rst_Area.getDocument().getLength());
	}

	public void cnt_logging_start(int t_cnt, Date logstdate) {
		Logger tlog = get_logger();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String logsttime = sd.format(logstdate);

		tlog.info(" * 테스트 Count : " + t_cnt);
		tlog.info("-----------------------------------------------");
		tlog.info("Start Time : " + logsttime);

		rst_Area.append(" * 테스트 Count : " + t_cnt + "\n");
		rst_Area.append("-----------------------------------------------\n");
		rst_Area.append("Start Time : " + logsttime + "\n");
		pBar_ing.setValue(t_cnt);
		pBar_ing.setString(t_cnt + " / " + pBar_ing.getMaximum());
		rst_Area.setCaretPosition(rst_Area.getDocument().getLength());

	}

	public void cnt_logging_end(Date logeddate) throws Exception {
		Logger tlog = get_logger();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String logedtime = sd.format(logeddate);
		tlog.info("End Time : " + logedtime);
		tlog.info("-----------------------------------------------");
		rst_Area.append("End Time : " + logedtime + "\n");
		rst_Area.append("-----------------------------------------------\n");
		rst_Area.setCaretPosition(rst_Area.getDocument().getLength());
	}

	public void cnt_logging_text(String log_mag) {
		Logger tlog = get_logger();

		tlog.info("[Log] : " + log_mag);
		rst_Area.append("[Log] : " + log_mag + "\n");
		rst_Area.setCaretPosition(rst_Area.getDocument().getLength());

	}

	public void End_logging(String test_name) {
		Logger tlog = get_logger();
		Date logenddt = new Date();
		String testendday;
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		testendday = sd.format(logenddt);
		tlog.info("################################################");
		tlog.info(test_name + " 테스트 종료");
		tlog.info(" * 테스트 종료시간 : " + testendday);
		tlog.info("################################################");

		rst_Area.append("################################################\n");
		rst_Area.append(test_name + " 테스트 종료\n");
		rst_Area.append(" * 테스트 종료시간 : " + testendday + "\n");
		rst_Area.append("################################################\n");
		rst_Area.setCaretPosition(rst_Area.getDocument().getLength());
		fh.close();
		csvfh.close();

	}

	public String get_init_parm(String testName, String parm_name) throws IOException {
		String[] init_parm_nms;
		String testdatepath = get_props("testdatepath");
		if (init_parm_nm_map.isEmpty()) {
			BufferedReader init_file_br = null;
			String initdatafileName = testName + "_init";
			// String initdatafileName = "init";
			init_file_br = new BufferedReader(new FileReader(testdatepath + initdatafileName + ".data"));
			String file_line = null;
			while ((file_line = init_file_br.readLine()) != null) {
				init_parm_nms = file_line.split("=");
				if (init_parm_nms.length == 2) {
					init_parm_nm_map.put(init_parm_nms[0], init_parm_nms[1]);
				} else {
					String init_parm_nm = "";
					for (int i = 1; i < init_parm_nms.length; i++) {
						if (i == 1) {
							init_parm_nm = init_parm_nms[i];
						} else {
							init_parm_nm = init_parm_nm + "=" + init_parm_nms[i];
						}
					}
					init_parm_nm_map.put(init_parm_nms[0], init_parm_nm);
				}

			}
			init_file_br.close();
		}

		String val_key = init_parm_nm_map.get(parm_name);
		return val_key;
	}

	public String get_parm(String testName, int chk, String parm_name) throws IOException {
		String parm_str = null;
		String[] parm_nms;
		String[] parm_vals;

		if (chk != chk_test) {
			if (br == null || datafileName != testName) {
				datafileName = testName;
				String testdatepath = get_props("testdatepath");
				br = new BufferedReader(new FileReader(testdatepath + testName + ".data"));
				parm_nms = br.readLine().split(",");
				for (int i = 0; i < parm_nms.length; i++) {
					parm_nm_map.put(parm_nms[i], i);
				}
			}
			if (parm_ln.size() == 0 || parm_ln.get(1) == null) {
				parm_ln.add(0, br.readLine());
				parm_vals = parm_ln.get(0).toString().split(",");
			} else {
				parm_ln.add(0, parm_ln.get(1).toString());
				parm_vals = parm_ln.get(0).toString().split(",");
			}
			int val_key = parm_nm_map.get(parm_name);
			parm_str = parm_vals[val_key];
			parm_ln.add(1, br.readLine());
			if (parm_ln.get(1) == null) {
				br.close();
				br = null;

			}
			chk_test = chk;
		} else {
			parm_vals = parm_ln.get(0).toString().split(",");
			int val_key = parm_nm_map.get(parm_name);
			parm_str = parm_vals[val_key];
		}

		return parm_str;
	}

	public void start_localperffile(String testName, String browser_type) throws IOException {
		String commend;
		String testlogpath = get_props("testlogpath");
		String perfcmd = get_props("perfcmd");
		if (null == perfcmd || perfcmd.equals("")) {
			if (browser_type.equals("W")) {
				commend = "typeperf \"\\process(W-*)\\% Processor Time\" \"\\processor(_Total)\\% Processor Time\" \"\\process(W-*)\\Working Set - Private\" \"\\memory\\Available Bytes\" -o "
						+ testlogpath + testName + "/localperf_" + testName + ".csv";
			} else if (browser_type.equals("C")) {
				commend = "typeperf \"\\process(chrome*)\\% Processor Time\" \"\\processor(_Total)\\% Processor Time\" \"\\process(chrome*)\\Working Set - Private\" \"\\memory\\Available Bytes\" -o "
						+ testlogpath + testName + "/localperf_" + testName + ".csv";
			} else if (browser_type.equals("I")) {
				commend = "typeperf \"\\process(iexplore*)\\% Processor Time\" \"\\processor(_Total)\\% Processor Time\" \"\\process(iexplore*)\\Working Set - Private\" \"\\memory\\Available Bytes\" -o "
						+ testlogpath + testName + "/localperf_" + testName + ".csv";
			} else {
				commend = "";
			}
		} else {
			commend = "typeperf " + get_props("perfcmd") + " -o " + testlogpath + testName + "/localperf_" + testName
					+ ".csv";
		}
		pc = new ProcessBuilder("cmd", "/c", commend).start();

	}

	public void stop_localperffile() {
		String commend1 = "taskkill /F /IM typeperf*";
		Process end_pc = null;
		try {
			end_pc = new ProcessBuilder("cmd", "/c", commend1).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			end_pc.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (pc != null) {
			pc.destroyForcibly();
			try {
				pc.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		end_pc.destroyForcibly();
		try {
			end_pc.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void userResult(String testName) {
		String wptbaseUrl = null;
		String testlogpath = null;
		try {
			wptbaseUrl = get_props("wptbaseUrl");
			testlogpath = get_props("testlogpath");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		URL userworkUrl = null;
		try {
			userworkUrl = new URL(wptbaseUrl + "user_work/user_fileupload.php");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String mimeBoundary = "-----CorrectBatteryHorseStaple";
		byte[] buff = new byte[1024];

		File TestlogDir = new File(testlogpath + testName);
		/*
		 * File TestcsvFile = new File(test_rootDir+testName+"/TestLog_" + testName +
		 * ".csv"); File LocalPerfcsvFile = new File(test_rootDir+testName+"/localperf_"
		 * + testName + ".csv"); File TestlogFile = new
		 * File(test_rootDir+testName+"/TestLog_" + testName + ".log");
		 */
		HttpURLConnection http = null;
		try {
			http = (HttpURLConnection) userworkUrl.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		http.setDoOutput(true);
		http.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + mimeBoundary);
		http.setChunkedStreamingMode(4096);
		OutputStream os = null;
		try {
			os = http.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(os, "UTF-8");
			writer.write("--");
			writer.write(mimeBoundary);
			writer.write("\r\n");
			writer.write("Content-Disposition: form-data; name=\"");
			writer.write("file");
			writer.write("\"; filename=\"");
			writer.write("UserTestLog_" + testName + ".zip");
			writer.write("\"\r\n");
			// writer.write("Content-Type: text/plain\r\n");
			writer.write("Content-Type: application/zip\r\n");
			writer.write("Content-Transfer-Encoding: binary\r\n\r\n");
			writer.flush();
			// Files.copy(csvFile.toPath(), os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ZipOutputStream zos = new ZipOutputStream(os);
		FileInputStream fis = null;
		if (TestlogDir.isDirectory()) {
			File[] fileArray = TestlogDir.listFiles();
			for (int i = 0; i < fileArray.length; i++) {
				if (fileArray[i].getName()
						.substring(fileArray[i].getName().length() - 3, fileArray[i].getName().length())
						.equals("lck")) {
					continue;
				}
				try {
					zos.putNextEntry(new ZipEntry(fileArray[i].getName()));
					fis = new FileInputStream(fileArray[i]);
					int len;
					while ((len = fis.read(buff)) > 0) {
						zos.write(buff, 0, len);
					}
					fis.close();
					zos.closeEntry();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/*
		 * if (TestcsvFile.exists()) { zos.putNextEntry(new ZipEntry("TestLog_" +
		 * testName + ".csv")); fis = new FileInputStream(TestcsvFile); int len; while((
		 * len = fis.read(buff)) > 0) { zos.write(buff, 0, len); } zos.closeEntry(); }
		 * 
		 * if (TestlogFile.exists()) { zos.putNextEntry(new ZipEntry("TestLog_" +
		 * testName + ".log")); fis = new FileInputStream(TestcsvFile); int len; while((
		 * len = fis.read(buff)) > 0) { zos.write(buff, 0, len); } zos.closeEntry(); }
		 * if (LocalPerfcsvFile.exists()) { zos.putNextEntry(new ZipEntry("localperf_" +
		 * testName + ".csv")); fis = new FileInputStream(TestcsvFile); int len; while((
		 * len = fis.read(buff)) > 0) { zos.write(buff, 0, len); } zos.closeEntry(); }
		 */
		try {
			zos.finish();
			writer.write("\r\n--");
			writer.write(mimeBoundary);
			writer.write("--\r\n");
			writer.flush();

			http.getInputStream().close();
			if (HttpURLConnection.HTTP_OK != http.getResponseCode()) {
				throw new IOException("User Result upload fail(" + testName + ") : " + http.getResponseCode() + " "
						+ http.getResponseMessage());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class CustomFromatter extends Formatter {
		@Override
		public String format(LogRecord record) {
			StringBuilder sb = new StringBuilder();
			sb.append(formatMessage(record)).append(System.getProperty("line.separator"));
			if (null != record.getThrown()) {
				sb.append("Throwable occurred: ");
				Throwable t = record.getThrown();
				PrintWriter pw = null;
				try {
					StringWriter sw = new StringWriter();
					pw = new PrintWriter(sw);
					t.printStackTrace(pw);
					sb.append(sw.toString());
				} finally {
					if (pw != null) {
						try {
							pw.close();
						} catch (Exception e) {
						}
					}
				}
			}
			return sb.toString();
		}
	}
}
