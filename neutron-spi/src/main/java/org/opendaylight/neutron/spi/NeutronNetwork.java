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

@XmlRootElement(name = "network")
@XmlAccessorType(XmlAccessType.NONE)

public class NeutronNetwork extends NeutronObject implements Serializable, INeutronObject {
    // See OpenStack Network API v2.0 Reference for description of
    // annotated attributes

    private static final long serialVersionUID = 1L;

    @XmlElement (name = "name")
    String networkName;

    @XmlElement (defaultValue = "true", name = "admin_state_up")
    Boolean adminStateUp;

    @XmlElement (defaultValue = "false", name = "shared")
    Boolean shared;

    //    @XmlElement (defaultValue = "false", name = "router:external")
    @XmlElement (defaultValue="false", namespace="router", name="external")
    Boolean routerExternal;

    //    @XmlElement (defaultValue = "flat", name = "provider:network_type")
    @XmlElement (namespace="provider", name="network_type")
    String providerNetworkType;

    //    @XmlElement (name = "provider:physical_network")
    @XmlElement (namespace="provider", name="physical_network")
    String providerPhysicalNetwork;

    //    @XmlElement (name = "provider:segmentation_id")
    @XmlElement (namespace="provider", name="segmentation_id")
    String providerSegmentationID;

    @XmlElement (name = "status")
    String status;

    @XmlElement (name="segments")
    List<NeutronNetwork_Segment> segments;

    @XmlElement (name="vlan_transparent")
    Boolean vlanTransparent;

    @XmlElement (name="mtu")
    Integer mtu;

    /* This attribute lists the ports associated with an instance
     * which is needed for determining if that instance can be deleted
     */

    public NeutronNetwork() {
    }

    @Override
    public void initDefaults() {
        if (status == null) {
            status = "ACTIVE";
        }
        if (adminStateUp == null) {
            adminStateUp = true;
        }
        if (shared == null) {
            shared = false;
        }
        if (routerExternal == null) {
            routerExternal = false;
        }
        if (providerNetworkType == null) {
            providerNetworkType = "flat";
        }
    }

    @Deprecated
    public String getNetworkUUID() {
        return getID();
    }

    @Deprecated
    public void setNetworkUUID(String uuid) {
        setID(uuid);
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public boolean isAdminStateUp() {
        return adminStateUp;
    }

    public Boolean getAdminStateUp() { return adminStateUp; }

    public void setAdminStateUp(boolean newValue) {
        adminStateUp = newValue;
    }

    public boolean isShared() { return shared; }

    public Boolean getShared() { return shared; }

    public void setShared(boolean newValue) {
        shared = newValue;
    }

    public boolean isRouterExternal() { return routerExternal; }

    public Boolean getRouterExternal() { return routerExternal; }

    public void setRouterExternal(boolean newValue) {
        routerExternal = newValue;
    }

    public String getProviderNetworkType() {
        return providerNetworkType;
    }

    public void setProviderNetworkType(String providerNetworkType) {
        this.providerNetworkType = providerNetworkType;
    }

    public String getProviderPhysicalNetwork() {
        return providerPhysicalNetwork;
    }

    public void setProviderPhysicalNetwork(String providerPhysicalNetwork) {
        this.providerPhysicalNetwork = providerPhysicalNetwork;
    }

    public String getProviderSegmentationID() {
        return providerSegmentationID;
    }

    public void setProviderSegmentationID(String providerSegmentationID) {
        this.providerSegmentationID = providerSegmentationID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSegments(List<NeutronNetwork_Segment> segments) {
        this.segments = segments;
    }

    public List<NeutronNetwork_Segment> getSegments() {
        return segments;
    }

    public Boolean getVlanTransparent() {
        return vlanTransparent;
    }

    public void setVlanTransparent(Boolean input) {
        this.vlanTransparent = input;
    }

    public Integer getMtu() {
        return mtu;
    }

    public void setMtu(Integer input) {
        mtu = input;
    }

    /**
     * This method copies selected fields from the object and returns them
     * as a new object, suitable for marshaling.
     *
     * @param fields
     *            List of attributes to be extracted
     * @return an OpenStackNetworks object with only the selected fields
     * populated
     */

    public NeutronNetwork extractFields(List<String> fields) {
        NeutronNetwork ans = new NeutronNetwork();
        Iterator<String> i = fields.iterator();
        while (i.hasNext()) {
            String s = i.next();
            if (s.equals("id")) {
                ans.setID(this.getID());
            }
            if (s.equals("name")) {
                ans.setNetworkName(this.getNetworkName());
            }
            if (s.equals("admin_state_up")) {
                ans.setAdminStateUp(adminStateUp);
            }
            if (s.equals("status")) {
                ans.setStatus(this.getStatus());
            }
            if (s.equals("shared")) {
                ans.setShared(shared);
            }
            if (s.equals("tenant_id")) {
                ans.setTenantID(this.getTenantID());
            }
            if (s.equals("external")) {
                ans.setRouterExternal(this.getRouterExternal());
            }
            if (s.equals("segmentation_id")) {
                ans.setProviderSegmentationID(this.getProviderSegmentationID());
            }
            if (s.equals("physical_network")) {
                ans.setProviderPhysicalNetwork(this.getProviderPhysicalNetwork());
            }
            if (s.equals("network_type")) {
                ans.setProviderNetworkType(this.getProviderNetworkType());
            }
        }
        return ans;
    }

    @Override
    public String toString() {
        return "NeutronNetwork [networkUUID=" + uuid + ", networkName=" + networkName + ", adminStateUp="
                + adminStateUp + ", shared=" + shared + ", tenantID=" + tenantID + ", routerExternal=" + routerExternal
                + ", providerNetworkType=" + providerNetworkType + ", providerPhysicalNetwork="
                + providerPhysicalNetwork + ", providerSegmentationID=" + providerSegmentationID + ", status=" + status
                + ", segments = " + segments + "]";
    }
}

