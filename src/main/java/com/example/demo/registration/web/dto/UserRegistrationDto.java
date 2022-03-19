package com.example.demo.registration.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationDto {
    @NotNull
    @Size(min = 1, max = 32)
    private String firstName;
    
    @NotNull
    @Size(min = 1, max = 32)
    private String lastName;

    @NotNull
    @Email
    private String emailAddress;
    
    @NotNull
    @Size(min = 8, max = 255)
    private String password;

    public UserRegistrationDto() {
        // TODO Auto-generated constructor stub
    }

    public UserRegistrationDto(String firstName, String lastName, String emailAddress, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
