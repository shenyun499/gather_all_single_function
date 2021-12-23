package pers.xue.datasource.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @auther huangzhixue
 * @data 2021/6/29 3:16 下午
 * @Description
 * 从客户端接收 2021-06-29T06:26:58.272Z
 * 格式处理一下才可以接收，否则会com.fasterxml.jackson.databind.exc.InvalidDefinitionException
 */
public class DateJsonDeserialize extends JsonDeserializer<LocalDateTime> {

    /**
     * format pattern: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return LocalDateTime.parse(jsonParser.getText(), formatter);
    }
}
