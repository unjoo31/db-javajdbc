package test;

import java.sql.Timestamp;

// 값을 하나씩 받는 것 보다 객체로 만들어서 한번에 받는 게 좋다
// 생성자 오버로딩을 한다
// 유저한테 받아야하는 컬럼만 받는다(그렇게 되면 너무 코드가 길어지고 복잡해진다)
class User{
    private Integer id;
    private String username;
    private String password;
    private Boolean status; // true 활동, false 휴먼
    private Timestamp createAt; // 가입날짜 now()
}
public class Builder02 {
    public static void main(String[] args) {

    }
}
