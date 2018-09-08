package com.ebase.core.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import com.ebase.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Count;
import io.searchbox.core.CountResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;

/**
 * <p>
 * elasticsearch 基础实现类
 * </p>
 *
 * @project core
 * @class EsBaseRepository
 */
public abstract class EsBaseRepository<T> implements EsRepository<T> {

	private final static Logger logger = LoggerFactory.getLogger(EsBaseRepository.class);

	@Autowired
	private JestClient jestClient;

	public abstract Class<T> getEntityClass();

	public abstract String getType();

	public abstract String getIndex();

	@Override
	public boolean meger(T t) {
		List<T> list = new ArrayList<>();
		list.add(t);
		return megerList(list);
	}

	@Override
	public boolean megerList(List<T> list) {
		try {
			Bulk.Builder bulk = new Bulk.Builder().defaultIndex(getIndex()).defaultType(getType());
			for (Object obj : list) {
				Index index = new Index.Builder(obj).build();
				bulk.addAction(index);
			}
			BulkResult br = jestClient.execute(bulk.build());
			return br.isSucceeded();
		} catch (Exception e) {
			logger.error("", e);
			throw new BusinessException("新增或者修改es数据出现错误", null, e);
		}
	}

	@Override
	public boolean delete(String id) {
		try {
			DocumentResult dr = jestClient.execute(new Delete.Builder(id).index(getIndex()).type(getType()).build());
			return dr.isSucceeded();
		} catch (Exception e) {
			logger.error("", e);
			throw new BusinessException("删除文档出现错误", null, e);
		}
	}

	@Override
	public boolean delete(List<String> ids) {
		try {
			Bulk.Builder bulk = new Bulk.Builder().defaultIndex(getIndex()).defaultType(getType());
			for (int i = 0; i < ids.size(); i++) {
				Delete action = new Delete.Builder(ids.get(i)).build();
				bulk.addAction(action);
			}
			BulkResult br = jestClient.execute(bulk.build());
			return br.isSucceeded();
		} catch (Exception e) {
			logger.error("", e);
			throw new BusinessException("批量删除es数据出现错误", null, e);
		}
	}

	public T get(String id) {
		try {
			Get get = new Get.Builder(getIndex(), id).type(getType()).build();
			JestResult result = jestClient.execute(get);
			if(result.isSucceeded()){
				T t = result.getSourceAsObject(getEntityClass());
				return t;
			}else{
				logger.info(result.getErrorMessage());
				return null;
			}
		} catch (Exception e) {
			logger.error("",e);
			throw new BusinessException("获取数据错误",null,e);
		}
	}

	@Override
	public int count(String query) {
		try {
			Count count = new Count.Builder().addIndex(getIndex()).addType(getType()).query(query).build();
			CountResult results = jestClient.execute(count);
			return results.getCount().intValue();
		} catch (Exception e) {
			logger.error("", e);
			throw new BusinessException("es获取count出现错误", null, e);
		}
	}

	@Override
	public List<T> queryList(String query) {
		try {
			Search search = new Search.Builder(query).addIndex(getIndex()).addType(getType()).build();
			SearchResult result = jestClient.execute(search);
			if (result.isSucceeded()) {
				List<Hit<T, Void>> hits = result.getHits(getEntityClass());
				List<T> list = new ArrayList<>();
				for (Hit<T, Void> hit : hits) {
					T t = hit.source;
					list.add(t);
				}
				return list;
			} else {
				throw new BusinessException("es获取查询list出现错误:" + result.getErrorMessage());
			}
		} catch (Exception e) {
			logger.error("", e);
			throw new BusinessException("es获取查询list出现错误", null, e);
		}
	}

	public JestClient getJestClient() {
		return jestClient;
	}
	
}
