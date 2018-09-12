package com.vcs.bogdan.beans;

import java.util.List;

public class Person {

    public static final String TABLE = "person";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";

    private String id;
    private String name;
    private String surname;
    private List<Contract> list;

    public Person() {
    }

    private Person(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Person(List<Contract> list) {
        new Person(id, name, surname);
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Contract> getList() {
        return list;
    }

    public void setList(List<Contract> list) {
        this.list = list;
    }
}
