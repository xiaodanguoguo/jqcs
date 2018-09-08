package com.ebase.core.pinyin;

import org.junit.Assert;
import org.junit.Test;

import com.ebase.utils.pinyin.PinyinUtil;

public class PinyinUtilTest {

	/**
	 * 将汉字转换为拼音的全拼的大写，非汉字的原样输出
	 */
	@Test
	public void testGetQuanPin() {
		String str = "测试全拼+test";
		String quanPin = PinyinUtil.getQuanPin(str);
		Assert.assertEquals(null, "CESHIQUANPIN+test", quanPin);
	}

}
