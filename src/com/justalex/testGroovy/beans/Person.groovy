package com.justalex.testGroovy.beans
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Person {

    int age
    String name

    Person(int age, String name) {
        this.age = age
        this.name = name
    }
}
