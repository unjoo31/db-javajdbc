package test;

import lombok.Getter;

@Getter
class Person {
    // 모델은 랩핑클래스를 사용한다
    // int를 사용하지 않고 null 값을 사용하기 위해서 Integer를 사용함
    private Integer id;
    private String name;
    private Integer age;

    public Person(Integer id, String name, Integer age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
public class Builder01 {

    public static void main(String[] args) {
        Person p1 = new Person(null, "홍길동", 10);
    }
}
