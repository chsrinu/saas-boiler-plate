package com.hrms.hrpitch.onboarding.service;

import com.hrms.hrpitch.common.dto.LoginData;
import com.hrms.hrpitch.onboarding.dto.ClientDetailsData;

import java.io.IOException;
import java.sql.SQLException;

public interface ClientOnboardingService {
    public void addClient(ClientDetailsData details) throws SQLException, IOException;
    public void addAdminCreds(LoginData data);
}
