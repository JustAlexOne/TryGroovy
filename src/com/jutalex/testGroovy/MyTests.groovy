package com.jutalex.testGroovy

import groovy.json.JsonBuilder
import org.testng.annotations.Test


class MyTests {

    @Test
    def testReadFile() {
        def file = new File('data1.txt')
        file.eachLine { line, index -> println "$index: $line"}
        def list = file.collect()
        assert list[0] == "Audi"
        assert list[1] == "BMW"
        assert list[2] == "Porsche"
    }

    @Test
    def testReadXml() {
        def customers = new XmlSlurper().parse(new File('data2.xml'))
        for (customer in customers.corporate.customer) {
            println "${customer.@name} works for ${customer.@company}"
        }
        def corporateCustomers = customers.corporate.customer.collect()
        assert corporateCustomers.size() == 3
    }

    @Test
    def testPerson() {
        Person person = new Person(19, 'John')
        assert person.@name == 'John'
        assert person.@age == 19
    }

    @Test
    def testJson() {
        Person person = new Person(19, 'John')
        JsonBuilder jsonBuilder = new JsonBuilder(person)
        def json = jsonBuilder.toString()
        assert json == '{"age":19,"name":"John"}'

        println jsonBuilder.toPrettyString()
    }

    @Test
    def testDate() {
        def today = new Date()
        println today
    }

    @Test
    def testNoBracetsForMethods() {
        def person = createPerson 19 , 'John'
        assert person.name == 'John'
        assert person.age == 19
    }

    Person createPerson(int age, String name) {
        new Person(age, name)
    }

    @Test
    def testMultipleAssignment() {
        def (x, y ,z) = [42, new Person(21, 'Jack'), 'hello']

        assert x == 42
        assert y.properties == new Person(21, 'Jack').properties
        assert y == new Person(21, 'Jack') // only works when equals is overriden for person (could be done by annotation @EqualsAndHashCode)
        assert z == 'hello'
    }

    @Test
    void testIntToString() {
        String string = (2 * 3 * 5).toString()
        assert string == '30'
    }

    @Test
    void testMinusString() {
        assert 'abc' - 'c' == 'ab'
        assert 'abc' - 'b' == 'ac'
        assert 'cabc' - 'c' - 'c' == 'ab'
    }

    @Test
    void testIsNumber() {
        assert '123'.isNumber()
        assert !'abc'.isNumber()
        assert !'a1'.isNumber()

    }

    @Test
    void testTimes() {
        String res = ''
        5.times {
            res += 'a'
        }
        assert res == 'aaaaa'
        assert res == 'a' * 5
    }

    @Test
    void testUpto() {
        String res = ''
        1.upto(5) {
            number -> res += number
        }
        assert res == '12345'
    }

    @Test
    void testStep() {
        String res = ''
        0.step(1, 0.2) {
            number -> res += number + ' '
        }
        assert res == '0 0.2 0.4 0.6 0.8 '
    }

    @Test
    void testCanonical() {
        /**
         * CanonicalPerson class is annotated with @Canonical which means it has toString, equals and hashcode autoinserted and several more things
         **/
        def canonicalPerson = new CanonicalPerson(21, 'John')
        println canonicalPerson
    }

    @Test
    void testListsInit() {
        List myList = [1, 2, 3]
        assert myList.size() == 3
        assert myList instanceof ArrayList
    }

    @Test
    void testListFromRange() {
        List myList = (0..100).toList()
        assert myList.size() == 101
        assert myList[0] == 0
        assert myList[100] == 100
    }

    @Test
    void testListGetByRange() {
        List myList = ['a', 'b', 'c', 'd', 'e']
        assert myList[0..2] == ['a', 'b', 'c']
        assert myList[2, 4] == ['c', 'e']

        myList[0..2] = ['x', 'y', 'z']
        assert myList == ['x', 'y', 'z', 'd', 'e']
    }

    @Test
    void testListManipulations() {
        List myList = ['a', 'b', 'c', 'd', 'e']
        assert myList[-1] == 'e'
        assert myList[-2] == 'd'

        myList[1..1] = [1, 2, 3]
        assert myList == ['a', 1, 2, 3, 'c', 'd', 'e']

        myList = []
        myList += 'a'
        assert myList == ['a']

        myList += ['b', 'c']
        assert myList == ['a', 'b', 'c']

        myList << 'd' << 'e'
        assert myList == ['a', 'b', 'c', 'd', 'e']

        myList = ['a', 'b']

        assert myList - ['b'] == ['a']
        assert myList * 2 == ['a', 'b', 'a', 'b']
    }
}
