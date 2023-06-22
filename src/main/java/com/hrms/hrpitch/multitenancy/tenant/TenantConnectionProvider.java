package com.hrms.hrpitch.multitenancy.tenant;

import com.hrms.hrpitch.common.events.GenericEvent;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class TenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl implements ApplicationRunner {
    private Map<String, DataSource> dbConnections = new HashMap<>();

    @Value("${default-database}")
    String appDb;

    @Autowired
    private DataSource defaultDb;

    @Autowired
    private ApplicationContext context;

    @Override
    protected DataSource selectAnyDataSource() {
        return dbConnections.get(appDb);
    }

    @PostConstruct
    public void load(){
        dbConnections.put(appDb, defaultDb);
    }

    @Override
    protected DataSource selectDataSource(String tenantId) {
        return dbConnections.get(tenantId) != null ? dbConnections.get(tenantId) : dbConnections.get(appDb);
    }

    @EventListener(condition = "#event?.getEventId() == T(com.hrms.hrpitch.onboarding.utils.OnboardingEvents).NEW_CLIENT_CONNECTION_EVENT")
    private void newClientListener(GenericEvent<Pair<String, DataSource>> event){
        dbConnections.put(event.getPayload().getFirst(), event.getPayload().getSecond());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
        dbConnections.putAll(tenantDataSource.loadConnections());
    }
}
