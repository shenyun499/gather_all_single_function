package pers.xue.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

import java.util.List;

/**
 * @author huangzhixue
 * @date 2021/12/23 6:01 下午
 * @Description
 */
public class ReadDBAndWriterFileItemReader implements ItemReader<List<CommonEntity>> {

    @Autowired
    private CommonRepository commonRepository;

    @Override
    public List<CommonEntity> read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        return commonRepository.findAll();
    }
}
