package com.jutalex.testGroovy

import com.jutalex.testGroovy.beans.*
import groovy.json.JsonBuilder
import groovy.test.GroovyAssert
import org.testng.annotations.Test

class GeneralTests extends GroovyTestCase /*needed to get methods like shouldFail()*/ {

    @Test
    def testReadFile() {
        def file = new File('data1.txt')
        file.eachLine { line, index -> println "$index: $line" }
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
        def person = createPerson 19, 'John'
        assert person.name == 'John'
        assert person.age == 19
    }

    Person createPerson(int age, String name) {
        new Person(age, name)
    }

    @Test
    def testMultipleAssignment() {
        def (x, y, z) = [42, new Person(21, 'Jack'), 'hello']

        assert x == 42
        assert y.properties == new Person(21, 'Jack').properties
        assert y == new Person(21, 'Jack')
        // only works when equals is overriden for person (could be done by annotation @EqualsAndHashCode)
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
    void testRanges() {
        def x = 1..10
        assert x.contains(5) // not implemented for doubles, floats and BigDecimals
        assert x.contains(15) == false
        assert x.size() == 10
        assert x.from == 1
        assert x.to == 10
        assert x.reverse() == 10..1
    }

    @Test
    void testLoopWithRange() {
        for (def i in 0..10) print "$i " // 0 1 2 3 4 5 6 7 8 9 10
        println()
        for (def i in 0..<10) print "$i " // 0 1 2 3 4 5 6 7 8 9
    }

    @Test
    void testRemoveExtension() {
        String str = 'piter.gif'
        str = str[0..-5]
        assert str == 'piter'
    }

    @Test
    void testSwitchCase() {
        def x = new Employee()
        switch (x) {
            case Employee: println 'this is Employee'
                break
            case Customer: println 'this is customer'
                break
            default: println 'other...'
        }
    }

    @Test
    void testInitWithNamedParams() {
        User user = new User(age: 10, name: 'John')
        User user2 = new User(name: 'John', age: 10)
        assert user == user2
    }

    @Test
    void testInsertInstanceMethod() {
        User user = new User(age: 10, name: 'John')
        user.metaClass.sayHello = {
            println "Hello"
        }
        user.sayHello()
    }

    @Test
    void testAssertExpectedException() {
        // Example 1
        String errorMsg = shouldFail(MissingMethodException) {
            new User(name: 'John', age: 15).someMethod()
        }
        assert errorMsg.startsWith('No signature of method')

        // Example 2
        Throwable error = GroovyAssert.shouldFail(MissingMethodException) {
            new User(name: 'John', age: 15).someMethod()
        }
        assert error.message.startsWith('No signature of method')
    }

    @Test
    void testInsert() {
        assert ['Hubert', 'Alexander', 'Klein', 'Ikkink'].inject('mr') { nickname, name ->
            nickname + name[0].toLowerCase()
        } == 'mrhaki'
    }

    @Test
    void testInsertClassMethod() {
        User user = new User(age: 10, name: 'John')

        // instances created before method class method insertion, will not have new methods
        def errorMsg = shouldFail(MissingMethodException) {
            user.getPrettyName()
        }
        assert errorMsg.startsWith('No signature of method')

        // delegate - means current instance (like this)
        User.metaClass.getPrettyName = { "My name is $delegate.name and I am $delegate.age years old" }
        User user1 = new User(age: 10, name: 'John')
        println user1.getPrettyName()
        assert user1.getPrettyName() == "My name is John and I am 10 years old"

        User user2 = new User(age: 15, name: 'Jack')
        assert user2.getPrettyName() == "My name is Jack and I am 15 years old"
        println user2.getPrettyName()
    }

    @Test
    void testInsertClassMethodExample() {
        ArrayList.metaClass.calcDuplicates = { def anyObject, Closure c ->
            int duplicatesCount = 0
            delegate.each {
                if (c.call(anyObject, it)) { // the closure takes 2 arguments
                    duplicatesCount++
                }
            }
            duplicatesCount
        }

        List users = [new User(name: 'a', age: 10), new User(name: 'b', age: 15), new User(name: 'a', age: 10), new User(name: 'c', age: 10)]
        int duplicatesByName = users.calcDuplicates(new User(name: 'a', age: 10)) {
                // user1 and user2 are the arguments for closure
            user1, user2 -> user1.name == user2.name
        }
        assert duplicatesByName == 2
        println "Duplicates by name $duplicatesByName"

        // what if we assume duplicate user is the one with the same age? Then we can just pass another closure to the method
        int duplicatesByAge = users.calcDuplicates(new User(name: 'whatever', age: 10)) {
            user1, user2 -> user1.age == user2.age
        }
        assert duplicatesByAge == 3
    }
}
