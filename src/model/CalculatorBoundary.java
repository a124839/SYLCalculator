package model;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author k570
 * 计算器界面
 *
 */
public class CalculatorBoundary {
	
	//操作數1，爲了程序安全，初值一定设置，设置为0
	String string1 = "0";
	//操作数2
	String string2 = "0";
	//运算符
	String signal = "+";
	//运算结果
	String result = "";
	
	
	//k1为状态开关，用于选择输入方向，将要写入string2
	int k1 = 1;
	//k2为状态开关，用于记录符号键的次数
	int k2 = 1;
	//标识String1是否可以被清零
	int k3 = 1;
	//标识String2是否可以被清零
	int k4 = 1;
	//标识小数点是否可用
	int k5 = 1;
	//用于记录是否连续按下符号键
	JButton store;
	//==========what's this????============
	Vector<Object> vt = new Vector<>(20,10);
	
	
	/**UI組件**/
	//主窗体，构造器为title
	JFrame calculatorFrame = new JFrame("Calculator");	
	//清除按钮
	JButton clear_Button = new JButton("clear");
	//创建一个 JTestField 对象并初始化。 JTestField 是用于显示操作和计算结果的文本框
	JTextField textField = new JTextField(20);
	//数字0-9
	JButton button_0 = new JButton("0");
	JButton button_1 = new JButton("1");
	JButton button_2 = new JButton("2");
	JButton button_3 = new JButton("3");
	JButton button_4 = new JButton("4");
	JButton button_5 = new JButton("5");
	JButton button_6 = new JButton("6");
	JButton button_7 = new JButton("7");
	JButton button_8 = new JButton("8");
	JButton button_9 = new JButton("9");
	JButton button_dot = new JButton(".");
	//数学运算
	JButton button_add = new JButton("+");
	JButton button_minus = new JButton("-");
	JButton button_mutiply = new JButton("*");
	JButton button_divid = new JButton("/");
	JButton button_cal = new JButton("=");
	
