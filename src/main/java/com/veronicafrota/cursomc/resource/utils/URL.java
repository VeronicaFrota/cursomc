package com.veronicafrota.cursomc.resource.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	// Converts the element to integer, then plays in the integer list
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	
	// To convert the list to integer
	public static List<Integer> decodeIntList(String s) {
		String[] vet = s.split(",");		// "Cut" the string into pieces
		
		List<Integer> list = new ArrayList<>();
		
		for(int i = 0; i < vet.length; i++) {
				list.add(Integer.parseInt(vet[i]));	// Converts the element to integer, then plays in the integer list
		}
		return list;
	}
	
}
