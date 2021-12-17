package pers.xue.jpa.processtor;

import org.springframework.batch.item.ItemProcessor;
import pers.xue.jpa.remote.ReadDBBean;

/**
 * @author huangzhixue
 * @date 2021/12/17 6:22 下午
 * @Description
 */
public class ReadDBBeanProcesstor implements ItemProcessor<ReadDBBean, ReadDBBean> {
    @Override
    public ReadDBBean process(ReadDBBean readDBBean) throws Exception {
        // todo: waiting to deal
        return null;
    }
}
