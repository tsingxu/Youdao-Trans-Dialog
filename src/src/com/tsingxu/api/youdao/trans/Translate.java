package com.tsingxu.api.youdao.trans;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;

import org.codehaus.jackson.map.ObjectMapper;

import com.tsingxu.api.youdao.YoudaoDicDialog;
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
	private final String ID = "tsingxu";
	private final String KEY = "1031053504";
	private final String DOCTYPE = "json";
	private final String URL_REQ = "http://fanyi.youdao.com/openapi.do";
	private final String urlHead;
	private static Translate instance = new Translate();
	private LinkedList<Task> queue = new LinkedList<Task>();
	private LimitCursorQueue<Task> lcdeque = new LimitCursorQueue<Task>(10000);
	private YoudaoDicDialog dialog;

	private Translate()
	{
		queue.clear();
		lcdeque.clear();
		urlHead = URL_REQ + "?" + "keyfrom=" + ID + "&key=" + KEY + "&type=data&doctype=" + DOCTYPE
				+ "&version=1.1" + "&q=";
	}

	public static Translate getInstance()
	{
		return instance;
	}

	public void setDialog(YoudaoDicDialog dialog)
	{
		this.dialog = dialog;
	}

	public Task previousTask()
	{
		Task t = lcdeque.stepBackward();
		if (t != null)
		{
			t.setIndex(lcdeque.getCursorIndex());
		}
		return t;
	}

	public Task nextTask()
	{
		Task t = lcdeque.stepForward();
		if (t != null)
		{
			t.setIndex(lcdeque.getCursorIndex());
		}
		return t;
	}

	public void translate(String input)
	{
		synchronized (queue)
		{
			queue.push(new Task(input));
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
		Task t = null;

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
				url = new URL(urlHead + URLEncoder.encode(t.getInString(), "utf-8"));
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
				outText = "The following content provided by fanyi.youdao.com\n\n" + "elapsed "
						+ (System.currentTimeMillis() - start) + " ms\n\n" + result;

				t.setOutString(outText);
				dialog.onResp(outText);
			}
			catch (Exception e)
			{
				t.setOutString(String.valueOf(e));
				dialog.onResp(String.valueOf(e));
			}
			finally
			{
				lcdeque.offer(t);
				lcdeque.resetCursor();
			}
		}
	}
}
