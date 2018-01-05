package com.justalex.testGroovy

import com.justalex.testGroovy.beans.Person
import org.testng.annotations.Test

class CollectionsTests {

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

    @Test
    void testListIsCase() {
        List myList = ['a', 'b', 'c', 'd', 'e']
        assert myList.isCase('a')
        assert 'a' in myList

        /**
         * if my string is contained in list1 then do action1, if in list2 - do action2 and so on
         */
        def candidate = 'a'
        switch (candidate) {
            case myList: assert true
                break
            default: assert false
        }
    }

    @Test
    void testListMethods() {
        List myList = ['a', ['b', 'c'], 'd']
        assert myList.flatten() == ['a', 'b', 'c', 'd']
        assert [1, 2, 3].intersect([6, 2, 3, 5]) == [2, 3]
        assert [1, 2, 3].disjoint([6, 5])
        assert ![1, 2, 3].disjoint([1, 5])

        myList = [1, 2, 3]
        assert myList.pop() == 3
        assert myList == [1, 2]
        assert myList.reverse() == [2, 1]
        assert [3, 2, 1].sort() == [1, 2, 3]
    }

    @Test
    void testListSortWithClosure() {
        List myList = [[5, 6, 3, 4], [1, 0], [0, 1, 2]]
        myList = myList.sort { item -> item.size }
        assert myList == [[1, 0], [0, 1, 2], [5, 6, 3, 4]]
    }

    @Test
    void testListCollect() {
        def doubled = [1, 2, 3].collect {
            item -> item * 2
        }
        assert doubled == [2, 4, 6]

        def odd = [1, 2, 3].findAll {
            it % 2 == 1
//            or: item -> item % 2 == 1
        }
        assert odd == [1, 3]
    }

    @Test
    void testListCollect2() {
        List persons = [new Person(1, 'a'), new Person(2, 'b'), new Person(3, 'b')]
        List ages = persons.collect { "$it.age" }
        assert ages == ['1', '2', '3']
    }

    @Test
    void testList_StarAsCollect() {
        List persons = [new Person(1, 'a'), new Person(2, 'b'), new Person(3, 'b')]
        List ages = persons*.getAge()
        assert ages == [1, 2, 3]
    }

    @Test
    void testListEvery_Any() {
        assert [1, 2, 3].every { item -> item < 4 }
        assert [1, 2, 3].any { item -> item > 2 }
    }

    @Test
    void testListFindVsFindAll() {
        List myList = [1, 2, 3]
        assert myList.find { it > 2 } == 3
        assert myList.findAll { it > 1 } == [2, 3]
    }

}
