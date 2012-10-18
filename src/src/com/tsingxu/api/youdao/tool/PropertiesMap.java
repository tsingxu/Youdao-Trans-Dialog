package com.tsingxu.api.youdao.tool;

import java.util.Properties;

public final class PropertiesMap
{
	private static PropertiesMap instance = new PropertiesMap();
	private Properties prop;

	private PropertiesMap()
	{
		reload();
	}

	public static PropertiesMap getInstance()
	{
		return instance;
	}

	private void reload()
	{
		try
		{
			Properties propTmp = new Properties();
			propTmp.load(this.getClass().getResourceAsStream("config.properties"));
			prop = propTmp;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getProperty(String key)
	{
		return prop.getProperty(key);
	}

}
