package com.justalex.testGroovy.examples

import org.testng.annotations.Test


class MapsExamples {

    @Test
    void testMapInit() {
        def map = [a: 1, b: 2]
        assert map.a == 1
        assert map['a'] == 1
    }

}
