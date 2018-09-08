package com.ebase.core.json;

import com.ebase.core.easymock.dto.User;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import com.ebase.utils.JsonUtil;

public class JsonTest {
	
	@Test
	public void testJson(){
		User u = new User();
		u.setIntru("123");
		u.setUserName("lipan");
		String str =  JsonUtil.toPrettyJson(u);
		System.out.println(str);
		MatcherAssert.assertThat(str, CoreMatchers.notNullValue());
	}

}
