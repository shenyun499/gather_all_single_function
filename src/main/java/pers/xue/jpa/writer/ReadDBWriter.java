package pers.xue.jpa.writer;

import org.springframework.batch.item.ItemWriter;
import pers.xue.jpa.remote.ReadDBBean;

import java.util.List;

/**
 * @author huangzhixue
 * @date 2021/12/17 6:09 下午
 * @Description
 */
public class ReadDBWriter implements ItemWriter<ReadDBBean> {
    @Override
    public void write(List<? extends ReadDBBean> list) throws Exception {
        // todo: waiting to add logic
    }
}
