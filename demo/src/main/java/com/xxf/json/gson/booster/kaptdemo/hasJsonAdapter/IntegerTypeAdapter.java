package com.xxf.json.gson.booster.kaptdemo.hasJsonAdapter;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;


/**
 * Description
 * <p>
 *
 * @Author: XGod  xuanyouwu@163.com  17611639080  https://github.com/NBXXF     https://blog.csdn.net/axuanqq
 * date createTime：2017/8/14
 * version 2.1.0
 */
public class IntegerTypeAdapter extends TypeAdapter<Integer> {
    @Override
    public void write(JsonWriter jsonWriter, Integer s) throws IOException {
        if (null == s) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(s);
        }
    }

    @Override
    public Integer read(JsonReader jsonReader) throws IOException {
        JsonToken peek = jsonReader.peek();
        jsonReader.setLenient(true);
        switch (peek) {
            case NUMBER:
                try {
                    return jsonReader.nextInt();
                } catch (NumberFormatException e) {
                    // 如果带小数点则会抛出这个异常
                    return (int) jsonReader.nextDouble();
                }
            case STRING:
                String result = jsonReader.nextString();
                if (result == null || "".equals(result)) {
                    return 0;
                }
                try {
                    return Integer.parseInt(result);
                } catch (NumberFormatException e) {
                    // 如果带小数点则会抛出这个异常
                    return (int) new BigDecimal(result).floatValue();
                }
            case NULL:
                jsonReader.nextNull();
                return null;
            default:
                throw new JsonSyntaxException("Expected long but was " + peek);
        }
    }
}
