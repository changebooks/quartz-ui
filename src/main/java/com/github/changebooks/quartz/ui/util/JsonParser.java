package com.github.changebooks.quartz.ui.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * 解析Json
 *
 * @author changebooks
 */
public final class JsonParser {
    /**
     * 默认的时间格式
     */
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * Parser
     */
    private static final Gson PARSER = (new GsonBuilder()).
            disableHtmlEscaping().
            setDateFormat(PATTERN).
            create();

    private JsonParser() {
    }

    /**
     * Convert Object to Json String
     *
     * @param src the object
     * @return a json string
     */
    public static String toJson(Object src) {
        return PARSER.toJson(src);
    }

    /**
     * Convert Object to Json String
     *
     * @param src       the object
     * @param typeOfSrc new TypeToken<Collection<Foo>>(){}.getType();
     * @return a json string
     */
    public static String toJson(Object src, final Type typeOfSrc) {
        return PARSER.toJson(src, typeOfSrc);
    }

    /**
     * Convert Json String to Object
     *
     * @param json     the json string
     * @param classOfT the class of T
     * @return an object of type T from the string
     */
    public static <T> T fromJson(String json, final Class<T> classOfT) throws JsonSyntaxException {
        return PARSER.fromJson(json, classOfT);
    }

    /**
     * Convert Json String to Object
     *
     * @param json    the json string
     * @param typeOfT new TypeToken<Collection<Foo>>(){}.getType();
     * @return an object of type T from the string
     */
    public static <T> T fromJson(String json, final Type typeOfT) throws JsonSyntaxException {
        return PARSER.fromJson(json, typeOfT);
    }

}
