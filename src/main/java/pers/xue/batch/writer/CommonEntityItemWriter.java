package pers.xue.batch.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.util.List;

/**
 * @author huangzhixue
 * @date 2022/1/20 5:35 下午
 * @Description
 */
@Slf4j
public class CommonEntityItemWriter implements ItemWriter<CommonEntity> {

    @Autowired
    private CommonRepository commonRepository;

    @Override
    public void write(List<? extends CommonEntity> list) throws Exception {
        log.info("write record {}", list);
        // more logic can be filled
        commonRepository.saveAll(list);
    }
}
