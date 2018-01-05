package com.jutalex.testGroovy

import org.testng.annotations.Test


class MapsTests {

    @Test
    void testMapInit() {
        def map = [a: 1, b: 2]
        assert map.a == 1
        assert map['a'] == 1
    }

}
