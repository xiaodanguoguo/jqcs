package com.ebase.core.elasticsearch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebase.core.elasticsearch.domain.User;
import com.ebase.core.elasticsearch.repository.UserRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ebase.core.page.PageDTO;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:elasticsearch/elasticsearch.spring.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRepositoryTest {

	private final static Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void aMegerTest(){
		User user = new User();
		user.setId("1");
		user.setName("lipan");
		user.setBirth(new Date());
		user.setAge(25);
		user.setIntru("he is a f-male");
		boolean result =userRepository.meger(user);
		assertThat("添加meger one数据", result, is(true));
	}
	
	
	@Test
	public void bMegerListTest(){
		User user1 = new User("1","abaa",222,new Date(),"第一个被修改了");
		User user2 = new User("2","abc",222,null,"321321");
		User user3 = new User("3","c",222,null,"321321");
		User user4 = new User("4","e",222,null,"321321");
		User user5 = new User("5","f",222,null,"321321");
		
		List<User> list =  new ArrayList<>();
		list.add(user1);
		list.add(user2);
		list.add(user3);
		list.add(user4);
		list.add(user5);
		
		boolean result =userRepository.megerList(list);
		assertThat("添加list数据", result, is(true));
	}
	
	@Test
	public void cGetTest(){
		User user1 =  userRepository.get("1");
		assertThat("获取单个数据", user1, notNullValue());
	}
	
	@Test
	public void dDeleteTest(){
		boolean result = userRepository.delete("5");
		assertThat("删除单个", result, is(true));
	}
	
	@Test
	public void fDeleteListTest(){
		List<String> list = new ArrayList<>();
		list.add("3");
		list.add("4");
		boolean result = userRepository.delete(list);
		assertThat("删除多个", result, is(true));
	}
	
	@Test
	public void hCountAllTest(){
		int count = userRepository.count("");
		assertThat("计数",count ,not(0));
	}
	
	
	@Test
	public void iQueryTest(){
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// name:abc  *:*  a 
		QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("name:abc intru:321321");// 通配符和正则表达式查询
		searchSourceBuilder.query(queryBuilder);
		String query = searchSourceBuilder.toString();
		logger.info(query);
		List<User> list =  userRepository.queryList(query);
		assertThat("查询",list.size(),not(0));
	}

	
	@Test
	public void lQueryPrefixTest(){
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// name:panly  *:*
		QueryBuilder queryBuilder = QueryBuilders.prefixQuery("name", "a");// 前缀查询
		searchSourceBuilder.query(queryBuilder);
		searchSourceBuilder.sort("id", SortOrder.ASC);
		String query = searchSourceBuilder.toString();
		logger.info(query);
		List<User> list =  userRepository.queryList(query);
		assertThat("查询",list.size(),not(0));
	}
	
	
	@Test
	public void nQueryPage(){
		int pageSize = 2;
		int pageNum = 3;
		
		PageDTO<User> page = new PageDTO<>(pageNum, pageSize);
		int start = page.getStartRow();
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// name:panly  *:*
		QueryBuilder queryBuilder = QueryBuilders.prefixQuery("name", "a");// 前缀查询
		searchSourceBuilder.query(queryBuilder);
		searchSourceBuilder.from(start);
		searchSourceBuilder.size(pageSize);
		String query = searchSourceBuilder.toString();
		logger.info(query);
		List<User> list =  userRepository.queryList(query);
		page.setResultData(list);
		assertThat("分页",page,notNullValue());
		
	}
	
	
}
