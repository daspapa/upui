package UpUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import java.awt.List;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class win_main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField text_expl;
	private JTextField text_cnt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					win_main frame = new win_main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public win_main() throws Exception {
		setResizable(false);
		setTitle("UpUI ver 1.0 (build 2017.09.04)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setSize(600, 600);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 584, 188);
		panel_2.setBorder(new LineBorder(Color.GRAY));
		panel.add(panel_2);
		panel_2.setLayout(null);
		
				JLabel lb_CI = new JLabel("       U p  U I");
				lb_CI.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						new win_upui_info();
					}
				});
				lb_CI.setForeground(new Color(255, 69, 0));
				lb_CI.setVerticalTextPosition(SwingConstants.BOTTOM);
				lb_CI.setVerticalAlignment(SwingConstants.BOTTOM);
				lb_CI.setFont(new Font("휴먼매직체", Font.BOLD, 18));
				lb_CI.setIcon(new ImageIcon(win_main.class.getResource("/UpUI/ci.png")));
				lb_CI.setBounds(43, 6, 265, 29);
				panel_2.add(lb_CI);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setBounds(16, 36, 360, 1);
		panel_2.add(lblNewLabel);

		JLabel lb_package = new JLabel("Package");
		lb_package.setBounds(15, 45, 78, 21);
		panel_2.add(lb_package);

		Vector<String> vc = new Vector<String>();
		// vc.add("선택");
		JComboBox<String> combo_package = new JComboBox<String>(vc);

		combo_package.setBounds(137, 46, 171, 21);
		panel_2.add(combo_package);

		JLabel lb_test_type = new JLabel("Test \uAD6C\uBD84(Class)");
		lb_test_type.setBounds(321, 46, 104, 21);
		panel_2.add(lb_test_type);

		JComboBox<String> combo_class = new JComboBox<String>();

		combo_class.setBounds(429, 46, 143, 21);

		panel_2.add(combo_class);

		JLabel lb_test_method = new JLabel("Test \uD654\uBA74(Method)");
		lb_test_method.setBounds(15, 76, 110, 21);
		panel_2.add(lb_test_method);

		JComboBox<String> combo_method = new JComboBox<String>();
		combo_method.setBounds(137, 76, 171, 21);

		panel_2.add(combo_method);
		JLabel lb_test_expl = new JLabel("Test \uC124\uBA85(\uCD5C\uB30015\uC790)");
		lb_test_expl.setBounds(15, 105, 137, 21);
		panel_2.add(lb_test_expl);

		setpackageItem(combo_package, combo_class, combo_method);

		text_expl = new JTextField();
		text_expl.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				JTextField cmp = (JTextField) e.getSource();
				if (cmp.getText().length() > 15)
					e.consume();
			}
		});
		text_expl.setBounds(137, 105, 239, 21);
		panel_2.add(text_expl);
		text_expl.setColumns(10);

		JLabel lb_test_cnt = new JLabel("Test \uD69F\uC218");
		lb_test_cnt.setBounds(321, 76, 78, 21);
		panel_2.add(lb_test_cnt);

		text_cnt = new JTextField();
		text_cnt.setHorizontalAlignment(SwingConstants.RIGHT);
		text_cnt.setColumns(10);
		text_cnt.setBounds(429, 76, 106, 21);
		panel_2.add(text_cnt);

		JLabel lb_test_cnt_1 = new JLabel("\uD68C");
		lb_test_cnt_1.setBounds(547, 76, 25, 21);
		panel_2.add(lb_test_cnt_1);

		JLabel lb_test_brws = new JLabel("Browser");
		lb_test_brws.setBounds(15, 134, 110, 21);
		panel_2.add(lb_test_brws);

		ButtonGroup bg_brws = new ButtonGroup();

		JRadioButton rdo_brws_1 = new JRadioButton("W-Browser");
		rdo_brws_1.setFont(new Font("굴림", Font.PLAIN, 11));
		rdo_brws_1.setActionCommand("W");
		rdo_brws_1.setBounds(304, 136, 89, 23);
		panel_2.add(rdo_brws_1);

		JRadioButton rdo_brws_2 = new JRadioButton("Chrome");
		rdo_brws_2.setSelected(true);
		rdo_brws_2.setFont(new Font("굴림", Font.PLAIN, 11));
		rdo_brws_2.setActionCommand("C");
		rdo_brws_2.setBounds(129, 135, 78, 23);
		panel_2.add(rdo_brws_2);

		JRadioButton rdo_brws_3 = new JRadioButton("i-Explorer");
		rdo_brws_3.setFont(new Font("굴림", Font.PLAIN, 11));
		rdo_brws_3.setActionCommand("I");
		rdo_brws_3.setBounds(221, 135, 81, 23);
		panel_2.add(rdo_brws_3);

		bg_brws.add(rdo_brws_1);
		bg_brws.add(rdo_brws_2);
		bg_brws.add(rdo_brws_3);

		ButtonGroup bg_rst = new ButtonGroup();

		JLabel lb_test_rst = new JLabel("\uACB0\uACFC\uCC98\uB9AC");
		lb_test_rst.setBounds(15, 159, 95, 21);
		panel_2.add(lb_test_rst);

		JRadioButton rdo_rst_1 = new JRadioButton("Local Only");
		rdo_rst_1.setFont(new Font("굴림", Font.PLAIN, 11));
		rdo_rst_1.setSelected(true);
		rdo_rst_1.setActionCommand("N");
		rdo_rst_1.setBounds(129, 157, 89, 23);
		panel_2.add(rdo_rst_1);

		JRadioButton rdo_rst_2 = new JRadioButton("WPT \uC5F0\uB3D9");
		rdo_rst_2.setFont(new Font("굴림", Font.PLAIN, 11));
		rdo_rst_2.setActionCommand("Y");
		rdo_rst_2.setBounds(222, 157, 86, 23);
		panel_2.add(rdo_rst_2);

		JRadioButton rdo_rst_3 = new JRadioButton("\uACB0\uACFC\uD30C\uC77C \uC804\uC1A1");
		rdo_rst_3.setFont(new Font("굴림", Font.PLAIN, 11));
		rdo_rst_3.setActionCommand("L");
		rdo_rst_3.setBounds(304, 157, 104, 23);
		panel_2.add(rdo_rst_3);

		bg_rst.add(rdo_rst_1);
		bg_rst.add(rdo_rst_2);
		bg_rst.add(rdo_rst_3);

		rdo_brws_3.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					rdo_rst_1.setSelected(true);
					rdo_rst_2.setEnabled(false);
					rdo_rst_3.setEnabled(false);
				} else if (e.getStateChange() == 2) {
					rdo_rst_2.setEnabled(true);
					rdo_rst_3.setEnabled(true);
				}
			}
		});

		JButton btn_compile = new JButton("Compile");
		btn_compile.setBounds(393, 10, 89, 22);
		panel_2.add(btn_compile);

		JButton btn_testrun = new JButton("Test \uC2E4\uD589");

		btn_testrun.setBounds(410, 129, 95, 51);
		panel_2.add(btn_testrun);

		JLabel label = new JLabel("( \uD3F4\uB354 \uBC0F \uD30C\uC77C\uBA85\uC5D0 \uD3EC\uD568 )");
		label.setBounds(381, 105, 154, 21);
		panel_2.add(label);

		JButton btnReload = new JButton("Reload");
		btnReload.setFont(new Font("굴림", Font.PLAIN, 12));
		btnReload.setForeground(Color.BLACK);
		btnReload.setBounds(483, 10, 89, 22);
		panel_2.add(btnReload);

		JButton btnClear = new JButton("Clear");
		btnClear.setForeground(Color.BLACK);
		btnClear.setFont(new Font("굴림", Font.PLAIN, 12));
		btnClear.setBounds(508, 129, 72, 25);
		panel_2.add(btnClear);

		JButton btnStop = new JButton("Stop");
		btnStop.setForeground(Color.BLACK);
		btnStop.setFont(new Font("굴림", Font.PLAIN, 12));
		btnStop.setBounds(508, 155, 72, 25);
		panel_2.add(btnStop);

		btn_compile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// JPopupMenu jop_compile = new JPopupMenu();
				new win_pop_compile();
			}
		});

		/*
		 * combo_package.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mousePressed(MouseEvent e) {
		 * setpackageItem(combo_package); } });
		 */

		combo_package.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e1) {

				try {
					if (e1.getStateChange() == 1) {
						changeclassItem(combo_class, e1.getItem());
					}
					// setMethodItem(combo_method, e.getItem());
				} catch (Exception ex1) {
					// TODO Auto-generated catch block
					ex1.printStackTrace();
				}
			}
		});

		combo_class.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e2) {

				try {
					if (e2.getStateChange() == 1) {
						changeMethodItem(combo_method, e2.getItem());
					}

				} catch (Exception ex2) {
					// TODO Auto-generated catch block
					ex2.printStackTrace();
				}
			}
		});

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 186, 584, 346);
		panel.add(panel_3);
		panel_3.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setBounds(12, 41, 560, 215);
		// panel_3.add(textArea);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 21, 584, 302);
		panel_3.add(scrollPane);

		JProgressBar progressBar = new JProgressBar(); // 중간 확인용
		progressBar.setBounds(0, 5, 584, 14);
		progressBar.setBorderPainted(false);
		panel_3.add(progressBar);

		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 40));
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(null);

		JProgressBar progressBar_ing = new JProgressBar();
		progressBar_ing.setStringPainted(true);
		progressBar_ing.setString("");
		progressBar_ing.setBounds(0, 3, 584, 20);
		progressBar_ing.setBorderPainted(true);
		panel_1.add(progressBar_ing);

		JLabel lblNewLabel_1 = new JLabel("Copyright by SK(\uC8FC) C&C SOT UpUI\uD300 ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 8));
		lblNewLabel_1.setBounds(381, 25, 203, 15);
		panel_1.add(lblNewLabel_1);

		btnReload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// System.out.println(e.toString());
					setpackageItem(combo_package, combo_class, combo_method);
					combo_package.setSelectedIndex(0);
					combo_class.removeAllItems();
					combo_method.removeAllItems();
					combo_class.addItem("선 택");
					combo_method.addItem("선 택");
					text_cnt.setText("");
					text_expl.setText("");
					textArea.setText("");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btn_testrun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List parmlist = new List();
				btn_testrun.setEnabled(false);
				if (combo_package.getSelectedItem().equals("선 택") || combo_package.getSelectedItem().equals("")) {
					textArea.append("테스트 Pakage를 선택하세요!!\n");
					btn_testrun.setEnabled(true);
					return;
				} else if (combo_class.getSelectedItem().equals("선 택") || combo_class.getSelectedItem().equals("")) {
					textArea.append("테스트 Class를 선택하세요!!\n");
					btn_testrun.setEnabled(true);
					return;
				} else if (combo_method.getSelectedItem().equals("선 택") || combo_method.getSelectedItem().equals("")) {
					textArea.append("테스트 Method를 선택하세요!!\n");
					btn_testrun.setEnabled(true);
					return;
				}

				parmlist.removeAll();
				parmlist.add(combo_package.getSelectedItem().toString());
				parmlist.add(combo_class.getSelectedItem().toString());
				parmlist.add(combo_method.getSelectedItem().toString());
				parmlist.add(text_expl.getText().replaceAll(" ", "_"));
				parmlist.add(text_cnt.getText());
				parmlist.add(bg_rst.getSelection().getActionCommand());
				parmlist.add(bg_brws.getSelection().getActionCommand());
				// btn_testrun.setEnabled(false);

				run_test(parmlist, textArea, progressBar_ing, progressBar, btn_testrun);
				// btn_testrun.setEnabled(true);
			}
		});

		btn_testrun.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("enabled")) {
					if (evt.getNewValue().equals(false)) {
						btn_compile.setEnabled(false);
						btnReload.setEnabled(false);
						combo_package.setEnabled(false);
						combo_class.setEnabled(false);
						combo_method.setEnabled(false);
						text_cnt.setEnabled(false);
						text_expl.setEnabled(false);
						btnClear.setEnabled(false);
						rdo_brws_1.setEnabled(false);
						rdo_brws_2.setEnabled(false);
						rdo_brws_3.setEnabled(false);
						rdo_rst_1.setEnabled(false);
						rdo_rst_2.setEnabled(false);
						rdo_rst_3.setEnabled(false);
						progressBar.setIndeterminate(true);
					} else if (evt.getNewValue().equals(true)) {
						btn_compile.setEnabled(true);
						btnReload.setEnabled(true);
						combo_package.setEnabled(true);
						combo_class.setEnabled(true);
						combo_method.setEnabled(true);
						text_cnt.setEnabled(true);
						text_expl.setEnabled(true);
						btnClear.setEnabled(true);
						rdo_brws_1.setEnabled(true);
						rdo_brws_2.setEnabled(true);
						rdo_brws_3.setEnabled(true);
						rdo_rst_1.setEnabled(true);
						rdo_rst_2.setEnabled(true);
						rdo_rst_3.setEnabled(true);
						progressBar.setIndeterminate(false);
					}

				}
			}
		});

		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				combo_package.setSelectedIndex(0);
				combo_class.removeAllItems();
				combo_method.removeAllItems();
				combo_class.addItem("선 택");
				combo_method.addItem("선 택");
				text_cnt.setText("");
				text_expl.setText("");
				textArea.setText("");
				progressBar.setIndeterminate(false);

				progressBar_ing.setMinimum(0);
				progressBar_ing.setMinimum(0);
				progressBar_ing.setValue(0);
				progressBar_ing.setString("");
			}
		});

		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				stop_thread(btn_testrun);
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop_thread(btn_testrun);
				// System.exit(0);
			}
		});
	}

	Thread test_th;

	protected void run_test(List parmlist, JTextArea textArea, JProgressBar progressBar_ing, JProgressBar progressBar,
			JButton btn_testrun) {
		// TODO Auto-generated method stub

		String[] args = parmlist.getItems();
		progressBar_ing.setMinimum(0);
		try {
			progressBar_ing.setMaximum(Integer.parseInt(parmlist.getItem(4)));
		} catch (Exception e) {
			textArea.append("테스트 회수는 숫자로 입력하세요!!!!\n");
			btn_testrun.setEnabled(true);
			return;
		}
		progressBar_ing.setValue(0);
		progressBar_ing.setString("");
		textArea.setText("");
		// progressBar_ing.setForeground(Color.ORANGE);
		btn_testrun.setEnabled(false);
		// test_rst run = new test_rst();
		test_proc proc = new test_proc(args, textArea, progressBar_ing, btn_testrun);
		test_th = new Thread(proc);
		test_th.start();
		// Thread th2 = new Thread(run);
		// th2.start();
	}

	protected void stop_thread(JButton btn_testrun) {
		// TODO Auto-generated method stub
		if (test_th != null) {
			test_th.interrupt();
			try {
				test_th.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!btn_testrun.isEnabled()) {
			btn_testrun.setEnabled(true);
		}

	}

	/*
	 * class test_rst implements Runnable { JTextArea rst_textArea; JProgressBar
	 * rst_progressBar;
	 * 
	 * public test_rst(JTextArea textArea, JProgressBar progressBar) { rst_textArea
	 * = textArea; rst_progressBar = progressBar; rst_textArea.setText("");
	 * rst_progressBar.removeAll(); } public void run() {
	 * rst_textArea.append("test\n");
	 * rst_progressBar.setForeground(Color.DARK_GRAY);
	 * rst_progressBar.setBorderPainted(true);
	 * rst_progressBar.setIndeterminate(true); while(true){ try {
	 * Thread.sleep(1000); } catch (InterruptedException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } }
	 * 
	 * } }
	 */
	// private String packdir= null;
	private File findclassdir = null;
	private File[] findclassdirs = null;
	private HashMap<Object, Object> packageMap = new HashMap<>();

	private void setpackageItem(JComboBox<String> combopackage, JComboBox<String> comboclass,
			JComboBox<String> combo_method) throws Exception {
		// combopackage.removeAllItems();
		// TODO Auto-generated method stub
		// combopackage.removeAllItems();
		combopackage.removeAllItems();
		comboclass.removeAllItems();
		combo_method.removeAllItems();

		combopackage.addItem("선 택");
		comboclass.addItem("선 택");
		combo_method.addItem("선 택");
		findclassdir = new File("classes");
		if (!findclassdir.isDirectory()) {
			findclassdir.mkdir();
		}
		findclassdirs = findclassdir.listFiles();

		String packageName = null;
		for (int k = 0; k < findclassdirs.length; k++) {
			if (findclassdirs[k].isDirectory()) {
				packageName = findclassdirs[k].getName();

				combopackage.addItem(packageName);

				packageMap.put(packageName, setclassItem(comboclass, combo_method, packageName));
			}
		}
		combopackage.setMaximumRowCount(findclassdirs.length + 1);
		// System.out.println("packageMap \n ["+packageMap+"]");
		// System.out.println("ClassMap \n ["+ClassMap+"]");
	}

	private File findclass = null;

	private File[] findclassfiles = null;
	private HashMap<Object, Object> ClassMap = new HashMap<>();

	private ArrayList<String> setclassItem(JComboBox<String> comboclass, JComboBox<String> combo_method,
			String packageName) throws Exception {
		findclass = new File("classes/" + packageName);
		findclassfiles = findclass.listFiles();
		String className = null;
		ArrayList<String> ClassList = new ArrayList<String>();
		for (int i = 0; i < findclassfiles.length; i++) {
			// System.out.println("test : ["+findclassfiles[i].getAbsolutePath()+"]");
			if (findclassfiles[i].getName().endsWith("testcase.class")) {
				className = findclassfiles[i].getName().replaceAll("_testcase.class", "");
				// comboclass.addItem(className);
				ClassList.add(className);
				ClassMap.put(className, setMethodItem(combo_method, packageName, className));
			}
		}
		// System.out.println("ClassList \n ["+ClassList+"]");
		return ClassList;
	}

	private ArrayList<String> setMethodItem(JComboBox<String> combo_method, String combo_package, String className)
			throws Exception {
		// TODO Auto-generated method stub

		// new Reloader().loadClass(classfile).newInstance();

		// Class<?> test_class = cd.loadClass(combo_package+"."+ className +
		// "_testcase");

		// Class<?> test_class = Class.forName (combo_package+"."+ className +
		// "_testcase");
		// Class<?> test_class = Class.forName(combo_package+"."+ className +
		// "_testcase", true, this.getClass().getClassLoader());
		// Class<?> test_class = Class.forName(combo_package+"."+ className +
		// "_testcase", false, ClassLoader.getSystemClassLoader());
		// Object test_ob = test_class.newInstance();
		Object test_ob = new Reloader().loadClass(combo_package + "." + className + "_testcase").newInstance();
		Method[] methodItems = test_ob.getClass().getMethods();
		String MathodName = null;
		ArrayList<String> MathodList = new ArrayList<String>();

		for (int j = 0; j < methodItems.length; j++) {
			if (methodItems[j].getName().startsWith("test_") && !methodItems[j].getName().startsWith("test_st")) {
				MathodName = methodItems[j].getName().replaceAll("test_", "");
				MathodList.add(MathodName);
				// combo_method.addItem(MathodName);
			}
		}

		return MathodList;
	}

	ArrayList<?> classItems;

	protected void changeclassItem(JComboBox<String> combo_class, Object item) {
		// TODO Auto-generated method stub
		combo_class.removeAllItems();
		// combo_class.addItem("선 택");

		classItems = (ArrayList<?>) packageMap.get(item.toString());
		if (classItems != null) {
			for (int l = 0; l < classItems.size(); l++) {
				combo_class.addItem(classItems.get(l).toString());
				// System.out.println("***********"+classItems.get(l).toString());
			}
			combo_class.setMaximumRowCount(classItems.size() + 1);
		}
	}

	ArrayList<?> methodItems;

	protected void changeMethodItem(JComboBox<String> combo_method, Object item) {
		// TODO Auto-generated method stub
		combo_method.removeAllItems();
		// combo_method.addItem("선 택");

		methodItems = (ArrayList<?>) ClassMap.get(item.toString());
		if (methodItems != null) {
			for (int l = 0; l < methodItems.size(); l++) {
				combo_method.addItem(methodItems.get(l).toString());
			}
			combo_method.setMaximumRowCount(methodItems.size() + 1);
		}
	}
}

class Reloader extends ClassLoader {
	@Override
	public Class<?> loadClass(String classname) {
		return findClass(classname);

	}

	@Override
	public Class<?> findClass(String classname) {
		try {
			byte[] bytes = loadClassData(classname);
			return defineClass(classname, bytes, 0, bytes.length);
		} catch (IOException ioe) {
			try {
				return super.loadClass(classname);
			} catch (ClassNotFoundException cnfe) {
			}
			ioe.printStackTrace(System.out);
			return null;
		}
	}

	private byte[] loadClassData(String classname) throws IOException {
		File classFile = new File("classes/" + classname.replaceAll("\\.", "/") + ".class");
		// System.out.println("6----------- "+ "classes/" + classname.replaceAll("\\.",
		// "/")+".class");
		int Size = (int) classFile.length();
		byte buff[] = new byte[Size];
		FileInputStream fis = new FileInputStream(classFile);
		DataInputStream dis = new DataInputStream(fis);
		dis.readFully(buff);
		dis.close();
		return buff;
	}
}
