package pers.xue.batch.partitioner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangzhixue
 * @date 2022/3/1 5:17 下午
 * @Description
 */
@Slf4j
public class MultiResourcePartitioner implements Partitioner {

    /**
     * 用来标记当前分区是第几个分区，后面会加i然后放入ExecutionContext中，在后续监听中可以通过日志打印
     */
    private static final String PARTITION_KEY = "partition";

    /**
     * 放入ExecutionContext中当作key，后面在reader时通过这个key拿到文件resource解析
     */
    private static final String keyName = "fileName";

    /**
     * 文件资源
     */
    private Resource[] resources;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        // 根据gridSize 设置map大小，其实也就是分区有几个 （gridSize : 声明分区的HashMap的初始值大小)
        Map<String, ExecutionContext> map = new HashMap<>(gridSize);
        int i = 0;
        for (Resource resource : resources) {
            // new 一个分区
            ExecutionContext context = new ExecutionContext();
            Assert.state(resource.exists(), "Resource does not exist: " + resource);
            try {
                String filePath = resource.getURI().getPath();
                log.info("Add file path to partition: {}", filePath);
                context.putString(keyName, filePath);
            }
            catch (IOException e) {
                throw new IllegalArgumentException("File could not be located for: "+resource, e);
            }
            // 标准当前分区信息
            map.put(PARTITION_KEY + i, context);
            i++;
        }
        return map;
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }
}
