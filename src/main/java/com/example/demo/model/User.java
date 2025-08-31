package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_table")
public class User {

    @Id
    private String _id; // MongoDB 기본 PK

    private String id; // 사용자 정의 ID
    private String password;
    private String line;
    private String authorization;
    private Boolean isAuthenticated;

    // 생성자
    public User() {}

    public User(String id, String password, String line, String authorization, Boolean isAuthenticated) {
        this.id = id;
        this.password = password;
        this.line = line;
        this.authorization = authorization;
        this.isAuthenticated = isAuthenticated;
    }

    // getter / setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getLine() { return line; }
    public void setLine(String line) { this.line = line; }

    public String getAuthorization() { return authorization; }
    public void setAuthorization(String authorization) { this.authorization = authorization; }

    public Boolean getIsAuthenticated() { return isAuthenticated; }
    public void setIsAuthenticated(Boolean isAuthenticated) { this.isAuthenticated = isAuthenticated; }
}