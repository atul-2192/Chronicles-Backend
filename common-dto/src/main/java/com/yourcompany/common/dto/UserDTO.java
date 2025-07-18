package com.yourcompany.common.dto;

public class UserDTO {
    private String email;
    private String name;
    private String role;

    public UserDTO() {}

    public UserDTO(String email, String name, String role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
