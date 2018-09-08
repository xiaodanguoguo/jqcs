package com.ebase.core.sensitive;

import java.util.Set;

import com.ebase.core.sensitive.SensitiveWordUtil;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class SensitiveTest {

	@Test
	public void testWord() {
		SensitiveWordUtil util = SensitiveWordUtil.getInstance();
		long start = System.currentTimeMillis();
		String str = "马化腾和丁磊在养猪";
		Set<String> set = util.getSensitiveWord(str, SensitiveWordUtil.minMatchTYpe);
		boolean sen = util.isContaintSensitiveWord(str, SensitiveWordUtil.minMatchTYpe);
		
		System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
		System.out.println("总共消耗时间为：" + (System.currentTimeMillis() - start));
		
		MatcherAssert.assertThat(set.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(sen, CoreMatchers.is(true));
		
	}

}
