/*
 * Copyright Tata Consultancy Services, 2015.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.neutron.spi;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.opendaylight.neutron.spi.JaxbTestHelper;
import org.opendaylight.neutron.spi.NeutronPort;

public class NeutronPortJAXBTest {

    private static final String NeutronPort_sourceJson = "{ "
            + "\"status\": \"ACTIVE\", "
            + "\"name\": \"net1\", "
            + "\"admin_state_up\": true, "
            + "\"tenant_id\": \"9bacb3c5d39d41a79512987f338cf177\", "
            + "\"fixed_ips\": [ { \"ip_address\":\"192.168.111.3\" , \"subnet_id\": \"22b44fc2-4ffb-4de4-b0f9-69d58b37ae27\" } ],"
            + "\"binding:vif_details\": [{\"port_filter\": true , \"ovs_hybrid_plug\": true } ], "
            + "\"extra_dhcp_opts\": [\"\"], " + "\"security_groups\": [\"\"], " + "\"allowed_address_pairs\": [\"\"], "
            + "\"device_id\": \"257614cc-e178-4c92-9c61-3b28d40eca44\", " + "\"device_owner\": \"\", "
            + "\"binding:host_id\": \"\", " + "\"binding:vif_type\": \"unbound\", "
            + "\"binding:vnic_type\": \"normal\", " + "\"mac_address\": \"fa:16:3e:c9:cb:f0\", "
            + "\"network_id\": \"e9330b1f-a2ef-4160-a991-169e56ab17f5\", "
            + "\"id\": \"4e8e5957-649f-477b-9e5b-f1f75b21c03c\" }";

    @Test
    public void test_NeutronPort_JAXB() {
        NeutronPort testObject = new NeutronPort();
        try {
            NeutronPort neutronObject = (NeutronPort) JaxbTestHelper.jaxbUnmarshall(testObject, NeutronPort_sourceJson);
            Assert.assertEquals("NeutronPort JAXB Test 1: Testing id failed", "4e8e5957-649f-477b-9e5b-f1f75b21c03c",
                    neutronObject.getID());

            Assert.assertEquals("NeutronPort JAXB Test 2: Testing tenant_id failed",
                    "9bacb3c5d39d41a79512987f338cf177", neutronObject.getTenantID());

            Assert.assertEquals("NeutronPort JAXB Test 3: Testing network_id failed",
                    "e9330b1f-a2ef-4160-a991-169e56ab17f5", neutronObject.getNetworkUUID());

            Assert.assertEquals("NeutronPort JAXB Test 4: Testing status failed", "ACTIVE", neutronObject.getStatus());

            List<Neutron_IPs> fixedIPs = neutronObject.getFixedIPs();
            Assert.assertEquals(" NeutronPort JAXB Test 5.1: Testing fixed_ips list length failed", 1, fixedIPs.size());

            Assert.assertEquals(" NeutronPort JAXB Test 5.2: Testing ip_address value failed", "192.168.111.3",
                    fixedIPs.get(0).ipAddress);

            Assert.assertEquals(" NeutronPort JAXB Test 5.3: Testing subnet_id value failed",
                    "22b44fc2-4ffb-4de4-b0f9-69d58b37ae27", fixedIPs.get(0).subnetUUID);

            List<NeutronPort_ExtraDHCPOption> extraDHCPOptions = neutronObject.getExtraDHCPOptions();

            Assert.assertEquals("NeutronPort JAXB Test 6: Testing extra_dhcp_opts list length failed", 1,
                    extraDHCPOptions.size());

            List<NeutronSecurityGroup> securityGroups = neutronObject.getSecurityGroups();
            Assert.assertEquals("NeutronPort JAXB Test 7: Testing security_groups list length failed", 1,
                    securityGroups.size());

            List<NeutronPort_AllowedAddressPairs> allowedAddressPairs = neutronObject.getAllowedAddressPairs();
            Assert.assertEquals("NeutronPort JAXB Test 8: Testing allowed_address_pairs list length failed", 1,
                    allowedAddressPairs.size());

            List<NeutronPort_VIFDetail> vifDetails = neutronObject.getVIFDetail();

            Assert.assertEquals("NeutronPort JAXB Test 9.1: Testing vif_details list length failed", 1,
                    vifDetails.size());

            Assert.assertEquals("NeutronPort JAXB Test 9.2: Testing port_filter value failed", true,
                    vifDetails.get(0).portFilter);

            Assert.assertEquals("NeutronNetwork JAXB Test 9.3: Testing ovs_hybrid_plug value failed", true,
                    vifDetails.get(0).ovsHybridPlug);

            Assert.assertEquals("NeutronPort JAXB Test 10: Testing name failed", "net1", neutronObject.getName());

            Assert.assertEquals("NeutronPort JAXB Test 11: Testing admin_state_up failed", true,
                    neutronObject.getAdminStateUp());

            Assert.assertEquals("NeutronPort JAXB Test 12: Testing binding:vif_type failed", "unbound",
                    neutronObject.getBindingvifType());

            Assert.assertEquals("NeutronPort JAXB Test 13: Testing binding:vnic_type failed", "normal",
                    neutronObject.getBindingvnicType());

            Assert.assertEquals("NeutronPort JAXB Test 14: Testing mac_address failed", "fa:16:3e:c9:cb:f0",
                    neutronObject.getMacAddress());

            Assert.assertEquals("NeutronPort JAXB Test 15: Testing device_id failed",
                    "257614cc-e178-4c92-9c61-3b28d40eca44", neutronObject.getDeviceID());

            Assert.assertEquals("NeutronPort JAXB Test 16: Testing device_owner failed", "",
                    neutronObject.getDeviceOwner());

            Assert.assertEquals("NeutronPort JAXB Test 17: Testing binding:host_id failed", "",
                    neutronObject.getBindinghostID());

        } catch (Exception e) {
            Assert.assertFalse("Tests Failed", true);
        }
    }

}
