package com.hrms.hrpitch.onboarding.service.impl;

import com.hrms.hrpitch.common.dao.ClientDetails;
import com.hrms.hrpitch.common.dao.Login;
import com.hrms.hrpitch.common.dao.Role;
import com.hrms.hrpitch.common.dto.LoginData;
import com.hrms.hrpitch.common.events.GenericEvent;
import com.hrms.hrpitch.common.exceptions.DuplicateUserIdException;
import com.hrms.hrpitch.common.repository.ClientDetailsRepository;
import com.hrms.hrpitch.common.repository.LoginRepository;
import com.hrms.hrpitch.common.repository.RoleRepository;
import com.hrms.hrpitch.multitenancy.tenant.TenantContext;
import com.hrms.hrpitch.multitenancy.tenant.TenantDataSource;
import com.hrms.hrpitch.onboarding.dto.ClientDetailsData;
import com.hrms.hrpitch.onboarding.exceptions.DuplicateClientCodeException;
import com.hrms.hrpitch.onboarding.exceptions.InvalidClientCodeException;
import com.hrms.hrpitch.onboarding.service.ClientOnboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

import static com.hrms.hrpitch.common.utils.Constants.ADMIN_ACCOUNT_EXISTS;
import static com.hrms.hrpitch.onboarding.utils.OnboardingConstants.DUPLICATE_CLIENT_CODE;
import static com.hrms.hrpitch.onboarding.utils.OnboardingConstants.INVALID_CLIENT_CODE;

@Service
public class ClientOnboardingServiceImpl implements ClientOnboardingService {

    @Autowired
    LoginRepository loginRepo;

    @Autowired
    ClientDetailsRepository clientDetailsRepo;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    TenantDataSource tenantDataSource;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepo;

    @Override
    public void addClient(ClientDetailsData clientData) throws SQLException, IOException {
        String code = clientData.getClientCode().toLowerCase();
        if(isClientCodeExists(code))
            throw new DuplicateClientCodeException(DUPLICATE_CLIENT_CODE);
        ClientDetails details = populateClientDetails(clientData);
        clientDetailsRepo.save(details);
        tenantDataSource.updateConnections(code);
    }

    @Override
    public void addAdminCreds(LoginData data) {
        //we cannot use the isClientCodeExists method here, since the database might be switched from default to tenant
        //specific one which doesn't have the client_details table
        if(!tenantDataSource.hasValidDataSource(TenantContext.getCurrentTenant())) throw new InvalidClientCodeException(INVALID_CLIENT_CODE);
        if(isAdminUserAlreadyCreated()) throw new DuplicateUserIdException(ADMIN_ACCOUNT_EXISTS);
        loginRepo.save(populateLogin(data));
    }

    private boolean isAdminUserAlreadyCreated(){
        return loginRepo.count()>0;
    }

    private boolean isClientCodeExists(String code) {
        return clientDetailsRepo.findById(code).isPresent();
    }

    private Login populateLogin(LoginData loginData){
        Login currentLogin = new Login();
        currentLogin.setUsername(loginData.getUsername());
        currentLogin.setPassword(encoder.encode(loginData.getPassword()));
        currentLogin.setRoles(new HashSet<>(roleRepo.findAll()));
        return currentLogin;
    }

    private ClientDetails populateClientDetails(ClientDetailsData clientDetailsData){
        return new ClientDetails()
                .setClientCode(clientDetailsData.getClientCode().toLowerCase())
                .setClientAddress(clientDetailsData.getClientAddress())
                .setClientName(clientDetailsData.getClientName())
                .setClientEmail(clientDetailsData.getClientEmail())
                .setCountryLocated(clientDetailsData.getCountryLocated());
    }

    private LoginData populateLoginData(Login login){
        return new LoginData().setUsername(login.getUsername()).setPassword(login.getPassword());
    }
}
