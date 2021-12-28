package pers.xue.batch.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.util.Iterator;
import java.util.List;

/**
 * @author huangzhixue
 * @date 2021/12/17 6:01 下午
 * @Description
 */
@Slf4j
public class ReadDBBeanReader implements ItemReader<CommonEntity> {

    @Autowired
    private CommonRepository commonRepository;
    private Iterator<CommonEntity> readDBBeanIterator;

    @Override
    public CommonEntity read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        if (readDBBeanIterator == null) {
            List<CommonEntity> commonEntities = commonRepository.findAll();
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
