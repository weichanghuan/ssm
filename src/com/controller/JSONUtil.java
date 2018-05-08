package com.controller;

import java.io.StringWriter;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json转换工具�?
 * 
 * @author jiong.peng
 *
 */
public class JSONUtil {

	/**
	 * 默认日期时间格式
	 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyyMMddHHmmss";

	/**
	 * 将对象转换为字符�?,如果有时间对象，转换默认格式为yyyyMMddHHmmss
	 * 
	 * @param obj 要转换的对象
	 * @return
	 */
	public static<T> String toJSonString(T obj) {
		return toJSonString(obj, DEFAULT_DATETIME_FORMAT);
	}


	/**
	 * 将对象转换为字符�?,如果有日期属性，且需要指点输出格式，可以按指点格式输�?
	 * 
	 * @param obj
	 *            要转换的对象
	 * @param dateFormat
	 *            格式字符�?
	 * @return
	 */
	public static<T> String toJSonString(T obj, String dateFormat) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter out = new StringWriter();
		try {
			JsonGenerator gen = new JsonFactory().createGenerator(out);
			mapper.setDateFormat(new SimpleDateFormat(dateFormat));
			mapper.writeValue(gen, obj);
			gen.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return out.toString();
	}


	/**
	 * 将json字符串转换为指点对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		return toObject(json, clazz, DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 将json字符串转换为指点对象,指点json�? 日期字段格式
	 * @param json
	 * @param clazz
	 * @param dateFormat
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz, String dateFormat) {
		ObjectMapper mapper = new ObjectMapper();
		T t = null;
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.setDateFormat(new SimpleDateFormat(dateFormat));
			t = mapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return t;
	}

}