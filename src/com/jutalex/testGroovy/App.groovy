package com.jutalex.testGroovy


class App {

    void sayHello() {
        println("hello!")
        assert 1 == 2
    }

    static void main(String[] args) {
//        def app = new App()
//        app.sayHello()

        String newS = "../input_data_files/operations/bandwidth.toml\n" +
                "../input_data_files/operations/bandwidth_cluster.toml\n" +
                "../input_data_files/operations/bandwidth_engine.toml\n" +
                "../input_data_files/operations/qps.toml\n" +
                "../input_data_files/operations/qps_20eng.toml\n" +
                "../input_data_files/operations/qps_cluster.toml\n" +
                "../input_data_files/operations/qps_engine.toml\n" +
                "../input_data_files/operations/qtype.toml\n" +
                "../input_data_files/operations/qtype_cluster.toml\n" +
                "../input_data_files/operations/qtype_engine.toml\n" +
                "../input_data_files/operations/rcode.toml\n" +
                "../input_data_files/operations/rcode_cluster.toml\n" +
                "../input_data_files/operations/rcode_engine.toml\n" +
                "../input_data_files/operations/recursion_contexts.toml\n" +
                "../input_data_files/operations/recursion_contexts_M.toml\n" +
                "../input_data_files/operations/recursion_contexts_cluster.toml\n" +
                "../input_data_files/operations/recursion_contexts_engine.toml"

        String old = "../input_data_files/operations/qps_20eng.toml\n" +
                "../input_data_files/operations/recursion_contexts_M.toml\n" +
                "../input_data_files/operations/bandwidth.toml\n" +
                "../input_data_files/operations/bandwidth_cluster.toml\n" +
                "../input_data_files/operations/qps.toml\n" +
                "../input_data_files/operations/rcode.toml\n" +
                "../input_data_files/operations/rcode_cluster.toml\n" +
                "../input_data_files/operations/recursion_contexts.toml\n" +
                "../input_data_files/operations/recursion_contexts_cluster.toml\n" +
                "../input_data_files/operations/bandwidth_engine.toml\n" +
                "../input_data_files/operations/qps_cluster.toml\n" +
                "../input_data_files/operations/qps_engine.toml\n" +
                "../input_data_files/operations/rcode_engine.toml\n" +
                "../input_data_files/operations/recursion_contexts_engine.toml\n" +
                "../input_data_files/systems/memory.toml\n" +
                "../input_data_files/systems/memory_cluster.toml\n" +
                "../input_data_files/systems/udp_tcp.toml\n" +
                "../input_data_files/systems/udp_tcp_cluster.toml\n" +
                "../input_data_files/systems/memory_engine.toml\n" +
                "../input_data_files/systems/udp_tcp_engine.toml"

        def list_new = newS.split("\n").collect()
        def list_old = old.split("\n").collect()
        def diff =  list_old - list_new

        println "new list " + list_new.size()
        println "old list " + list_old.size()
        println "dif  list " + diff.size()
        println "diff" + diff
        diff.each{ println it}
//        assert list_new == list_old
    }
}
