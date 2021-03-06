/*
 * Copyright (c) 2013, 2015 IBM Corporation and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.neutron.spi;

/**
 * This interface defines the methods a service that wishes to be aware of Neutron Networks needs to implement
 *
 */
@Deprecated
public interface INeutronNetworkAware {

    /**
     * Services provide this interface method to indicate if the specified network can be created
     *
     * @param network
     *            instance of proposed new Neutron Network object
     * @return integer
     *            the return value is understood to be a HTTP status code.  A return value outside of 200 through 299
     *            results in the create operation being interrupted and the returned status value reflected in the
     *            HTTP response.
     */
    int canCreateNetwork(NeutronNetwork network);

    /**
     * Services provide this interface method for taking action after a network has been created
     *
     * @param network
     *            instance of new Neutron Network object
     */
    void neutronNetworkCreated(NeutronNetwork network);

    /**
     * Services provide this interface method to indicate if the specified network can be changed using the specified
     * delta
     *
     * @param delta
     *            updates to the network object using patch semantics
     * @param original
     *            instance of the Neutron Network object to be updated
     * @return integer
     *            the return value is understood to be a HTTP status code.  A return value outside of 200 through 299
     *            results in the update operation being interrupted and the returned status value reflected in the
     *            HTTP response.
     */
    int canUpdateNetwork(NeutronNetwork delta, NeutronNetwork original);

    /**
     * Services provide this interface method for taking action after a network has been updated
     *
     * @param network
     *            instance of modified Neutron Network object
     */
    void neutronNetworkUpdated(NeutronNetwork network);

    /**
     * Services provide this interface method to indicate if the specified network can be deleted
     *
     * @param network
     *            instance of the Neutron Network object to be deleted
     * @return integer
     *            the return value is understood to be a HTTP status code.  A return value outside of 200 through 299
     *            results in the delete operation being interrupted and the returned status value reflected in the
     *            HTTP response.
     */
    int canDeleteNetwork(NeutronNetwork network);

    /**
     * Services provide this interface method for taking action after a network has been deleted
     *
     * @param network
     *            instance of deleted Neutron Network object
     */
    void neutronNetworkDeleted(NeutronNetwork network);
}
