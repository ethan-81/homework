package com.homework.mpay.common.config.database;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {
    private static final String readWriteDataSourceName = "read-write";
    private static final String readOnlyDataSourceName = "read-only";

    private final DataSource defaultDataSource;
    private final Map<Object, Object> dataSourceMap;

    public static RoutingDataSource initDataSource(
            DataSource readWriteDataSource, DataSource readOnlyDataSource) {
        RoutingDataSource routingDataSource =
                new RoutingDataSource(readWriteDataSource, readOnlyDataSource);
        routingDataSource.setDataSource();
        return routingDataSource;
    }

    public RoutingDataSource(DataSource readWriteDataSource, DataSource readOnlyDataSource) {
        super();
        this.defaultDataSource = readWriteDataSource;
        this.dataSourceMap =
                Map.ofEntries(
                        Map.entry(RoutingDataSource.readWriteDataSourceName, readWriteDataSource),
                        Map.entry(RoutingDataSource.readOnlyDataSourceName, readOnlyDataSource));
    }

    private void setDataSource() {
        super.setTargetDataSources(this.dataSourceMap);
        super.setDefaultTargetDataSource(this.defaultDataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? RoutingDataSource.readOnlyDataSourceName
                : RoutingDataSource.readWriteDataSourceName;
    }
}
