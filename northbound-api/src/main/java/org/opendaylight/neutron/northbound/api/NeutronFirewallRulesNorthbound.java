/*
 * Copyright (c) 2014, 2015 Red Hat, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.neutron.northbound.api;

import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.enunciate.jaxrs.ResponseCode;
import org.codehaus.enunciate.jaxrs.StatusCodes;
import org.opendaylight.neutron.spi.INeutronFirewallRuleAware;
import org.opendaylight.neutron.spi.INeutronFirewallRuleCRUD;
import org.opendaylight.neutron.spi.NeutronCRUDInterfaces;
import org.opendaylight.neutron.spi.NeutronFirewallRule;

/**
 * Neutron Northbound REST APIs for Firewall Rule.<br>
 * This class provides REST APIs for managing neutron Firewall Rule
 *
 * <br>
 * <br>
 * Authentication scheme : <b>HTTP Basic</b><br>
 * Authentication realm : <b>opendaylight</b><br>
 * Transport : <b>HTTP and HTTPS</b><br>
 * <br>
 * HTTPS Authentication is disabled by default. Administrator can enable it in
 * tomcat-server.xml after adding a proper keystore / SSL certificate from a
 * trusted authority.<br>
 * More info :
 * http://tomcat.apache.org/tomcat-7.0-doc/ssl-howto.html#Configuration
 */

