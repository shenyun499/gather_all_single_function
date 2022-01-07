package pers.xue.batch.writer;

import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonObjectMarshaller;
import org.springframework.core.io.Resource;
import pers.xue.batch.entity.CommonEntity;

import java.nio.charset.StandardCharsets;

/**
 * @author huangzhixue
 * @date 2022/1/7 3:01 下午
 * @Description
 */
public class ReadDBAndWriteJsonFileWriter extends JsonFileItemWriter<CommonEntity> {

    public ReadDBAndWriteJsonFileWriter(Resource resource, JsonObjectMarshaller<CommonEntity> jsonObjectMarshaller) {
        super(resource, jsonObjectMarshaller);
        this.setResource(resource);
        this.setJsonObjectMarshaller(jsonObjectMarshaller);
        this.setEncoding(StandardCharsets.UTF_8.name());
        this.setName("readDBAndWriteJsonFileWriter");
    }
}
