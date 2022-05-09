package pers.xue.batch.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.util.Iterator;
import java.util.List;

/**
 * @author huangzhixue
 * @date 2022/3/4 1:17 下午
 * @Description
 */
@Slf4j
public class PartitionMultiDataReader implements ItemReader<CommonEntity> {

    private Integer page;
    private Integer pageSize;
    private CommonRepository commonRepository;
    public PartitionMultiDataReader(Integer page, Integer pageSize, CommonRepository commonRepository) {
        this.page = page;
        this.pageSize = pageSize;
        this.commonRepository = commonRepository;
    }

    private Iterator<CommonEntity> readDBBeanIterator;

    @Override
    public CommonEntity read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        if (readDBBeanIterator == null) {
            Page<CommonEntity> commonEntities = commonRepository.findByContent("神韵学Spring Batch", PageRequest.of(page, pageSize));
            readDBBeanIterator = commonEntities.iterator();
        }
        if (readDBBeanIterator.hasNext()) {
            CommonEntity commonEntity = readDBBeanIterator.next();
            log.info("read record {}", commonEntity);
            return commonEntity;
        }
        return null;
    }
}
