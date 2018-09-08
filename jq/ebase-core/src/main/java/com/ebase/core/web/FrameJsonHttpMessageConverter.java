package com.ebase.core.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

import com.ebase.utils.ReflectUtil;
import com.ebase.utils.StringUtil;

/**
 * <p>
 * 对返回的bean转json进行处理,为空对象的处理 平台的对象返回new Object() list set map 的基本处理
 * </p>
 *
 * @project core
 * @class FrameJsonHttpMessageConverter
 */
public class FrameJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
	
	public final static Charset UTF8 = Charset.forName("UTF-8");

	private Charset charset = UTF8;
	
	private SerializerFeature[] features = new SerializerFeature[0];

	private ValueFilter valueFilter = new ValueFilter() {

		@Override
		public Object process(Object o, String propertyName, Object propertyValue) {
			try {
				if (propertyValue == null) {
					Field field = null;
					try {
						field = ReflectUtil.getDeclaredField(o, propertyName);
					} catch (Exception e) {
						return null;
					}
					String typeClassName = field.getType().getName();
					if (StringUtil.startsWith(typeClassName, "cn.enn.ygego")) {
						return new Object();
					}
					Class<?> typeClass = field.getType();
					if (typeClass.isAssignableFrom(List.class)) {
						return new ArrayList<>();
					}
					if (typeClass.isAssignableFrom(Set.class)) {
						return new HashSet<>();
					}
					if (typeClass.isAssignableFrom(Map.class)) {
						return new HashMap<>();
					}
					return null;
				}
			} catch (Exception e) {
				logger.error(propertyName + " 出现问题", e);
			}
			return propertyValue;
		}
	};

	public FrameJsonHttpMessageConverter() {
		 super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

	public Charset getCharset() {
		return this.charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public SerializerFeature[] getFeatures() {
		return features;
	}

	public void setFeatures(SerializerFeature... features) {
		this.features = features;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = inputMessage.getBody();
		byte[] buf = new byte[1024];
		for (;;) {
			int len = in.read(buf);
			if (len == -1) {
				break;
			}
			if (len > 0) {
				baos.write(buf, 0, len);
			}
		}
		byte[] bytes = baos.toByteArray();
		return JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
	}

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		OutputStream out = outputMessage.getBody();
		String text = JSON.toJSONString(obj, valueFilter,features);
		byte[] bytes = text.getBytes(charset);
		out.write(bytes);
	}

}
