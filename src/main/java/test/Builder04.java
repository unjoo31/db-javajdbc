package test;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
class 인간 {
    private Integer id;
    private String username;
    private String password;
    private Boolean status; // true 활동, false 휴먼
    private Timestamp createdAt; // 가입날짜 now()

    @Builder
    public 인간(Integer id, String username, String password, Boolean status, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.createdAt = createdAt;
    }
}

public class Builder04 {
    public static void main(String[] args) {
        인간 s = 인간.builder().username("ssar").password("1234").build();
    }
}