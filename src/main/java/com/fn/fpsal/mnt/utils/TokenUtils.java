package com.fn.fpsal.mnt.utils;

import java.text.SimpleDateFormat;

import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
	
	private final static String SUFFIX = "whiteList";
	private static SimpleDateFormat SF = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private final static String PRIVATE_KEY = "";
	
	public static boolean volidateToken(String token) throws ParseException {
		String decryptData = null;
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}
}
