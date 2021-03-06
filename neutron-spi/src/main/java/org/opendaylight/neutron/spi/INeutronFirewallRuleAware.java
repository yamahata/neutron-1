/*
 * Copyright (C) 2014 Red Hat, Inc.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.neutron.spi;

/**
 * This interface defines the methods a service that wishes to be aware of Firewall Rules needs to implement
 *
 */
@Deprecated
public interface INeutronFirewallRuleAware {

    /**
     * Services provide this interface method to indicate if the specified firewallRule can be created
     *
     * @param firewallRule
     *            instance of proposed new Firewall Rule object
     * @return integer
     *            the return value is understood to be a HTTP status code.  A return value outside of 200 through 299
     *            results in the create operation being interrupted and the returned status value reflected in the
     *            HTTP response.
     */
    int canCreateNeutronFirewallRule(NeutronFirewallRule firewallRule);

    /**
     * Services provide this interface method for taking action after a firewallRule has been created
     *
     * @param firewallRule
     *            instance of new Firewall Rule object
     */
    void neutronFirewallRuleCreated(NeutronFirewallRule firewallRule);

    /**
     * Services provide this interface method to indicate if the specified firewallRule can be changed using the specified
     * delta
     *
     * @param delta
     *            updates to the firewallRule object using patch semantics
     * @param original
     *            instance of the Firewall Rule object to be updated
     * @return integer
     *            the return value is understood to be a HTTP status code.  A return value outside of 200 through 299
     *            results in the update operation being interrupted and the returned status value reflected in the
     *            HTTP response.
     */
    int canUpdateNeutronFirewallRule(NeutronFirewallRule delta, NeutronFirewallRule original);

    /**
     * Services provide this interface method for taking action after a firewallRule has been updated
     *
     * @param firewallRule
     *            instance of modified Firewall Rule object
     */
    void neutronFirewallRuleUpdated(NeutronFirewallRule firewallRule);

    /**
     * Services provide this interface method to indicate if the specified firewallRule can be deleted
     *
     * @param firewallRule
     *            instance of the Firewall Rule object to be deleted
     * @return integer
     *            the return value is understood to be a HTTP status code.  A return value outside of 200 through 299
     *            results in the delete operation being interrupted and the returned status value reflected in the
     *            HTTP response.
     */
    int canDeleteNeutronFirewallRule(NeutronFirewallRule firewallRule);

    /**
     * Services provide this interface method for taking action after a firewallRule has been deleted
     *
     * @param firewallRule
     *            instance of deleted Firewall Rule object
     */
    void neutronFirewallRuleDeleted(NeutronFirewallRule firewallRule);
}
