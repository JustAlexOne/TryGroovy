package com.justalex.testGroovy.beans

import groovy.transform.Canonical

@Canonical
class CanonicalPerson {

    int age
    String name

    CanonicalPerson(int age, String name) {
        this.age = age
        this.name = name
    }
}
