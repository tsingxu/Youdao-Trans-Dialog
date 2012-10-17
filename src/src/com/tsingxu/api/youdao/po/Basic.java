package com.tsingxu.api.youdao.po;

import java.util.List;

public class Basic
{
	private String phonetic = null;

	private List<String> explains = null;

	public String getPhonetic()
	{
		return phonetic;
	}

	public void setPhonetic(String phonetic)
	{
		this.phonetic = phonetic;
	}

	public List<String> getExplains()
	{
		return explains;
	}

	public void setExplains(List<String> explains)
	{
		this.explains = explains;
	}

	@Override
	public String toString()
	{
		return "Basic [phonetic=" + phonetic + ", explains=" + explains + "]";
	}

}
