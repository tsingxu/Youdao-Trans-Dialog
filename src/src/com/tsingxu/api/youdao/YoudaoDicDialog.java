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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.tsingxu.api.youdao.trans.Task;
import com.tsingxu.api.youdao.trans.Translate;

/**
 * <b>有道词典查询界面</b>
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
	private String defaultInput = "";
	private JTextField input = new JTextField(defaultInput, 20);
	private JTextAreaT output = new JTextAreaT();
	private JButton translate = new JButton("translate");
	private JButton backward = new JButton("<");
	private JButton forward = new JButton(">");
	private JButton reset = new JButton("reset");
	private JButton wisdom = new JButton("wisdom");
	private JButton about = new JButton("about");
	private String[] wisdoms = new String[] {
			"Life is not like you imagine so well, but not as you imagine so bad. I think people 's fragile and strong are beyond our imagination.",
			"日出江花红胜火， 春来江水绿如蓝",
			"The future belongs to those who believe in the beauty of their dreams.",
			"Eternity is not a distance but a decision.",
			"Real girls aren’t perfect. Perfect girls aren’t real",
			"It's an amazing feeling to realize how one person who was once just a stranger suddenly meant the world to you",
			"Sometimes you need to step outside, clear your head, and remind yourself of who you are and where you wanna be.",
			"the pursuit of Happiness", "Get busy living, Or get busy dying.",
			"To see  a world in a grain of sand. And a heaven in a wild flower.",
			"我觉得人的脆弱和坚强都超乎自己的想象。有时，我可能脆弱得一句话就泪流满面，有时，也发现自己咬着牙走了很长的路" };

	public YoudaoDicDialog()
	{
		Font font = new Font("微软雅黑", Font.BOLD, 13);
		translate.setFont(font);
		reset.setFont(font);
		wisdom.setFont(font);
		about.setFont(font);
		backward.setFont(font);
		forward.setFont(font);

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
		output.addKeyListener(new KeyListener()
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
				char c = e.getKeyChar();
				if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
				{
					input.grabFocus();
					input.setText(c + "");
				}
			}
		});

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

		backward.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Task t = Translate.getInstance().previousTask();
				if (t != null)
				{
					input.setText(t.getInString());
					output.setText(t.getOutString());
				}
				input.grabFocus();
			}
		});
		forward.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Task t = Translate.getInstance().nextTask();
				if (t != null)
				{
					input.setText(t.getInString());
					output.setText(t.getOutString());
				}
				input.grabFocus();
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
				input.grabFocus();
			}
		});
		wisdom.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int cnt = (int) (Math.random() * wisdoms.length);
				input.setText(wisdoms[cnt]);
				translate();
				input.grabFocus();
			}
		});
		jp.add(backward);
		jp.add(forward);
		jp.add(input);
		jp.add(translate);
		jp.add(reset);
		jp.add(wisdom);
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
		Translate.getInstance().setDialog(this);
	}

	class translateListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			translate();
			input.grabFocus();
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
			Translate.getInstance().translate(input.getText().trim());
		}
	}

	class resetListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			reset();
			input.grabFocus();
		}
	}

	public void onResp(String resp)
	{
		this.output.setText(resp);
		input.grabFocus();
	}

	public void reset()
	{
		input.setText(defaultInput);
		input.grabFocus();
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