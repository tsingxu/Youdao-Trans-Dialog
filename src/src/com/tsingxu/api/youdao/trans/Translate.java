package com.tsingxu.api.youdao.trans;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;

import javax.swing.JTextArea;

import org.codehaus.jackson.map.ObjectMapper;

import com.tsingxu.api.youdao.po.Result;

/**
 * <b>translate线程，持有一个消息队列，持续的翻译队列中的输入</b>
 * 
 * <ol>
 * <li>...</li>
 * </ol>
 * 
 * @since 2012-10-17 下午4:50:04
 * @author x00199331
 */
public class Translate implements Runnable
{
	private static final String ID = "tsingxu";
	private static final String KEY = "1031053504";
	private static final String DOCTYPE = "json";
	private static final String URL_REQ = "http://fanyi.youdao.com/openapi.do";
	private static final String urlHead;
	private static Translate instance = new Translate();
	private LinkedList<task> queue = new LinkedList<task>();

	private Translate()
	{
		queue.clear();
	}

	public static Translate getInstance()
	{
		return instance;
	}

	private class task
	{
		String input;
		JTextArea output;

		public task(String input, JTextArea output)
		{
			this.input = input;
			this.output = output;
		}
	}

	static
	{
		urlHead = URL_REQ + "?" + "keyfrom=" + ID + "&key=" + KEY + "&type=data&doctype=" + DOCTYPE
				+ "&version=1.1" + "&q=";
	}

	public void translate(String input, JTextArea output)
	{
		synchronized (queue)
		{
			queue.push(new task(input, output));
			if (queue.size() <= 1)
			{
				queue.notify();
			}
		}

	}

	@Override
	public void run()
	{
		URL url;
		int count;
		byte[] buff = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectMapper om = new ObjectMapper();
		long start;
		String resp;
		task t = null;

		while (true)
		{
			synchronized (queue)
			{
				if (queue.isEmpty())
				{
					try
					{
						queue.wait();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						return;
					}
				}
				t = queue.pollLast();
			}

			if (t == null)
			{
				continue;
			}

			try
			{
				start = System.currentTimeMillis();
				url = new URL(urlHead + URLEncoder.encode(t.input, "utf-8"));
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");

				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

				baos.reset();
				while ((count = bis.read(buff)) != -1)
				{
					baos.write(buff, 0, count);
				}
				baos.flush();

				resp = baos.toString("utf-8");
				Result result = (Result) om.readValue(resp, Result.class);
				String outText = String.valueOf(result);
				outText = "elapsed " + (System.currentTimeMillis() - start) + " ms\n\n" + result;

				t.output.setText(outText);
			}
			catch (Exception e)
			{
				t.output.setText(String.valueOf(e));
			}
		}
	}
}
