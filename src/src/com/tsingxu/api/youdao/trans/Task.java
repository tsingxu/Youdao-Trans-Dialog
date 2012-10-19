package com.tsingxu.api.youdao.trans;

public class Task
{
	private String inString;
	private String outString;
	private int index;
	private int sum;

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
		return "Task [inString=" + inString + ", outString=" + outString + ", index=" + index
				+ ", sum=" + sum + "]";
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int getSum()
	{
		return sum;
	}

	public void setSum(int sum)
	{
		this.sum = sum;
	}

}