package com.ebase.core.threadlocal;


import com.ebase.core.Right;

public class RightThreadLocal {
	
	private static ThreadLocal<Right> threadLocal = new ThreadLocal<>();
	
	public static void init(Right t){
		threadLocal.set(t);
	}
	
	public static void remove(){
		threadLocal.remove();
	}
	
	public static Right get(){
		return threadLocal.get();
	}
	
	
}
