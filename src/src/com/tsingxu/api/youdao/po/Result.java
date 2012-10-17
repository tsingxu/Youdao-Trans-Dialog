package com.tsingxu.api.youdao.po;

import java.util.List;

public class Result
{
	private String errorCode = null;

	private String query = null;

	private List<String> translation = null;

	private Basic basic = null;

	private List<Web> web = null;

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}

	public Basic getBasic()
	{
		return basic;
	}

	public void setBasic(Basic basic)
	{
		this.basic = basic;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ReturnCode : \n\t").append(errorCode).append("\n");
		sb.append("Query : \n\t").append(query).append("\n");
		sb.append("Translation : \n\t").append(translation).append("\n");

		if (basic != null && basic.getPhonetic() != null)
		{
			sb.append("Phonetic : \n\t").append(basic.getPhonetic()).append("\n");
		}

		if (basic != null && basic.getExplains() != null)
		{
			sb.append("Explains : \n\t").append(basic.getExplains()).append("\n");
		}

		sb.append("\nWeb:\n");

		if (web != null)
		{
			for (Web _web : web)
			{
				sb.append("\t").append(_web.getKey()).append(" , ").append(_web.getValue())
						.append("\n");
			}
		}

		return sb.toString();
	}

	public List<String> getTranslation()
	{
		return translation;
	}

	public void setTranslation(List<String> translation)
	{
		this.translation = translation;
	}

	public List<Web> getWeb()
	{
		return web;
	}

	public void setWeb(List<Web> web)
	{
		this.web = web;
	}
}
