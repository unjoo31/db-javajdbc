package test;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
class Member {
    private Integer id;
    private String username;
    private String password;
    private Boolean status; // true 활동, false 휴먼
    private Timestamp createdAt; // 가입날짜 now()

    public Member id(Integer id){
        this.id = id;
        return this;
    }

    public Member username(String username){
        this.username = username;
        return this;
    }

    public Member password(String password){
        this.password = password;
        return this;
    }

    public static Member builder(){
        return new Member();
    }
}

public class Builder03 {

    public static void main(String[] args) {
        Member m1 = Member.builder().password("1234").id(1).username("ssar");
    }
}