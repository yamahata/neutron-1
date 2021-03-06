/*
 * Copyright (c) 2013, 2015 IBM Corporation and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.neutron.northbound.api;

import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.enunciate.jaxrs.ResponseCode;
import org.codehaus.enunciate.jaxrs.StatusCodes;
import org.codehaus.enunciate.jaxrs.TypeHint;
import org.opendaylight.neutron.spi.INeutronNetworkAware;
import org.opendaylight.neutron.spi.INeutronNetworkCRUD;
import org.opendaylight.neutron.spi.NeutronCRUDInterfaces;
import org.opendaylight.neutron.spi.NeutronNetwork;

/**
 * Neutron Northbound REST APIs for Network.<br>
 * This class provides REST APIs for managing neutron Networks
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
 *
 */

@Path("/networks")
public class NeutronNetworksNorthbound
    extends AbstractNeutronNorthboundIAware<NeutronNetwork, NeutronNetworkRequest, INeutronNetworkCRUD, INeutronNetworkAware> {

    @Context
    UriInfo uriInfo;

    private static final String RESOURCE_NAME = "Network";

    @Override
    protected String getResourceName() {
        return RESOURCE_NAME;
    }

    @Override
    protected NeutronNetwork extractFields(NeutronNetwork o, List<String> fields) {
        return o.extractFields(fields);
    }

    @Override
    protected NeutronNetworkRequest newNeutronRequest(NeutronNetwork o) {
        return new NeutronNetworkRequest(o);
    }

    @Override
    protected INeutronNetworkCRUD getNeutronCRUD() {
        NeutronCRUDInterfaces answer = new NeutronCRUDInterfaces().fetchINeutronNetworkCRUD(this);
        if (answer.getNetworkInterface() == null) {
            throw new ServiceUnavailableException(serviceUnavailable());
        }
        return answer.getNetworkInterface();
    }

    @Override
    protected Object[] getInstances() {
        return NeutronUtil.getInstances(INeutronNetworkAware.class, this);
    }

    @Override
    protected int canCreate(Object instance, NeutronNetwork singleton) {
        INeutronNetworkAware service = (INeutronNetworkAware) instance;
        return service.canCreateNetwork(singleton);
    }

    @Override
    protected void created(Object instance, NeutronNetwork singleton) {
        INeutronNetworkAware service = (INeutronNetworkAware) instance;
        service.neutronNetworkCreated(singleton);
    }

    @Override
    protected int canUpdate(Object instance, NeutronNetwork delta, NeutronNetwork original) {
        INeutronNetworkAware service = (INeutronNetworkAware) instance;
        return service.canUpdateNetwork(delta, original);
    }

    @Override
    protected void updated(Object instance, NeutronNetwork updated) {
        INeutronNetworkAware service = (INeutronNetworkAware) instance;
        service.neutronNetworkUpdated(updated);
    }

    @Override
    protected int canDelete(Object instance, NeutronNetwork singleton) {
        INeutronNetworkAware service = (INeutronNetworkAware) instance;
        return service.canDeleteNetwork(singleton);
    }

    @Override
    protected void deleted(Object instance, NeutronNetwork singleton) {
        INeutronNetworkAware service = (INeutronNetworkAware) instance;
        service.neutronNetworkDeleted(singleton);
    }

    /**
     * Returns a list of all Networks */

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    //@TypeHint(OpenStackNetworks.class)
    @StatusCodes({
        @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
        @ResponseCode(code = HttpURLConnection.HTTP_UNAUTHORIZED, condition = "Unauthorized"),
        @ResponseCode(code = HttpURLConnection.HTTP_NOT_IMPLEMENTED, condition = "Not Implemented"),
        @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response listNetworks(
            // return fields
            @QueryParam("fields") List<String> fields,
            // note: openstack isn't clear about filtering on lists, so we aren't handling them
            @QueryParam("id") String queryID,
            @QueryParam("name") String queryName,
            @QueryParam("admin_state_up") String queryAdminStateUp,
            @QueryParam("status") String queryStatus,
            @QueryParam("shared") String queryShared,
            @QueryParam("tenant_id") String queryTenantID,
            @QueryParam("router_external") String queryRouterExternal,
            @QueryParam("provider_network_type") String queryProviderNetworkType,
            @QueryParam("provider_physical_network") String queryProviderPhysicalNetwork,
            @QueryParam("provider_segmentation_id") String queryProviderSegmentationID,
            // linkTitle
            @QueryParam("limit") Integer limit,
            @QueryParam("marker") String marker,
            @DefaultValue("false") @QueryParam("page_reverse") Boolean pageReverse
            // sorting not supported
            ) {
        INeutronNetworkCRUD networkInterface = getNeutronCRUD();
        List<NeutronNetwork> allNetworks = networkInterface.getAllNetworks();
        List<NeutronNetwork> ans = new ArrayList<NeutronNetwork>();
        Iterator<NeutronNetwork> i = allNetworks.iterator();
        while (i.hasNext()) {
            NeutronNetwork oSN = i.next();
            //match filters: TODO provider extension
            Boolean bAdminStateUp = null;
            Boolean bShared = null;
            Boolean bRouterExternal = null;
            if (queryAdminStateUp != null) {
                bAdminStateUp = Boolean.valueOf(queryAdminStateUp);
            }
            if (queryShared != null) {
                bShared = Boolean.valueOf(queryShared);
            }
            if (queryRouterExternal != null) {
                bRouterExternal = Boolean.valueOf(queryRouterExternal);
            }
            if ((queryID == null || queryID.equals(oSN.getID())) &&
                    (queryName == null || queryName.equals(oSN.getNetworkName())) &&
                    (bAdminStateUp == null || bAdminStateUp.booleanValue() == oSN.isAdminStateUp()) &&
                    (queryStatus == null || queryStatus.equals(oSN.getStatus())) &&
                    (bShared == null || bShared.booleanValue() == oSN.isShared()) &&
                    (bRouterExternal == null || bRouterExternal.booleanValue() == oSN.isRouterExternal()) &&
                    (queryTenantID == null || queryTenantID.equals(oSN.getTenantID()))) {
                if (fields.size() > 0) {
                    ans.add(extractFields(oSN,fields));
                } else {
                    ans.add(oSN);
                }
            }
        }

        if (limit != null && ans.size() > 1) {
            // Return a paginated request
            NeutronNetworkRequest request = (NeutronNetworkRequest) PaginatedRequestFactory.createRequest(limit,
                    marker, pageReverse, uriInfo, ans, NeutronNetwork.class);
            return Response.status(HttpURLConnection.HTTP_OK).entity(request).build();
        }

    return Response.status(HttpURLConnection.HTTP_OK).entity(new NeutronNetworkRequest(ans)).build();

    }

    /**
     * Returns a specific Network */

    @Path("{netUUID}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    //@TypeHint(OpenStackNetworks.class)
    @StatusCodes({
        @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
        @ResponseCode(code = HttpURLConnection.HTTP_UNAUTHORIZED, condition = "Unauthorized"),
        @ResponseCode(code = HttpURLConnection.HTTP_NOT_FOUND, condition = "Not Found"),
        @ResponseCode(code = HttpURLConnection.HTTP_NOT_IMPLEMENTED, condition = "Not Implemented"),
        @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response showNetwork(
            @PathParam("netUUID") String netUUID,
            // return fields
            @QueryParam("fields") List<String> fields
            ) {
        return show(netUUID, fields);
    }

    /**
     * Creates new Networks */
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    @TypeHint(NeutronNetwork.class)
    @StatusCodes({
        @ResponseCode(code = HttpURLConnection.HTTP_CREATED, condition = "Created"),
        @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response createNetworks(final NeutronNetworkRequest input) {
        return create(input);
    }

    @Override
    protected void updateDelta(String uuid, NeutronNetwork delta, NeutronNetwork original) {
        /*
         *  note: what we get appears to not be a delta but
         * rather an incomplete updated object.  So we need to set
         * the ID to complete the object and then send that down
         * for folks to check
         */

        delta.setID(uuid);
        delta.setTenantID(original.getTenantID());
    }

    /**
     * Updates a Network */
    @Path("{netUUID}")
    @PUT
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    //@TypeHint(OpenStackNetworks.class)
    @StatusCodes({
        @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
        @ResponseCode(code = HttpURLConnection.HTTP_NOT_FOUND, condition = "Not Found"),
        @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response updateNetwork(
            @PathParam("netUUID") String netUUID, final NeutronNetworkRequest input
            ) {
        return update(netUUID, input);
    }

    /**
     * Deletes a Network */

    @Path("{netUUID}")
    @DELETE
    @StatusCodes({
        @ResponseCode(code = HttpURLConnection.HTTP_NO_CONTENT, condition = "No Content"),
        @ResponseCode(code = HttpURLConnection.HTTP_NOT_FOUND, condition = "Not Found"),
        @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response deleteNetwork(
            @PathParam("netUUID") String netUUID) {
        return delete(netUUID);
    }
}
