package com.brajesh.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Long teamId;
}


