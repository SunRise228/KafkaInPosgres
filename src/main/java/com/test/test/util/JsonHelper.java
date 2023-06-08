package com.test.test.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.*;

public final class JsonHelper {

    @Getter
    private static ObjectMapper mapper = null;
    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.setDateFormat(sdf);
        mapper.registerModule(new JavaTimeModule());
    }

    private JsonHelper() {
    }

    public static String toJson(Object o) {
        if (o == null) {
            return "null";
        }
        String result = null;
        try {
            result = mapper.writeValueAsString(o);
        } catch(Exception e) {
            return null;
        }
        return result;
    }

    public static String toBeautifulJson(Object o) {
        if(o == null) {
            return "null";
        }
        String result = null;
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        }
        catch(Exception e) {
            return null;
        }
        return result;
    }

    public static <T> T fromJson(String json, Class<T> valueClass) {
        if (json == null) {
            return null;
        }
        T result;
        try {
            result = mapper.readValue(json, valueClass);
        } catch(Exception e) {
            return null;
        }
        return result;
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (json == null) {
            return null;
        }
        T result;
        try {
            result = mapper.readValue(json, typeReference);
        } catch(Exception e) {
            return null;
        }
        return result;
    }

    public static <T> T fromJson(String json, JavaType typeReference) {
        if (json == null) {
            return null;
        }
        T result;
        try {
            result = mapper.readValue(json, typeReference);
        } catch(Exception e) {
            return null;
        }
        return result;
    }

    public static String toFlatJson(String json) {
        var map = JsonHelper.fromJson(json, Map.class);
        return "{" + scan(map, "") + "}";
    }

    private static String scan(Object node, String path) {
        String result = "";

        if (node instanceof ArrayList) {
            int count = 1;
            for (Object o: ((ArrayList) node)) {
                if (count > 1) {
                    result += ",";
                }
                result += scan(o, path + count);
                count++;
            }
        }
        else if (node instanceof Map || node instanceof Set) {

            for (Map.Entry entry: ((LinkedHashMap<String, String>) node).entrySet()) {
                Object object = entry.getValue();
                if (result.length() > 0) {
                    result += ",";
                }
                String newPath = "";
                if (! "".equals(path)) {

                    if (! "".equals(entry.getKey())) {
                        newPath = path + "." + entry.getKey();
                    }
                    else {
                        newPath = path;
                    }
                }
                else {
                    newPath = "" + entry.getKey();
                }
                result += scan(object, newPath);
            }
        }
        else {
            String name = path;
            String value = (String) node;
            result = "\"" + name + "\": \"" + value + "\"";
        }
        return result;
    }
}