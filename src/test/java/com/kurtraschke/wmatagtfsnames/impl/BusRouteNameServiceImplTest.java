package com.kurtraschke.wmatagtfsnames.impl;

import java.io.IOException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BusRouteNameServiceImplTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BusRouteNameServiceImplTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(BusRouteNameServiceImplTest.class);
    }

    public void testRouteName() throws IOException {
        BusRouteNameServiceImpl brs = new BusRouteNameServiceImpl();

        String routeName = brs.getNameForRoute("5A");

        assertEquals(routeName, "DC-Dulles Line");

    }
}
