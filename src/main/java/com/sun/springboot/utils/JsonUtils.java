package com.sun.springboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sun
 */
public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * jackjson把json字符串转换为Java对象的实现方法
	 *
	 * @param <T>
	 *            转换为的java对象
	 * @param json
	 *            json字符串
	 * @param typeReference
	 *            jackjson自定义的类型
	 * @return 返回Java对象
	 */
	public static <T> T jsonToObj(String json, TypeReference<T> typeReference) {
		try {
			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param json
	 *            字符串
	 * @param valueType
	 *            对象的class
	 * @param <T>
	 *            要转换的对象
	 * @return
	 */
	public static <T> T jsonToObj(String json, Class<T> valueType) {
		try {
			return mapper.readValue(json, valueType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json字符串转化成List
	 *
	 * @param json
	 * @param valueType
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> jsonToList(String json, Class<T> valueType) {
		try {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, valueType);
			return mapper.readValue(json, javaType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json字符串转换成Map
	 *
	 * @param json
	 * @param valueType
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, T> jsonToMap(String json, Class<T> valueType) {
		try {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, valueType);
			return mapper.readValue(json, javaType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * java对象转换为json字符串
	 *
	 * @param object
	 *            Java对象
	 * @return 返回字符串
	 */
	public static String objToJson(Object object) {
		try {
			if (object != null)
				return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
