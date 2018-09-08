package com.ebase.core.random;

import org.junit.Test;

import com.ebase.utils.RandomCharUtil;

public class RandomCharUtilTest {
	
	@Test
	public void randomTest(){
		String st = RandomCharUtil.random(4);
		System.out.println(st);
	}
	
	@Test
	public void randomNumTest(){
		String st = RandomCharUtil.randomNum(4);
		System.out.println(st);
	}

	
	@Test
	public void randomLetterTest(){
		String st = RandomCharUtil.randomLetter(4);
		System.out.println(st);
	}

}
