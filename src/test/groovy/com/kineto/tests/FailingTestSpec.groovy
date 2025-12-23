package com.kineto.tests

import spock.lang.Specification
import spock.lang.Title
import spock.lang.Tag

/**
 * Intentionally failing test for demonstration purposes
 * This test is designed to fail to verify TeamCity reporting
 */
@Title("Failing Test Demo")
@Tag("demo")
@Tag("failing")
class FailingTestSpec extends Specification {

    def "DEMO-FAIL-01: This test intentionally fails to demonstrate failure reporting"() {
        given: "A condition that should be true"
        def expected = "Published"
        
        when: "We check the actual status"
        def actual = "Draft"  // Intentionally wrong value
        
        then: "The status should match (THIS WILL FAIL)"
        actual == expected
    }

    def "DEMO-PASS-01: This test passes for comparison"() {
        given: "A simple condition"
        def value = 42
        
        when: "We check the value"
        def result = value * 2
        
        then: "The result is correct"
        result == 84
    }
}
