/*
 * Copyright (C) 2014 Red Hat, Inc.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.neutron.spi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * OpenStack Neutron v2.0 Security Group bindings.
 * See OpenStack Network API v2.0 Reference for description of
 * annotated attributes. The current fields are as follows:
 * <p>
 * id                   uuid-str unique ID for the security group.
 * name                 String name of the security group.
 * description          String name of the security group.
 * tenant_id            uuid-str Owner of security rule..
 * security_group_rules List&lt;NeutronSecurityRule&gt; nested RO in the sec group.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)

public class NeutronSecurityGroup extends NeutronObject implements Serializable, INeutronObject {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "name")
    String securityGroupName;

    @XmlElement(name = "description")
    String securityGroupDescription;

    @XmlElement(name = "security_group_rules")
    List<NeutronSecurityRule> neutronSecurityRule;

    public NeutronSecurityGroup() {
        neutronSecurityRule = new ArrayList<NeutronSecurityRule>();

    }

    // @deprecated use getID()
    public String getSecurityGroupUUID() {
        return getID();
    }

    // @deprecated use setID()
    public void setSecurityGroupUUID(String uuid) {
        setID(uuid);
    }

    public String getSecurityGroupName() {
        return securityGroupName;
    }

    public void setSecurityGroupName(String securityGroupName) {
        this.securityGroupName = securityGroupName;
    }

    public String getSecurityGroupDescription() {
        return securityGroupDescription;
    }

    public void setSecurityGroupDescription(String securityGroupDescription) {
        this.securityGroupDescription = securityGroupDescription;
    }

    @Deprecated
    public String getSecurityGroupTenantID() {
        return getTenantID();
    }

    @Deprecated
    public void setSecurityGroupTenantID(String tenantID) {
        setTenantID(tenantID);
    }

    // Rules In Group
    public List<NeutronSecurityRule> getSecurityRules() {
        return neutronSecurityRule;
    }

    public void setSecurityRules(List<NeutronSecurityRule> neutronSecurityRule) {
        this.neutronSecurityRule = neutronSecurityRule;
    }

    public NeutronSecurityGroup extractFields(List<String> fields) {
        NeutronSecurityGroup ans = new NeutronSecurityGroup ();
        Iterator<String> i = fields.iterator ();
        while (i.hasNext ()) {
            String s = i.next ();
            if (s.equals ("id")) {
                ans.setID (this.getID ());
            }
            if (s.equals ("name")) {
                ans.setSecurityGroupName (this.getSecurityGroupName ());
            }
            if (s.equals ("description")) {
                ans.setSecurityGroupDescription (this.getSecurityGroupDescription ());
            }
            if (s.equals ("tenant_id")) {
                ans.setTenantID (this.getTenantID ());
            }
            if (s.equals ("security_group_rules")) {
                ans.setSecurityRules (this.getSecurityRules ());
            }
        }
        return ans;
    }

    @Override
    public String toString() {
        return "NeutronSecurityGroup{" +
                "securityGroupUUID='" + uuid + '\'' +
                ", securityGroupName='" + securityGroupName + '\'' +
                ", securityGroupDescription='" + securityGroupDescription + '\'' +
                ", securityGroupTenantID='" + tenantID + '\'' +
                ", securityRules=" + neutronSecurityRule + "]";
    }

    @Override
    public void initDefaults() {
        //TODO verify no defaults values are nessecary required.
    }
}
