package com.tsingxu.api.youdao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * <b>in_a_word_briefly</b>
 * 
 * <ol>
 * <li>...</li>
 * </ol>
 * 
 * @since 2012-10-16 上午8:25:50
 * @author x00199331
 */
@SuppressWarnings("serial")
public class YoudaoDicDialog extends JFrame
{
	private JFrame jf = new JFrame("有道词典查询界面 by Tsingxu");
	private JPanel jp = new JPanel();
	private JLabel inputLabel = new JLabel("输入");
	private String defaultInput = "";
	private JTextField input = new JTextField(defaultInput, 20);
	private JTextAreaT output = new JTextAreaT();
	private JButton translate = new JButton("translate");
	private JButton reset = new JButton("reset");
	private JButton random = new JButton("random");
	private JButton about = new JButton("about");

	public YoudaoDicDialog()
	{
		Font font = new Font("微软雅黑", Font.PLAIN, 13);
		inputLabel.setFont(font);

		translate.setFont(new Font("微软雅黑", Font.BOLD, 13));
		reset.setFont(new Font("微软雅黑", Font.BOLD, 13));
		random.setFont(new Font("微软雅黑", Font.BOLD, 13));
		about.setFont(new Font("微软雅黑", Font.BOLD, 13));

		output.setColumns(68);
		output.setRows(20);
		output.setLineWrap(true);
		output.setForeground(Color.BLACK);
		output.setEditable(false);
		output.setCursor(Cursor.getPredefinedCursor(HEIGHT));
		output.setLocale(Locale.US);
		output.setSelectedTextColor(Color.WHITE);
		float[] hsb = Color.RGBtoHSB(51, 135, 255, null);
		output.setSelectionColor(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));

		float[] hsb_1 = Color.RGBtoHSB(186, 224, 190, null);
		output.setBackground(Color.getHSBColor(hsb_1[0], hsb_1[1], hsb_1[2]));
		output.setFont(new Font("", Font.PLAIN, 14));

		input.setFont(new Font("微软雅黑", Font.BOLD, 15));
		input.setSelectedTextColor(Color.WHITE);
		input.setSelectionColor(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
		input.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e)
			{
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					translate();
				}
			}
		});

		translate.addActionListener(new translateListener());
		reset.addActionListener(new resetListener());
		about.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				input.setText("tsingxu");
				translate();
			}
		});
		random.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int cnt = (int) (Math.random() * 10) + 1;

				if (cnt == 3)
				{
					input.setText("Life is not like you imagine so well, but not as you imagine so bad");
				}
				else
				{
					StringBuilder sb = new StringBuilder();
					while (cnt > 0)
					{
						int randomChar = (int) (Math.random() * 25) + (int) 'a';
						sb.append((char) randomChar);
						cnt--;
					}
					input.setText(sb.toString());
				}
				translate();
			}
		});
		jp.add(inputLabel);
		jp.add(input);
		jp.add(translate);
		jp.add(reset);
		jp.add(random);
		jp.add(about);
		jp.add(output);

		jp.setBackground(Color.getHSBColor(hsb_1[0], hsb_1[1], hsb_1[2]));

		jf.add(jp);
		jf.setVisible(true);
		jf.setSize(800, 600);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jf.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 800) / 2,
				((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 600) / 2);

		ImageIcon ii = new ImageIcon(this.getClass().getResource("dropbox.png"));
		jf.setIconImage(ii.getImage());
	}

	class translateListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			translate();
		}
	}

	public void translate()
	{
		output.setText("正在查询中 ... ");
		if ("".equals(input.getText().trim()))
		{
			output.setText("无输入");
			input.setText("");
		}
		else if ("tsingxu".equals(input.getText().trim()))
		{
			output.setText("\n\n\n\n\n\n\n          I am tsingxu , from Huitong Solution Depart , my email is 3x3h3q@163.com.");
		}
		else
		{
			Translate.getInstance().translate(input.getText().trim(), output);
		}
	}

	class resetListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			reset();
		}
	}

	public void reset()
	{
		input.setText(defaultInput);
	}

	public static void main(String[] args)
	{
		new Thread(Translate.getInstance()).start();
		new YoudaoDicDialog();
	}

	class JTextAreaT extends JTextArea
	{
		@Override
		public void setText(String str)
		{
			super.setText("\n" + str);
		}
	}

}