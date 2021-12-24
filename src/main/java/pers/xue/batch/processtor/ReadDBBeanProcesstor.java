package pers.xue.batch.processtor;

import org.springframework.batch.item.ItemProcessor;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.remote.ReadDBBean;

/**
 * @author huangzhixue
 * @date 2021/12/17 6:22 下午
 * @Description
 */
public class ReadDBBeanProcesstor implements ItemProcessor<CommonEntity, ReadDBBean> {

    @Override
    public ReadDBBean process(CommonEntity commonEntity) {
        return ReadDBBean.builder().content(commonEntity.getContent()).build();
    }
}
