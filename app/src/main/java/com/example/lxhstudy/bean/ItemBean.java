package com.example.lxhstudy.bean;

public class ItemBean {
    private String name;
    private Class aClass;

    public ItemBean(String name, Class aClass) {
        this.name = name;
        this.aClass = aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
