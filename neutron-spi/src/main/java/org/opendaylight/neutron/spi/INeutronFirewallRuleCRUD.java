/*
 * Copyright (C) 2014 Red Hat, Inc.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.neutron.spi;

import java.util.List;

/**
 * This interface defines the methods for CRUD of NB OpenStack Firewall Rule objects
 *
 */

public interface INeutronFirewallRuleCRUD
    extends INeutronCRUD<NeutronFirewallRule> {
    /**
     * Applications call this interface method to determine if a particular
     *FirewallRule object exists
     *
     * @param uuid
     *            UUID of the Firewall Rule object
     * @return boolean
     */

    boolean neutronFirewallRuleExists(String uuid);

    /**
     * Applications call this interface method to return if a particular
     * FirewallRule object exists
     *
     * @param uuid
     *            UUID of the Firewall Rule object
     * @return {@link NeutronFirewallRule}
     *          OpenStackFirewall Rule class
     */

    NeutronFirewallRule getNeutronFirewallRule(String uuid);

    /**
     * Applications call this interface method to return all Firewall Rule objects
     *
     * @return List of OpenStackNetworks objects
     */

    List<NeutronFirewallRule> getAllNeutronFirewallRules();

    /**
     * Applications call this interface method to add a Firewall Rule object to the
     * concurrent map
     *
     * @param input
     *            OpenStackNetwork object
     * @return boolean on whether the object was added or not
     */

    boolean addNeutronFirewallRule(NeutronFirewallRule input);

    /**
     * Applications call this interface method to remove a Neutron FirewallRule object to the
     * concurrent map
     *
     * @param uuid
     *            identifier for the Firewall Rule object
     * @return boolean on whether the object was removed or not
     */

    boolean removeNeutronFirewallRule(String uuid);

    /**
     * Applications call this interface method to edit a FirewallRule object
     *
     * @param uuid
     *            identifier of the Firewall Rule object
     * @param delta
     *            OpenStackFirewallRule object containing changes to apply
     * @return boolean on whether the object was updated or not
     */

    boolean updateNeutronFirewallRule(String uuid, NeutronFirewallRule delta);

    /**
     * Applications call this interface method to see if a MAC address is in use
     *
     * @param uuid
     *            identifier of the Firewall Rule object
     * @return boolean on whether the macAddress is already associated with a
     * port or not
     */

    boolean neutronFirewallRuleInUse(String uuid);

}