	public CalculatorBoundary(){
		//为按钮设置等效建，通过对应键盘按键替代点击
		button_0.setMnemonic(KeyEvent.VK_0);
		//设置文本框右对齐
		textField.setHorizontalAlignment(JTextField.RIGHT);
		// 轻量级框架
		JPanel calculatorPanel = new JPanel();
		// 设置该容器的布局为四行四列，边距为5像素
		calculatorPanel.setLayout(new GridLayout(4, 4, 5, 5));
		// 将用于计算的按钮添加到容器内
		calculatorPanel.add(button_0);
		calculatorPanel.add(button_1);
		calculatorPanel.add(button_2);
		calculatorPanel.add(button_3);
		calculatorPanel.add(button_4);
		calculatorPanel.add(button_5);
		calculatorPanel.add(button_6);
		calculatorPanel.add(button_7);
		calculatorPanel.add(button_8);
		calculatorPanel.add(button_9);
		calculatorPanel.add(button_dot);
		calculatorPanel.add(button_add);
		calculatorPanel.add(button_minus);
		calculatorPanel.add(button_mutiply);
		calculatorPanel.add(button_divid);
		calculatorPanel.add(button_cal);
		// 设置对象边框
		calculatorPanel.setBorder(BorderFactory.
				createEmptyBorder(5, 5, 5, 5));	
		
		
		JPanel jPanel2 = new JPanel();
		jPanel2.setLayout(new BorderLayout());
		jPanel2.add(textField,BorderLayout.WEST);
		jPanel2.add(clear_Button, BorderLayout.EAST);
		
		//设置窗口出现的位置
		calculatorFrame.setLocation(300, 300);
		//设置主窗体固定大小
		calculatorFrame.setResizable(false);
		calculatorFrame.getContentPane().setLayout(new BorderLayout());
		calculatorFrame.getContentPane().add(calculatorPanel, BorderLayout.NORTH);
		calculatorFrame.getContentPane().add(jPanel2, BorderLayout.CENTER);
		
		calculatorFrame.pack();
		calculatorFrame.setVisible(true);
		
		
		//数字键
		/**
		 * @author k570
		 * 数字键
		 *
		 */
		class Listener_num implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String ss = ((JButton)e.getSource()).getText();
				store = (JButton) e.getSource();
				vt.add(store);
				if (k1 == 1) {
					if (k3 == 1) {
						string1 = "";
						//还原k5状态
						k5 = 1;
					}
					string1 = string1 + ss;
					k3 = k3 + 1;
					//显示结果
					textField.setText(string1);
				}else if (k1 == 2) {
					if (k4 == 1) {
						string2 = "";
						//还原k5
						k5 = 1;
					}
					string2 = string2 + ss;
					k4 = k4 + 1;
					textField.setText(string2);					
				}				
			}
			
		}
		
		/**
		 * @author k570
		 * 运算符号处理
		 *
		 */
		class Listener_signal implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String ss2 = ((JButton)e.getSource()).getText();
				store = (JButton) e.getSource();
				vt.add(store);
				
				if (k2 == 1) {
					//k1为1时向1写入输入值，2时向2写入输入值
					k1 = 2;
					k5 = 1;
					signal = ss2;
					k2 = k2 + 1;//按符号键的次数
					
				}else{
					int size = vt.size();
					JButton c = (JButton) vt.get(size - 2);
					
					if (!(c.getText().equals("+"))
							&& !(c.getText().equals("-"))
							&& !(c.getText().equals("*"))
							&& !(c.getText().equals("/"))) {
						control();
						string1 = result;
						k1 = 2;
						k5 = 1;
						k4 = 1;
						signal = ss2;
					}
					k2 = k2 + 1;
				}
			}
			
		}
		/**
		 * @author k570
		 * 清除键的逻辑
		 *
		 */
		class Listener_clear implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				store = (JButton) e.getSource();
				vt.add(store);
				k5 = 1;
				k2 = 1;
				k1 = 1;
				k3 = 1;
				k4 = 1;
				string1 = "0";
				string2 = "0";
				signal = "";
				result = "";
				textField.setText(result);
				vt.clear();
			}
			
		}
		/**
		 * @author k570
		 * 等于键的逻辑
		 *
		 */
		class Listener_cal implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				store = (JButton) e.getSource();
				vt.add(store);
				control();
				k5 = 1;
				k2 = 1;
				k1 = 1;
				k3 = 1;
				k4 = 1;
				string1 = result;
			}			
		}
		
		/**
		 * @author k570
		 *
		 */
		class Listener_dot implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				store = (JButton) e.getSource();
				vt.add(store);
				if (k5 == 1) {
					String ss2 = ((JButton)e.getSource()).getText();
					if (k1 == 1) {
						if (k3 == 1) {
							string1 = "";
							k5 = 1;
						}
						string1 = string1 + ss2;
						k3 = k3 + 1;
						textField.setText(string1);
						
					}else if (k1 == 2) {
						if (k4 == 1) {
							string2 = "";
							k5 = 1;
						}
						string2 = string2 + ss2;
						k4 = k4 + 1;
						textField.setText(string2);
						
					}
				}
				k5 = k5 + 1;
			}			
		}
		
		//创建监听器对象
		Listener_num jt_num = new Listener_num();
		Listener_cal jt_cal = new Listener_cal();
		Listener_clear jt_clear = new Listener_clear();
		Listener_dot jt_dot = new Listener_dot();
		Listener_signal jt_signal = new Listener_signal();
		
		//绑定监听器
		button_0.addActionListener(jt_num);
		button_1.addActionListener(jt_num);
		button_2.addActionListener(jt_num);
		button_3.addActionListener(jt_num);
		button_4.addActionListener(jt_num);
		button_5.addActionListener(jt_num);
		button_6.addActionListener(jt_num);
		button_7.addActionListener(jt_num);
		button_8.addActionListener(jt_num);
		button_9.addActionListener(jt_num);
		button_add.addActionListener(jt_signal);
		button_minus.addActionListener(jt_signal);
		button_mutiply.addActionListener(jt_signal);
		button_divid.addActionListener(jt_signal);
		button_dot.addActionListener(jt_dot);
		button_cal.addActionListener(jt_cal);
		clear_Button.addActionListener(jt_clear);
		//窗体关闭相应程序
		calculatorFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent wEvent) {
				System.exit(0);
			}
		});
		
	}
	
	public void control() {
		//操作数1
		double a2;
		//操作数2
		double b2;
		//运算符
		String s = signal;
		//运算结果
		double result2 = 0;
		
		if (s.equals("")) {
			textField.setText("请输入");
		}else {
			//手动处理小数点问题
			if (string1.equals(".")) {
				string1 = "0.0";
			}
			if (string2.equals(".")) {
				string2 = "0.0";
			}
			a2 = Double.valueOf(string1).doubleValue();
			b2 = Double.valueOf(string2).doubleValue();
			if (s.equals("+")) {
				result2 = a2 + b2;
			}
			if (s.equals("-")) {
				result2 = a2 - b2;
			}
			if (s.equals("*")) {
				result2 = a2 * b2;
			}
			if (s.equals("/")) {
				
				if (b2 == 0) {
					result2 = 0;
				}else {
					result2 = a2 / b2;
				}
			}
		}
		result = ((Double)result2).toString();
		textField.setText(result);
	}	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CalculatorBoundary cBoundary = new CalculatorBoundary();
	}
}
