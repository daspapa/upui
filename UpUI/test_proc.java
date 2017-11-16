package UpUI;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.json.JSONObject;

public class test_proc extends Thread {
	private static String test_name;
	private static String test_exp;
	static set_driver set = new set_driver();
	static test_util tutil = new test_util();
	static List<HashMap<Integer, String>> reslist = new ArrayList<HashMap<Integer, String>>();
	static String wpt_yn;
	static String browser_type = "W";
	static ChromeDriver chromedriver;
	static InternetExplorerDriver iedriver;
	private static String user_url = "";
	String[] args;
	JTextArea rst_Area;
	JProgressBar pBar_ing;
	JButton btn_test;

	public test_proc(String[] parms, JTextArea textArea, JProgressBar progressBar_ing, JButton btn_testrun) {
		// TODO Auto-generated constructor stub
		args = parms;
		rst_Area = textArea;
		pBar_ing = progressBar_ing;
		btn_test = btn_testrun;
	}

	public void creat_test_result(int test_cnt) {
		JSONObject td = null;
		try {
			String testbaseUrl = tutil.get_init_parm(test_name, "testbaseUrl");
			td = set.creat_test_result(testbaseUrl, test_cnt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user_url = td.getJSONObject("data").getString("userUrl");
	}

	public void get_rst() {
		String rst_url = null;
		try {
			rst_url = set.get_rst_url();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tutil.rst_logging(rst_url);
	}

	@Override
	public void run() {
		// test_proc test = new test_proc();

		Date logdt = new Date();
		String testday;
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
		testday = sd.format(logdt);
		// testcase testcase = null ;
		Method test_st_mtd = null;
		Method test_mtd = null;
		Class[] test_st_args = new Class[3];
		Class[] test_args = new Class[5];
		Object test_ob = null;
		int test_cnt = 0;

		if (args.length != 6 && args.length != 7) {
			rst_Area.append("입력 파라메터 오류 : 파라메터 수\n");
			btn_test.setEnabled(true);
			return;
		} else {
			if (args.length == 7) {
				browser_type = args[6];
				if (!browser_type.equals("C") && !browser_type.equals("W") && !browser_type.equals("I")) {
					rst_Area.append("입력 파라메터 오류 : 브라우져 모드\n");
					btn_test.setEnabled(true);
					return;
				}
			}
			String pkgNM = args[0];
			String group_name = args[1];
			test_name = args[2];
			String test_class_name = null;
			// if(browser_type.equals("I")){
			// test_class_name = group_name.toLowerCase()+"_testcase_ie";
			// }else{
			test_class_name = group_name + "_testcase";
			// }
			String test_case_name = "test_" + test_name;
			try {
				// ClassLoader cd = new ClassLoader();
				// Class<?> test_class = Class.forName(pkgNM+"."+test_class_name, true,
				// ClassLoader.getSystemClassLoader());

				// Class<?> test_class = cd.loadClass(pkgNM+"."+test_class_name);

				// Class<?> test_class = Class.forName(pkgNM+"."+test_class_name);
				// test_ob = test_class.newInstance();
				test_ob = new Reloader().loadClass(pkgNM + "." + test_class_name).newInstance();
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				rst_Area.append("입력 파라메터 오류 : 대상 테스트 그룹 없음\n");
				rst_Area.append(sw.toString() + "\n");
				rst_Area.append("=====================================================\n");
				// rst_Area.append(e.getCause().toString()+"\n");

				btn_test.setEnabled(true);
				return;
			}

			test_st_args[0] = String.class;
			test_st_args[1] = String.class;
			if (browser_type.equals("I")) {
				test_st_args[2] = InternetExplorerDriver.class;
			} else {
				test_st_args[2] = ChromeDriver.class;
			}
			test_args[0] = Integer.TYPE;
			test_args[1] = Integer.TYPE;
			test_args[2] = Integer.TYPE;
			test_args[3] = Integer.TYPE;
			test_args[4] = String.class;
			try {
				test_st_mtd = test_ob.getClass().getDeclaredMethod("test_st", test_st_args);
				test_mtd = test_ob.getClass().getDeclaredMethod(test_case_name, test_args);
			} catch (Exception e) {

				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));

				rst_Area.append("입력 파라메터 오류 : 대상 테스트 케이스 없음\n");
				rst_Area.append(sw.toString() + "\n");
				rst_Area.append("=====================================================\n");
				// rst_Area.append(e.getCause().toString()+"\n");

				btn_test.setEnabled(true);
				return;

			}

			test_exp = args[3];
			try {
				test_cnt = Integer.parseInt(args[4]);
				if (test_cnt < 1) {
					// System.out.println("입력 파라메터 오류 : 테스트 카운트 오류(0 이하 값 입력)");
					rst_Area.append("입력 파라메터 오류 : 테스트 카운트 오류(0 이하 값 입력)\n");
					btn_test.setEnabled(true);

					return;

				}
			} catch (Exception e) {
				rst_Area.append("입력 파라메터 오류 : 테스트 카운트 오류(숫자 아님)\n");
				btn_test.setEnabled(true);
				return;
			}
			wpt_yn = args[5];
			if (!wpt_yn.equals("Y") && !wpt_yn.equals("N") && !wpt_yn.equals("L")) {
				rst_Area.append("입력 파라메터 오류 : 테스트 모드\n");
				btn_test.setEnabled(true);

				return;

			}

		}

		try {
			tutil.Set_logfile(test_name + "_" + test_exp + "_" + testday, rst_Area, pBar_ing);

			tutil.Start_logging(test_name + "_" + test_exp, test_cnt);
			if (!browser_type.equals("I")) {
				chromedriver = set.set_ChromeDriver(wpt_yn, browser_type);
			} else {
				iedriver = set.set_IeDriver(wpt_yn, browser_type);
			}

			tutil.Set_csvfile(test_name + "_" + test_exp + "_" + testday);
			// try {
			tutil.start_localperffile(test_name + "_" + test_exp + "_" + testday, browser_type);
			// int tot_cnt = test_cnt;
			int proc_cnt = test_cnt / 10;
			int cnt = test_cnt % 10;
			int t_cnt = 0;
			if (wpt_yn.equals("N")) {
				proc_cnt = 0;
				cnt = test_cnt;
			}
			if (browser_type.equals("I")) {
				test_st_mtd.invoke(test_ob, test_name, wpt_yn, iedriver);
			} else {
				test_st_mtd.invoke(test_ob, test_name, wpt_yn, chromedriver);
			}

			// testcase.test_st(wpt_yn, driver);

			for (int k = 0; k < proc_cnt + 1; k++) {
				if (test_cnt == 0) {
					break;
				} else if (test_cnt < 10 || wpt_yn.equals("N")) {
					if (wpt_yn.equals("Y"))
						creat_test_result(cnt);
					for (int i = 1; i <= cnt; i++) {
						t_cnt = (k == 0) ? i : (k * 10) + i;
						test_mtd.invoke(test_ob, k, cnt, i, t_cnt, user_url);
					}
					if (wpt_yn.equals("Y"))
						get_rst();
				} else if (test_cnt >= 10) {
					if (wpt_yn.equals("Y"))
						creat_test_result(10);
					for (int i = 1; i <= 10; i++) {
						t_cnt = (k == 0) ? i : (k * 10) + i;
						test_mtd.invoke(test_ob, k, 10, i, t_cnt, user_url);
					}
					if (wpt_yn.equals("Y"))
						get_rst();
					test_cnt -= 10;
				}
			}

		} catch (Exception e) {
			tutil.stop_localperffile();
			tutil.Error_logging(e);

			btn_test.setEnabled(true);

			if (wpt_yn.equals("Y")) {
				get_rst();
				creat_test_result(-1);
				set.proc_test_result(-1);
				get_rst();
			}
			set.err_res(test_name + "_" + test_exp + "_" + testday);
			// btn_test.setEnabled(true);
		} finally {
			tutil.End_logging(test_name + "_" + test_exp);
			if (!wpt_yn.equals("N"))
				tutil.userResult(test_name + "_" + test_exp + "_" + testday);
			// if(wpt_yn.equals("Y")) set.proc_test_noresult();
			set.quit_driver();
			tutil.stop_localperffile();
			btn_test.setEnabled(true);

		}
		// return;
	}
}