@Path("fw/firewall_rules")
public class NeutronFirewallRulesNorthbound
    extends AbstractNeutronNorthboundIAware<NeutronFirewallRule, NeutronFirewallRuleRequest, INeutronFirewallRuleCRUD, INeutronFirewallRuleAware> {
    private static final String RESOURCE_NAME = "Firewall Rule";

    @Override
    protected String getResourceName() {
        return RESOURCE_NAME;
    }

    @Override
    protected NeutronFirewallRule extractFields(NeutronFirewallRule o, List<String> fields) {
        return o.extractFields(fields);
    }

    @Override
    protected NeutronFirewallRuleRequest newNeutronRequest(NeutronFirewallRule o) {
        return new NeutronFirewallRuleRequest(o);
    }

    @Override
    protected INeutronFirewallRuleCRUD getNeutronCRUD() {
        NeutronCRUDInterfaces answer = new NeutronCRUDInterfaces().fetchINeutronFirewallRuleCRUD(this);
        if (answer.getFirewallRuleInterface() == null) {
            throw new ServiceUnavailableException(serviceUnavailable());
        }
        return answer.getFirewallRuleInterface();
    }

    @Override
    protected Object[] getInstances() {
        return NeutronUtil.getInstances(INeutronFirewallRuleAware.class, this);
    }

    @Override
    protected int canCreate(Object instance, NeutronFirewallRule singleton) {
        INeutronFirewallRuleAware service = (INeutronFirewallRuleAware) instance;
        return service.canCreateNeutronFirewallRule(singleton);
    }

    @Override
    protected void created(Object instance, NeutronFirewallRule singleton) {
        INeutronFirewallRuleAware service = (INeutronFirewallRuleAware) instance;
        service.neutronFirewallRuleCreated(singleton);
    }

    @Override
    protected int canUpdate(Object instance, NeutronFirewallRule delta, NeutronFirewallRule original) {
        INeutronFirewallRuleAware service = (INeutronFirewallRuleAware) instance;
        return service.canUpdateNeutronFirewallRule(delta, original);
    }

    @Override
    protected void updated(Object instance, NeutronFirewallRule updated) {
        INeutronFirewallRuleAware service = (INeutronFirewallRuleAware) instance;
        service.neutronFirewallRuleUpdated(updated);
    }

    @Override
    protected int canDelete(Object instance, NeutronFirewallRule singleton) {
        INeutronFirewallRuleAware service = (INeutronFirewallRuleAware) instance;
        return service.canDeleteNeutronFirewallRule(singleton);
    }

    @Override
    protected void deleted(Object instance, NeutronFirewallRule singleton) {
        INeutronFirewallRuleAware service = (INeutronFirewallRuleAware) instance;
        service.neutronFirewallRuleDeleted(singleton);
    }

    /**
     * Returns a list of all Firewall Rules
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAUTHORIZED, condition = "Unauthorized"),
            @ResponseCode(code = HttpURLConnection.HTTP_NOT_IMPLEMENTED, condition = "Not Implemented"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response listRules(
            // return fields
            @QueryParam("fields") List<String> fields,
            // OpenStack firewall rule attributes
            @QueryParam("id") String queryFirewallRuleUUID,
            @QueryParam("tenant_id") String queryFirewallRuleTenantID,
            @QueryParam("name") String queryFirewallRuleName,
            @QueryParam("description") String queryFirewallRuleDescription,
            @QueryParam("status") String queryFirewallRuleStatus,
            @QueryParam("shared") Boolean queryFirewallRuleIsShared,
            @QueryParam("firewall_policy_id") String queryFirewallRulePolicyID,
            @QueryParam("protocol") String queryFirewallRuleProtocol,
            @QueryParam("ip_version") Integer queryFirewallRuleIpVer,
            @QueryParam("source_ip_address") String queryFirewallRuleSrcIpAddr,
            @QueryParam("destination_ip_address") String queryFirewallRuleDstIpAddr,
            @QueryParam("source_port") Integer queryFirewallRuleSrcPort,
            @QueryParam("destination_port") Integer queryFirewallRuleDstPort,
            @QueryParam("position") Integer queryFirewallRulePosition,
            @QueryParam("action") String queryFirewallRuleAction,
            @QueryParam("enabled") Boolean queryFirewallRuleIsEnabled,
            // pagination
            @QueryParam("limit") String limit,
            @QueryParam("marker") String marker,
            @QueryParam("page_reverse") String pageReverse
            // sorting not supported
    ) {
        INeutronFirewallRuleCRUD firewallRuleInterface = getNeutronCRUD();
        List<NeutronFirewallRule> ans = new ArrayList<NeutronFirewallRule>();
        for (NeutronFirewallRule nsr : firewallRuleInterface.getAllNeutronFirewallRules()) {
            if ((queryFirewallRuleUUID == null ||
                    queryFirewallRuleUUID.equals(nsr.getID())) &&
                    (queryFirewallRuleTenantID == null ||
                            queryFirewallRuleTenantID.equals(nsr.getTenantID())) &&
                    (queryFirewallRuleName == null ||
                            queryFirewallRuleName.equals(nsr.getFirewallRuleName())) &&
                    (queryFirewallRuleDescription == null ||
                            queryFirewallRuleDescription.equals(nsr.getFirewallRuleDescription())) &&
                    (queryFirewallRuleStatus == null ||
                            queryFirewallRuleStatus.equals(nsr.getFirewallRuleStatus())) &&
                    (queryFirewallRuleIsShared == null ||
                            queryFirewallRuleIsShared.equals(nsr.getFirewallRuleIsShared())) &&
                    (queryFirewallRulePolicyID == null ||
                            queryFirewallRulePolicyID.equals(nsr.getFirewallRulePolicyID())) &&
                    (queryFirewallRuleProtocol == null ||
                            queryFirewallRuleProtocol.equals(nsr.getFirewallRuleProtocol())) &&
                    (queryFirewallRuleIpVer == null ||
                            queryFirewallRuleIpVer.equals(nsr.getFirewallRuleIpVer())) &&
                    (queryFirewallRuleSrcIpAddr == null ||
                            queryFirewallRuleSrcIpAddr.equals(nsr.getFirewallRuleSrcIpAddr())) &&
                    (queryFirewallRuleDstIpAddr == null ||
                            queryFirewallRuleDstIpAddr.equals(nsr.getFirewallRuleDstIpAddr())) &&
                    (queryFirewallRuleSrcPort == null ||
                            queryFirewallRuleSrcPort.equals(nsr.getFirewallRuleSrcPort())) &&
                    (queryFirewallRuleDstPort == null ||
                            queryFirewallRuleDstPort.equals(nsr.getFirewallRuleDstPort())) &&
                    (queryFirewallRulePosition == null ||
                            queryFirewallRulePosition.equals(nsr.getFirewallRulePosition())) &&
                    (queryFirewallRuleAction == null ||
                            queryFirewallRuleAction.equals(nsr.getFirewallRuleAction())) &&
                    (queryFirewallRuleIsEnabled == null ||
                            queryFirewallRuleIsEnabled.equals(nsr.getFirewallRuleIsEnabled()))) {
                if (fields.size() > 0) {
                    ans.add(extractFields(nsr, fields));
                } else {
                    ans.add(nsr);
                }
            }
        }
        //TODO: apply pagination to results
        return Response.status(HttpURLConnection.HTTP_OK).entity(
                new NeutronFirewallRuleRequest(ans)).build();
    }

    /**
     * Returns a specific Firewall Rule
     */

    @Path("{firewallRuleUUID}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAUTHORIZED, condition = "Unauthorized"),
            @ResponseCode(code = HttpURLConnection.HTTP_NOT_FOUND, condition = "Not Found"),
            @ResponseCode(code = HttpURLConnection.HTTP_NOT_IMPLEMENTED, condition = "Not Implemented"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response showFirewallRule(@PathParam("firewallRuleUUID") String firewallRuleUUID,
            // return fields
            @QueryParam("fields") List<String> fields) {
        return show(firewallRuleUUID, fields);
    }

    /**
     * Creates new Firewall Rule
     */

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_CREATED, condition = "Created"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response createFirewallRules(final NeutronFirewallRuleRequest input) {
        return create(input);
    }

    /**
     * Updates a Firewall Rule
     */
    @Path("{firewallRuleUUID}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
            @ResponseCode(code = HttpURLConnection.HTTP_NOT_FOUND, condition = "Not Found"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response updateFirewallRule(
            @PathParam("firewallRuleUUID") String firewallRuleUUID, final NeutronFirewallRuleRequest input) {
        return update(firewallRuleUUID, input);
    }

    /**
     * Deletes a Firewall Rule
     */

    @Path("{firewallRuleUUID}")
    @DELETE
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_NO_CONTENT, condition = "No Content"),
            @ResponseCode(code = HttpURLConnection.HTTP_NOT_FOUND, condition = "Not Found"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response deleteFirewallRule(
            @PathParam("firewallRuleUUID") String firewallRuleUUID) {
        return delete(firewallRuleUUID);
    }
}
