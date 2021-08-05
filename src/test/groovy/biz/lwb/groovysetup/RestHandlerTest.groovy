package biz.lwb.groovysetup


import spock.lang.Specification

class RestHandlerTest extends Specification {

    def AppServer appServer

    def setup() {
        println "setup"
    }

    def "server starts"() {
        appServer = new AppServer()
        appServer.startServer()

        expect:
        true
    }

    def cleanup() {
        println("cleanup")
        appServer.setShutdownHook()
    }


}
