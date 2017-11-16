package UpUI;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;

public class win_upui_info extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */


	/**
	 * Create the dialog.
	 */
	public win_upui_info() {
		setVisible(true);
		setTitle("UpUI Information");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 228, 434, 1);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 10, 410, 219);
			getContentPane().add(scrollPane);
			{
				JTextArea txtrUpui = new JTextArea();
				scrollPane.setViewportView(txtrUpui);
				txtrUpui.setForeground(new Color(255, 255, 255));
				txtrUpui.setBackground(Color.DARK_GRAY);
				txtrUpui.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
				txtrUpui.setEditable(false);
				txtrUpui.setText("  [ Up!UI \uBC84\uC804 : V 1.0 ]\r\n      selenium-java-3.5.3\r\n      WebPageTest 2.19\r\n      chromedriver 2.31\r\n      IEDriverServer 3.5.1 \r\n      JDK 1.8 \uC774\uC0C1\r\n\r\n  [ UpUI \uD300 ]\r\n     \uC720\uD615\uC6B4 / \uC218\uC11D (\uD1B5\uC2E0\uC778\uD504\uB77C\uC11C\uBE44\uC2A4\uD300) \r\n     \uD669\uC624\uD604 / \uC218\uC11D (\uD1B5\uC2E0\uC778\uD504\uB77C\uC11C\uBE44\uC2A4\uD300)\r\n     \uC774\uC9C0\uC219 / \uC218\uC11D (\uBBF8\uB514\uC5B4\uC0AC\uC5C5\uD300)\r\n     \uC2EC\uD615\uBCF4 / \uC218\uC11D (\uD1B5\uC2E0\uC778\uD504\uB77C\uC11C\uBE44\uC2A4\uD300)");
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 229, 434, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancel_perform();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	protected void cancel_perform() {
		// TODO Auto-generated method stub
		this.dispose();
	}

}
