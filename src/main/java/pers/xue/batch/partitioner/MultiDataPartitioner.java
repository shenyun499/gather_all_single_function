package pers.xue.batch.partitioner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import pers.xue.batch.repository.CommonRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangzhixue
 * @date 2022/3/2 8:24 下午
 * @Description
 * 按照数据分区
 */
@Slf4j
public class MultiDataPartitioner implements Partitioner {

    /**
     * 用来标记当前分区是第几个分区，后面会加i然后放入ExecutionContext中，在后续监听中可以通过日志打印
     */
    private static final String PARTITION_KEY = "partition";

    @Autowired
    CommonRepository commonRepository;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        // 获取数据库总数据
        long count = commonRepository.count();
        // 根据gridSize 设置map大小，其实也就是分区有几个 （gridSize : 声明分区的HashMap的初始值大小)
        Map<String, ExecutionContext> map = new HashMap<>(gridSize);
        int i = 0;
            // new 一个分区
            ExecutionContext context = new ExecutionContext();
            // 标准当前分区信息
            map.put(PARTITION_KEY + i, context);
            i++;
        return map;
    }
}
