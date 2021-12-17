package pers.xue.jpa.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import pers.xue.jpa.remote.ReadDBBean;

/**
 * @author huangzhixue
 * @date 2021/12/17 6:01 下午
 * @Description
 */
public class ReadDBBeanReader implements ItemReader<ReadDBBean> {

    @Override
    public ReadDBBean read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // todo: waiting to add logic
        return null;
    }
}
