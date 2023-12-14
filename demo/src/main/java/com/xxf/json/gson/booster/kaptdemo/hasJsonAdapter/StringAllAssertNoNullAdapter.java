package com.xxf.json.gson.booster.kaptdemo.hasJsonAdapter;


import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Description  :【反序列化+序列化】 断言不能为null 如果null 那么自动报错
 *
 * @Author: XGod  xuanyouwu@163.com  17611639080  https://github.com/NBXXF     https://blog.csdn.net/axuanqq
 * date createTime：2017/7/19
 * version 2.0.0
 */
public class StringAllAssertNoNullAdapter extends TypeAdapter<String> {

    @Override
    public String read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            throw new JsonParseException("String is null");
        }
        return reader.nextString();
    }

    @Override
    public void write(JsonWriter writer, String value) throws IOException {
        if (value == null) {
            throw new JsonParseException("String is null");
        }
        writer.value(value);
    }
}
