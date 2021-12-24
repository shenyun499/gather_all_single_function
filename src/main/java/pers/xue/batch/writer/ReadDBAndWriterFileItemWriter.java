package pers.xue.batch.writer;

import org.springframework.batch.item.ItemWriter;
import pers.xue.batch.entity.CommonEntity;

import java.util.List;

/**
 * @author huangzhixue
 * @date 2021/12/23 6:02 下午
 * @Description
 */
public class ReadDBAndWriterFileItemWriter implements ItemWriter<CommonEntity> {

    @Override
    public void write(List<? extends CommonEntity> list) throws Exception {

    }
}
