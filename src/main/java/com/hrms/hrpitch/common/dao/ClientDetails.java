package com.hrms.hrpitch.common.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;

@Accessors(chain = true)
@Getter
@Setter
@Entity(name = "client_details")
public class ClientDetails {
    @Id
    String clientCode;
    String clientName;
    String clientAddress;
    String clientEmail;
    String countryLocated;

}
