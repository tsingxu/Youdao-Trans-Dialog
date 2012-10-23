package com.tsingxu.api.youdao.po;

import java.util.List;

/**
 * <b>in_a_word_briefly</b>
 * 
 * <ol>
 * <li>...</li>
 * </ol>
 * 
 * @since 2012-10-23 下午7:22:19
 * @author xuhuiqing
 */
public class Web
{
	private String key;

	private List<String> value;

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public List<String> getValue()
	{
		return value;
	}

	public void setValue(List<String> value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return "Web [key=" + key + ", value=" + value + "]";
	}
}
