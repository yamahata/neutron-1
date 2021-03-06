/*
 * Copyright Tata Consultancy Services, 2015.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.neutron.spi;

import org.junit.Assert;
import org.junit.Test;

import org.opendaylight.neutron.spi.JaxbTestHelper;
import org.opendaylight.neutron.spi.NeutronPort_ExtraDHCPOption;

public class NeutronPort_ExtraDHCPOptionJAXBTest {

    private static final String NeutronPort_ExtraDHCPOption_sourceJson = "{" + "\"opt_value\": \"123.123.123.456\", "
            + "\"opt_name\": \"server-ip-address\" "
            + "}";
    private static final String NeutronPort_ExtraDHCPOption_IPv4_sourceJson = "{" + "\"opt_value\": \"123.123.123.456\", "
            + "\"opt_name\": \"server-ip-address\", "
            + "\"ip_version\": 4"
            + "}";
    private static final String NeutronPort_ExtraDHCPOption_IPv6_sourceJson = "{" + "\"opt_value\": \"::ffff:123.123.123.456\", "
            + "\"opt_name\": \"server-ip-address\", "
            + "\"ip_version\": 6"
            + "}";

    @Test
    public void test_NeutronPort_ExtraDHCPOption_JAXB() {
        NeutronPort_ExtraDHCPOption portObject = new NeutronPort_ExtraDHCPOption();
        NeutronPort_ExtraDHCPOption testObject;
        try {
            testObject = (NeutronPort_ExtraDHCPOption) JaxbTestHelper.jaxbUnmarshall(
                    portObject, NeutronPort_ExtraDHCPOption_sourceJson);
        } catch (Exception e) {
            Assert.assertFalse("Tests Failed", true);
            return;
        }

        Assert.assertEquals("NeutronPort_ExtraDHCPOption JAXB Test 1: Testing opt_value failed", "123.123.123.456",
                            testObject.getValue());
        Assert.assertEquals("NeutronPort_ExtraDHCPOption JAXB Test 10: Testing opt_name failed",
                            "server-ip-address", testObject.getName());
        Assert.assertEquals("NeutronPort_ExtraDHCPOption JAXB Test 20: Testing opt_name failed",
                            4, testObject.getIpVersion().intValue());
    }

    @Test
    public void test_NeutronPort_ExtraDHCPOption_IPv4_JAXB() {
        NeutronPort_ExtraDHCPOption portObject = new NeutronPort_ExtraDHCPOption();
        NeutronPort_ExtraDHCPOption testObject;
        try {
            testObject = (NeutronPort_ExtraDHCPOption) JaxbTestHelper.jaxbUnmarshall(
                    portObject, NeutronPort_ExtraDHCPOption_IPv4_sourceJson);
        } catch (Exception e) {
            Assert.assertFalse("Tests Failed", true);
            return;
        }

        Assert.assertEquals("NeutronPort_ExtraDHCPOption_IPv4 JAXB Test 1: Testing opt_value failed",
                            "123.123.123.456", testObject.getValue());
        Assert.assertEquals("NeutronPort_ExtraDHCPOption_IPv4 JAXB Test 10: Testing opt_name failed",
                            "server-ip-address", testObject.getName());
        Assert.assertEquals("NeutronPort_ExtraDHCPOption_IPv4 JAXB Test 20: Testing opt_name failed",
                            4, testObject.getIpVersion().intValue());
    }

    @Test
    public void test_NeutronPort_ExtraDHCPOption_IPv6_JAXB() {
        NeutronPort_ExtraDHCPOption portObject = new NeutronPort_ExtraDHCPOption();
        NeutronPort_ExtraDHCPOption testObject;
        try {
            testObject = (NeutronPort_ExtraDHCPOption) JaxbTestHelper.jaxbUnmarshall(
                    portObject, NeutronPort_ExtraDHCPOption_IPv6_sourceJson);
        } catch (Exception e) {
            Assert.assertFalse("Tests Failed", true);
            return;
        }

        Assert.assertEquals("NeutronPort_ExtraDHCPOption_IPv6 JAXB Test 1: Testing opt_value failed",
                            "::ffff:123.123.123.456", testObject.getValue());
        Assert.assertEquals("NeutronPort_ExtraDHCPOption_IPv6 JAXB Test 10: Testing opt_name failed",
                            "server-ip-address", testObject.getName());
        Assert.assertEquals("NeutronPort_ExtraDHCPOption_IPv6 JAXB Test 20: Testing opt_name failed",
                            6, testObject.getIpVersion().intValue());
    }
}
