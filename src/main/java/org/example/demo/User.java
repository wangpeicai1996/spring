package org.example.demo;

import org.springframework.beans.factory.InitializingBean;

public class User implements InitializingBean {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("属性设置完成之后，自定义初始化方法");
    }
}
