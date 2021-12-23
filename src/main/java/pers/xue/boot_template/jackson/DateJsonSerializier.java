package pers.xue.boot_template.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 响应指定格式处理。如，需要响应的时间格式为dd-MM-yyyy HH:mm，则进行转换
 */
public class DateJsonSerializier extends JsonSerializer<LocalDateTime> {

    /**
     * format pattern: dd-MM-yyyy HH:mm
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(localDateTime));
    }
}
