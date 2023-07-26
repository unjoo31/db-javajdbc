package test;

import lombok.Builder;
import lombok.Getter;

@Getter

class Test{
    private String name;
    private Integer age;
    private Integer weight;

    @Builder
    public Test(String name, Integer age, Integer weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }
}
public class Builder05 {
    public static void main(String[] args) {
        Test test = Test.builder().age(10).build();
    }
}
