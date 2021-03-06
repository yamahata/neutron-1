/*
 * Copyright (c) 2013, 2015 IBM Corporation and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.neutron.spi;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)

public class NeutronFloatingIP extends NeutronObject implements Serializable, INeutronObject {
    private static final long serialVersionUID = 1L;

    // See OpenStack Network API v2.0 Reference for description of
    // annotated attributes

    @XmlElement (name = "floating_network_id")
    String floatingNetworkUUID;

    @XmlElement (name = "port_id")
    String portUUID;

    @XmlElement (name = "fixed_ip_address")
    String fixedIPAddress;

    @XmlElement (name = "floating_ip_address")
    String floatingIPAddress;

    @XmlElement (name="router_id")
    String routerUUID;

    @XmlElement (name="status")
    String status;

    public NeutronFloatingIP() {
    }

    // @deprecated use getID()
    public String getFloatingIPUUID() {
        return getID();
    }

    // @deprecated use setID()
    public void setFloatingIPUUID(String uuid) {
        setID(uuid);
    }

    public String getFloatingNetworkUUID() {
        return floatingNetworkUUID;
    }

    public void setFloatingNetworkUUID(String floatingNetworkUUID) {
        this.floatingNetworkUUID = floatingNetworkUUID;
    }

    public String getPortUUID() {
        return portUUID;
    }

    public String getRouterUUID() {
        return routerUUID;
    }

    public void setPortUUID(String portUUID) {
        this.portUUID = portUUID;
    }

    public String getFixedIPAddress() {
        return fixedIPAddress;
    }

    public void setFixedIPAddress(String fixedIPAddress) {
        this.fixedIPAddress = fixedIPAddress;
    }

    public String getFloatingIPAddress() {
        return floatingIPAddress;
    }

    public void setFloatingIPAddress(String floatingIPAddress) {
        this.floatingIPAddress = floatingIPAddress;
    }

    @Deprecated
    public String getTenantUUID() {
        return getTenantID();
    }

    @Deprecated
    public void setTenantUUID(String tenantID) {
        setTenantID(tenantID);
    }

    public void setRouterUUID(String routerUUID) {
        this.routerUUID = routerUUID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method copies selected fields from the object and returns them
     * as a new object, suitable for marshaling.
     *
     * @param fields
     *            List of attributes to be extracted
     * @return an OpenStackFloatingIPs object with only the selected fields
     * populated
     */

    public NeutronFloatingIP extractFields(List<String> fields) {
        NeutronFloatingIP ans = new NeutronFloatingIP();
        Iterator<String> i = fields.iterator();
        while (i.hasNext()) {
            String s = i.next();
            if (s.equals("id")) {
                ans.setID(this.getID());
            }
            if (s.equals("floating_network_id")) {
                ans.setFloatingNetworkUUID(this.getFloatingNetworkUUID());
            }
            if (s.equals("port_id")) {
                ans.setPortUUID(this.getPortUUID());
            }
            if (s.equals("fixed_ip_address")) {
                ans.setFixedIPAddress(this.getFixedIPAddress());
            }
            if (s.equals("floating_ip_address")) {
                ans.setFloatingIPAddress(this.getFloatingIPAddress());
            }
            if (s.equals("tenant_id")) {
                ans.setTenantID(this.getTenantID());
            }
            if (s.equals("router_id")) {
                ans.setRouterUUID(this.getRouterUUID());
            }
            if (s.equals("status")) {
                ans.setStatus(this.getStatus());
            }
        }
        return ans;
    }

    @Override
    public String toString() {
        return "NeutronFloatingIP{" +
            "fipUUID='" + uuid + '\'' +
            ", fipFloatingNetworkId='" + floatingNetworkUUID + '\'' +
            ", fipPortUUID='" + portUUID + '\'' +
            ", fipFixedIPAddress='" + fixedIPAddress + '\'' +
            ", fipFloatingIPAddress=" + floatingIPAddress +
            ", fipTenantId='" + tenantID + '\'' +
            ", fipRouterId='" + routerUUID + '\'' +
            ", fipStatus='" + status + '\'' +
            '}';
    }
}
