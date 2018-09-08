package com.ebase.utils.json;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class FrameBeanSerializerModifier extends BeanSerializerModifier {

	private JsonSerializer<Object> _nullArrayJsonSerializer = new FrameNullArrayJsonSerializer();
	
	private JsonSerializer<Object> _nullStringJsonSerializer = new FrameNullStringJsonSerializer();
	
	private JsonSerializer<Object> _nullEntityJsonSerializer = new FrameNullEntityJsonSerializer();

	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		//循环所有的beanPropertyWriter
		for (int i = 0; i < beanProperties.size(); i++) {
			BeanPropertyWriter writer = beanProperties.get(i);
			JavaType type = writer.getType();
			Class<?> clazz = type.getRawClass();
			if(isArrayType(clazz)){
				writer.assignNullSerializer(_nullArrayJsonSerializer);
			}
			if(clazz.equals(String.class)){
				writer.assignNullSerializer(_nullStringJsonSerializer);
			}
			if(isEntity(clazz)){
				writer.assignNullSerializer(_nullEntityJsonSerializer);
			}
		}
		return beanProperties;
	}

	private boolean isEntity(Class<?> clazz) {
		if(clazz.isAssignableFrom(Map.class)){
			return true;
		}
		if(clazz.getName().startsWith("cn.enn.ygego")){
			return true;
		}
		return false;
	}

	// 判断是否是array
	protected boolean isArrayType(Class<?> clazz) {
		return clazz.isArray() || clazz.equals(List.class) || clazz.equals(Set.class);
	}
	
	
}
