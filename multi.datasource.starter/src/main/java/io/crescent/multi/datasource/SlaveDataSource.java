package io.crescent.multi.datasource;

import java.util.List;
import java.util.Random;

/**
 * @author liuhongjun
 * @since 2019-06-27
 * <p>
 * <p>
 * TODO 支持权重
 */
public class SlaveDataSource {

    private List<String> slaveDataSourceKeys;

    private Random random;

    protected SlaveDataSource(List<String> slaveDataSourceKeys) {
        this.slaveDataSourceKeys = slaveDataSourceKeys;
        this.random = new Random(System.currentTimeMillis());
    }


    public Object getOne() {
        if (1 == slaveDataSourceKeys.size()) {
            return slaveDataSourceKeys.get(0);
        }
        return slaveDataSourceKeys.get(random.nextInt(slaveDataSourceKeys.size()));
    }
}
