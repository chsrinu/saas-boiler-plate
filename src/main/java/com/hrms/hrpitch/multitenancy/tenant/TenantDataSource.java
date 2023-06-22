package com.hrms.hrpitch.multitenancy.tenant;

import com.hrms.hrpitch.common.dao.ClientDetails;
import com.hrms.hrpitch.common.events.GenericEvent;
import com.hrms.hrpitch.common.repository.ClientDetailsRepository;
import com.hrms.hrpitch.common.utils.Constants;
import com.hrms.hrpitch.common.utils.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.hrms.hrpitch.common.utils.Constants.CLIENT_INIT_SQL_PATH;
import static com.hrms.hrpitch.onboarding.utils.OnboardingEvents.NEW_CLIENT_CONNECTION_EVENT;

@Component
public class TenantDataSource {
    private HashMap<String, DataSource> dataSources = new HashMap<>();

    @Autowired
    ClientDetailsRepository repository;

    @Value("${spring.database-url}")
    String connectionUrl;

    @Value("${spring.datasource.username}")
    String userName;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private DataSource getDataSource(String code){
        if(dataSources.get(code)!=null){
            return dataSources.get(code);
        }
        DataSource ds = createDataSource(code);
        if(ds != null) dataSources.put(code, ds);
        return ds;
    }

    public void updateConnections(String code) throws SQLException {
        DataSource ds = createConnection(code);
        dataSources.put(code, ds);
        try(Connection connection = ds.getConnection()){
            ClassPathResource resource = new ClassPathResource(CLIENT_INIT_SQL_PATH);
            ScriptUtils.executeSqlScript(connection, resource);
        }
        GenericEvent<Pair<String, DataSource>> updateConnectionProviderEvent =
                new GenericEvent<Pair<String, DataSource>>(this, Pair.of(code, ds), NEW_CLIENT_CONNECTION_EVENT);
        eventPublisher.publishEvent(updateConnectionProviderEvent);
    }

    public Map<String, DataSource> loadConnections() {
        List<ClientDetails> clientData = repository.findAll();
        Map<String, DataSource> tempConn = new HashMap<>();
        for(ClientDetails detail: clientData){
            String code = detail.getClientCode();
            tempConn.put(code, getDataSource(code));
        }
        return tempConn;
    }

    private DataSource createDataSource(String code){
        Optional<ClientDetails> client = repository.findById(code);
        if(client.isPresent()){
            return createConnection(code);
        }
        return null;
    }

    private DataSource createConnection(String code){
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .username(userName)
                .password(password)
                .url(String.format("%s/%s?createDatabaseIfNotExist=true", connectionUrl,code))
                .build();
    }

    public boolean hasValidDataSource(String code){
        return dataSources.containsKey(code);
    }
}
