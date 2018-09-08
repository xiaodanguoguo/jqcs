package com.ebase.core.easymock;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.easymock.dto.User;
import com.ebase.core.easymock.facade.UserFacade;
import com.ebase.core.easymock.facade.VerificationFacade;
import com.ebase.core.easymock.weblogic.UserWeblogic;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

/**
 * <p>
 * 使用easymock的例子
 * </p>
 * <p>
 * easymock的作用，将interface中实现实现方法，模拟mock返回
 * </p>
 *
 * @project core
 * @class MockTest
 * @author a@panly.me
 * @date 2017年6月22日上午11:51:34
 */
public class MockTest{

	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	private UserWeblogic userWeblogic;
	
	@Before
	public void setup(){
		
		//创建mock对象
		IMocksControl control = EasyMock.createControl();   
		UserFacade userFacade = control.createMock(UserFacade.class);
		VerificationFacade verificationFacade  =control.createMock(VerificationFacade.class);
		
		//设置接口方法的预计结果
		EasyMock.expect(userFacade.getUser(1)).andReturn(new User()).anyTimes();
		EasyMock.expect(userFacade.queryUsers("name")).andReturn(new ArrayList<User>()).anyTimes();
		EasyMock.expect(userFacade.reg("name", "password")).andReturn(true).anyTimes();
		EasyMock.expect(verificationFacade.verify("name", "123456")).andReturn(true).anyTimes();
		
		//设置验证码不正确
		EasyMock.expect(verificationFacade.verify("name", "xxxxxx")).andReturn(false).anyTimes();
		
		//开启模拟
		control.replay();
		
		//组装测试类
		userWeblogic = new UserWeblogic();
		userWeblogic.setUserFacade(userFacade);
		userWeblogic.setVerificationFacade(verificationFacade);
	}
	
	@Test
	public void regTest(){
		boolean success =  userWeblogic.regUser("name", "password", "123456");
		assertThat("注册", success, is(true));
	}
	
	@Test
	public void regErrorTest(){
		//必须在报错之前，判断预期是否报错
		thrown.expect(BusinessException.class);
		userWeblogic.regUser("name", "password", "xxxxxx");
	}
	
	@Test
	public void queryUserTest(){
		List<User> list = userWeblogic.queryUser("name");
		assertThat("查询User",list.size(),is(0));
	}
	
	@Test
	public void getUserTest(){
		User user =  userWeblogic.getUser(1);
		assertThat("获取user", user, notNullValue());
	}
	
}
