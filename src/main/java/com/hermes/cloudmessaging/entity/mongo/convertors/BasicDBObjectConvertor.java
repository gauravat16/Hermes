package com.hermes.cloudmessaging.entity.mongo.convertors;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * Convert Json String into {@link DBObject}
 */
@ReadingConverter
public class BasicDBObjectConvertor implements Converter<String, DBObject> {

    @Override
    public DBObject convert(String source) {
        return BasicDBObject.parse(source);
    }
}
