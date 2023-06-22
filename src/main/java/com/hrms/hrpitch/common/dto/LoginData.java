package com.hrms.hrpitch.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Accessors(chain = true)
@Getter
@Setter
public class LoginData {
    @NotNull(message = "userId cannot be null")
    @Size(min=4, max =20, message = "userId must be between 4 and 20 characters")
    private String username;
    @NotNull(message = "password cannot be null")
    @Size(min=4, max =20,message = "password must be between 4 and 20 characters")
    private String password;
}
