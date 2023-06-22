package com.hrms.hrpitch.onboarding.dto;

import com.hrms.hrpitch.common.dto.LoginData;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ClientDetailsData {
    @NotNull(message = "ClientCode cannot be null")
    @Size(min = 4, max = 10, message = "CompanyCode must be between 4 and 10 characters")
    String clientCode;
    @NotNull(message = "ClientName cannot be null")
    @Size(min = 4, max = 20, message = "CompanyCode must be between 4 and 20 characters")
    String clientName;
    @NotNull(message = "ClientAddress cannot be null")
    @Size(min = 10, max = 100, message = "CompanyCode must be between 10 and 100 characters")
    String clientAddress;
    @NotNull(message = "CountryLocated cannot be null")
    @Size(min = 3, max = 20, message = "CompanyCode must be between 3 and 20 characters")
    String countryLocated;
    @NotNull(message = "ClientEmail cannot be null")
    @Email(message = "Please provide a valid email")
    @Size(min = 3, max = 20, message = "ClientEmail must be between 3 and 20 characters")
    String clientEmail;

}
