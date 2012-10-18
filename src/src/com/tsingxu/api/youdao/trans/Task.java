package com.tsingxu.api.youdao.trans;

public class Task
{
	private String inString;
	private String outString;

	public Task(String inString)
	{
		this.inString = inString;
	}

	public String getInString()
	{
		return inString;
	}

	public void setInString(String inString)
	{
		this.inString = inString;
	}

	public String getOutString()
	{
		return outString;
	}

	public void setOutString(String outString)
	{
		this.outString = outString;
	}

	@Override
	public String toString()
	{
		return "Task [inString=" + inString + ", outString=" + outString + "]";
	}

}