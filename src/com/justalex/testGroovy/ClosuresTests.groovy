package com.justalex.testGroovy

import com.justalex.testGroovy.beans.Person
import org.testng.annotations.Test


class ClosuresTests {

    @Test
    void initClosure() {
        def sayHello = {println 'Hello!'}
        sayHello.call() // or sayHello()

        def sayHelloTo = {person -> println "Hello $person!"}
        sayHelloTo('John')

        def replaced = applyFunctionOnString('Boss') { it.replace('o', 'a')}
// or   def replaced = applyFunctionOnString('Boss') { String str -> str.replace('o', 'a') }
        assert replaced == 'Bass'
    }

    String applyFunctionOnString(String str, Closure func) {
        func(str)
    }

    @Test
    void customCompareTo() {
        /**
         * Need to sort persons in list, comparing by name and if names are equal, then compare by age
         */
        List persons = [new Person(5, 'a'), new Person(1, 'b'), new Person(4, 'a'), new Person(19, 'c')]
        persons.sort({ a, b -> a.name <=> b.name ?: a.age <=> b.age })
        // or persons.sort({ a, b -> a.name.compareTo(b.name) ?: a.age.compareTo(b.age) })
        assert persons == [new Person(4, 'a'), new Person(5, 'a'), new Person(1, 'b'), new Person(19, 'c')]
    }

}
