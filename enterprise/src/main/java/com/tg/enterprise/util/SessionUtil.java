package com.tg.enterprise.util;

import com.alibaba.fastjson.JSONObject;
import com.tg.enterprise.model.User;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SessionUtil 
{
	public static User getUser(HttpSession session)
	{
		Object jsonStr = session.getAttribute(SystemConstants.SESSION_KEY);
		if (jsonStr != null)
		{
			User userVO = JSONObject.parseObject(jsonStr.toString(), User.class);
			return userVO;
		}
		else
		{
			return null;
		}
	}
	
	public static void removeUser(HttpSession session)
	{
		Enumeration<String> names = session.getAttributeNames();
		List<String> nameArray = new ArrayList<>();
		while (names.hasMoreElements())
		{
			String name = names.nextElement();
			nameArray.add(name);
		}
		for (String name : nameArray)
		{
			session.removeAttribute(name);
		}
	}
}
