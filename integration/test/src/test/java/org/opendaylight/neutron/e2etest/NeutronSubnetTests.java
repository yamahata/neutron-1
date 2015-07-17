/*
 * Copyright (C) 2015 IBM, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.neutron.e2etest;

import java.io.OutputStreamWriter;

import java.lang.Thread;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;

public class NeutronSubnetTests {
    String base;

    public NeutronSubnetTests(String base) {
        this.base = base;
    }

    public void subnet_collection_get_test() {
        String url = base + "/subnets";
        ITNeutronE2E.test_fetch(url, "Subnet Collection GET failed");
    }

    //TODO handle SB check
    public void singleton_subnet_create_test() {
        String url = base + "/subnets";
        String content = " { \"subnet\": { "+
            "\"name\": \"\", "+
            "\"enable_dhcp\": true, "+
            "\"network_id\": \"4e8e5957-649f-477b-9e5b-f1f75b21c03c\", "+
            "\"tenant_id\": \"9bacb3c5d39d41a79512987f338cf177\", "+
            "\"dns_nameservers\": [], "+
            "\"allocation_pools\": [ { "+
                "\"start\": \"10.0.0.2\", "+
                "\"end\": \"10.0.0.254\" } ], "+
            "\"host_routes\":[ { \"destination\":\"0.0.0.0/0\", " +
               " \"nexthop\":\"123.456.78.9\" }, " +
            " { \"destination\":\"192.168.0.0/24\", " +
               " \"nexthop\":\"192.168.0.1\" } ], " +
            "\"ip_version\": 4, "+
            "\"gateway_ip\": \"10.0.0.1\", "+
            "\"cidr\": \"10.0.0.0/24\", "+
            "\"id\": \"3b80198d-4f7b-4f77-9ef5-774d54e17126\" } } ";
        ITNeutronE2E.test_create(url, content, "Singleton Subnet Post Failed NB");
    }

    //TODO handle SB check
    public void external_subnet_create_test() {
        String url = base + "/subnets";
        String content = " { \"subnet\": { "+
            "\"name\": \"\", "+
            "\"enable_dhcp\": true, "+
            "\"network_id\": \"8ca37218-28ff-41cb-9b10-039601ea7e6b\", "+
            "\"tenant_id\": \"9bacb3c5d39d41a79512987f338cf177\", "+
            "\"dns_nameservers\": [], "+
            "\"allocation_pools\": [ { "+
                "\"start\": \"10.1.0.2\", "+
                "\"end\": \"10.1.0.254\" } ], "+
            "\"host_routes\": [], "+
            "\"ip_version\": 4, "+
            "\"gateway_ip\": \"10.1.0.1\", "+
            "\"cidr\": \"10.1.0.0/24\", "+
            "\"id\": \"f13b537f-1268-455f-b5fa-1e6817a9c204\" } } ";
        ITNeutronE2E.test_create(url, content, "External Subnet Post Failed NB");
    }

    public void bulk_subnet_create_test() {
        String url = base + "/subnets";
        String content = " { \"subnets\": [ "
            + " { \"allocation_pools\": [ "
            + " { \"end\": \"192.168.199.254\", \"start\": \"192.168.199.2\" } ], "
            + " \"cidr\": \"192.168.199.0/24\", "
            + " \"dns_nameservers\": [], "
            + " \"enable_dhcp\": true, "
            + " \"gateway_ip\": \"192.168.199.1\", "
            + " \"host_routes\": [], "
            + " \"id\": \"0468a7a7-290d-4127-aedd-6c9449775a24\", "
            + " \"ip_version\": 4, "
            + " \"name\": \"\", "
            + " \"network_id\": \"af374017-c9ae-4a1d-b799-ab73111476e2\", "
            + " \"tenant_id\": \"4fd44f30292945e481c7b8a0c8908869\" }, { "
            + " \"allocation_pools\": [ { \"end\": \"10.56.7.254\", \"start\": \"10.56.4.2\" } ], "
            + " \"cidr\": \"10.56.4.0/22\", "
            + " \"dns_nameservers\": [], "
            + " \"enable_dhcp\": true, "
            + " \"gateway_ip\": \"10.56.4.1\", "
            + " \"host_routes\": [], "
            + " \"id\": \"b0e7435c-1512-45fb-aa9e-9a7c5932fb30\", "
            + " \"ip_version\": 4, "
            + " \"name\": \"\", "
            + " \"network_id\": \"af374017-c9ae-4a1d-b799-ab73111476e2\", "
            + " \"tenant_id\": \"4fd44f30292945e481c7b8a0c8908869\" } ] }";
        ITNeutronE2E.test_create(url, content, "Bulk Subnet Post Failed");
    }

    public void subnet_update_test() {
        String url = base + "/subnets/b0e7435c-1512-45fb-aa9e-9a7c5932fb30";
        String content = " { \"subnet\": { "
            + " \"name\": \"my_subnet\", "
            + " \"enable_dhcp\": true, "
            + " \"network_id\": \"af374017-c9ae-4a1d-b799-ab73111476e2\", "
            + " \"tenant_id\": \"4fd44f30292945e481c7b8a0c8908869\", "
            + " \"dns_nameservers\": [], "
            + " \"allocation_pools\": [ { \"start\": \"10.0.0.2\", \"end\": \"10.0.0.254\" } ], "
            + " \"host_routes\": [], "
            + " \"ip_version\": 4, "
            + " \"gateway_ip\": \"10.0.0.1\", "
            + " \"cidr\": \"10.0.0.0/24\", "
            + " \"id\": \"b0e7435c-1512-45fb-aa9e-9a7c5932fb30\" } }";
        ITNeutronE2E.test_modify(url, content,"Subnet Put Failed");
    }

    public void subnet_element_get_test() {
        String url = base + "/subnets/b0e7435c-1512-45fb-aa9e-9a7c5932fb30";
        ITNeutronE2E.test_fetch(url, true, "Subnet Element Get Failed");
    }

    public void subnet_delete_test() {
        String url = base + "/subnets/b0e7435c-1512-45fb-aa9e-9a7c5932fb30";
        ITNeutronE2E.test_delete(url, "Subnet Element Delete Failed");
    }

    public void subnet_element_negative_get_test() {
        String url = base + "/subnets/b0e7435c-1512-45fb-aa9e-9a7c5932fb30";
        ITNeutronE2E.test_fetch(url, false, "Subnet Element Get Failed");
    }
}
