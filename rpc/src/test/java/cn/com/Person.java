package cn.com;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    String name;
    int age;
    String loginName;
    String pwd;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", loginName='" + loginName + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && name.equals(person.name) && loginName.equals(person.loginName) && pwd.equals(person.pwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, loginName, pwd);
    }
}
