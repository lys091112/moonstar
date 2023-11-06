package io.crescent.multi.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author liuhongjun
 * @since 2019-06-27
 * <p>
 * TODO slave 支持权重
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    private static AtomicInteger slaveIndex = new AtomicInteger(0);
    private Map<Object, SlaveDataSource> slaveDataSources;

    private DynamicDataSource() {
        this.slaveDataSources = new HashMap<>(4);
    }

    void setSlaveDataSources(Map<Object, List<String>> slaveDataSources) {
        slaveDataSources.forEach((k, v) -> this.slaveDataSources.put(k, new SlaveDataSource(v)));
    }

    /**
     * 动态获取当前线程绑定的key
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String key = DataSourceHolder.get();

        // slave节点
        if (slaveDataSources.containsKey(key)) {
            return slaveDataSources.get(key).getOne();
        }
        return key;
    }

    public final class DynamicDataSourceBuilder {

        private Object defaultTargetDatasource;
        private Map<Object, Object> targetDataSources;
        private Map<Object, List<String>> slaveDataSources;

        public DynamicDataSourceBuilder() {
            this.targetDataSources = new HashMap<>();
            this.slaveDataSources = new HashMap<>();
        }

        public DynamicDataSourceBuilder defaultTargetDatasource(Object defaultTargetDatasource) {
            this.defaultTargetDatasource = defaultTargetDatasource;
            return this;
        }

        public DynamicDataSourceBuilder datasource(Object key, Object dataSource) {
            this.targetDataSources.put(DataSourceType.NORMAL.getDsKey(key), dataSource);
            return this;
        }

        public DynamicDataSourceBuilder masterDatasource(Object key, Object dataSource) {
            this.targetDataSources.put(DataSourceType.MASTER.getDsKey(key), dataSource);
            return this;
        }

        public DynamicDataSourceBuilder slaveDatasource(Object key, Object dataSource) {
            String slaveKey = DataSourceType.SLAVE.getDsKey(key);
            String slaveKeyIndex = slaveKey + slaveIndex.incrementAndGet();
            this.targetDataSources.put(slaveKeyIndex, dataSource);
            if (!slaveDataSources.containsKey(slaveKey)) {
                slaveDataSources.put(slaveKey, new ArrayList<>());
            }
            slaveDataSources.get(slaveKey).add(slaveKeyIndex);
            return this;
        }

        public DynamicDataSource build() {
            DynamicDataSource myBeanDataSource = new DynamicDataSource();
            myBeanDataSource.setSlaveDataSources(this.slaveDataSources);
            myBeanDataSource.setTargetDataSources(targetDataSources);
            myBeanDataSource.setDefaultTargetDataSource(defaultTargetDatasource);
            return myBeanDataSource;
        }
    }
}
