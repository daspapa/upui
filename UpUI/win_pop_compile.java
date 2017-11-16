package UpUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class win_pop_compile extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textsrcfile;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public win_pop_compile() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		// this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// this.setVisible(true);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 420, 376);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		textsrcfile = new JTextField();
		textsrcfile.setBounds(63, 10, 262, 21);
		textsrcfile.setText("");
		contentPanel.add(textsrcfile);
		textsrcfile.setColumns(10);

		JLabel lblNewLabel = new JLabel("\uB300\uC0C1\uD30C\uC77C");
		lblNewLabel.setBounds(12, 13, 57, 15);
		contentPanel.add(lblNewLabel);

		JButton btnNewButton = new JButton("\uCC3E\uAE30");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane jop = new JOptionPane();
				JFileChooser jfc = new JFileChooser(".\\");
				FileNameExtensionFilter ff = new FileNameExtensionFilter("java file", "java");
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.addChoosableFileFilter(ff);
				jfc.showOpenDialog(jop);

				File selectfile = jfc.getSelectedFile();
				if (selectfile != null) {
					textsrcfile.setText(selectfile.getAbsolutePath());
				}
			}
		});
		btnNewButton.setBounds(329, 9, 64, 23);
		contentPanel.add(btnNewButton);

		JTextArea textcompilerst = new JTextArea();
		// textcompilerst.setBounds(0, 0, 4, 24);
		// contentPanel.add(textcompilerst);
		JScrollPane scrollPane_rst = new JScrollPane(textcompilerst);
		scrollPane_rst.setBounds(12, 41, 380, 254);
		scrollPane_rst.setWheelScrollingEnabled(true);
		contentPanel.add(scrollPane_rst);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Compile");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				command_compile(textcompilerst);
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("\uC644\uB8CC");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel_perform();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		setVisible(true);
	}

	protected void cancel_perform() {
		// TODO Auto-generated method stub
		this.dispose();
	}

	private Process pc = null;

	protected void command_compile(JTextArea textcompilerst) {
		// TODO Auto-generated method stub
		File classesdir = new File(".\\classes");
		if (!classesdir.isDirectory()) {
			classesdir.mkdir();
		}
		textcompilerst.setText("");
		String commend;
		// commend = "javac -classpath .\\test_ui_ver2.jar -d .\\classes
		// .\\src\\prm3_testcase.java";
		// commend = "javac -classpath .\\classes\\*.jar -d .\\classes
		// "+textsrcfile.getText();
		commend = "javac -classpath .\\classes\\*.jar -d .\\classes " + textsrcfile.getText();

		textcompilerst.append("=============================================================\n");
		textcompilerst.append("Command : [" + commend + "]\n");
		textcompilerst.append("=============================================================\n");
		textcompilerst.append("\n");
		try {
			pc = new ProcessBuilder("cmd", "/c", commend).start();
			BufferedReader br_out = new BufferedReader(new InputStreamReader(pc.getInputStream()));
			BufferedReader br_err = new BufferedReader(new InputStreamReader(pc.getErrorStream()));
			String line1 = null;
			while ((line1 = br_err.readLine()) != null) {
				textcompilerst.append(line1 + "\n");
				textcompilerst.setCaretPosition(textcompilerst.getDocument().getLength());
			}
			String line2 = null;
			while ((line2 = br_out.readLine()) != null) {
				System.out.println("[br_out]");
				System.out.println(line2);
				textcompilerst.append(line2 + "\n");
				textcompilerst.setCaretPosition(textcompilerst.getDocument().getLength());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
