package pers.xue.batch.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import pers.xue.batch.remote.ReadDBBean;

import java.util.List;

/**
 * @author huangzhixue
 * @date 2021/12/17 6:09 下午
 * @Description
 */
@Slf4j
public class ReadDBWriter implements ItemWriter<ReadDBBean> {
    @Override
    public void write(List<? extends ReadDBBean> list) throws Exception {
        log.info("write record {}", list);
    }
}
