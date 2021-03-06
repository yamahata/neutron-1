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
import org.opendaylight.neutron.spi.INeutronRouterAware;
import org.opendaylight.neutron.spi.INeutronRouterCRUD;
import org.opendaylight.neutron.spi.NeutronCRUDInterfaces;
import org.opendaylight.neutron.spi.NeutronRouter;
import org.opendaylight.neutron.spi.NeutronRouter_Interface;


/**
 * Neutron Northbound REST APIs.<br>
 * This class provides REST APIs for managing neutron routers
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

@Path("/routers")
public class NeutronRoutersNorthbound
    extends AbstractNeutronNorthboundIAware<NeutronRouter, NeutronRouterRequest, INeutronRouterCRUD, INeutronRouterAware> {
    static final String ROUTER_INTERFACE_STR = "network:router_interface";
    static final String ROUTER_GATEWAY_STR = "network:router_gateway";
    private static final String RESOURCE_NAME = "Router";

    @Override
    protected String getResourceName() {
        return RESOURCE_NAME;
    }

    @Override
    protected NeutronRouter extractFields(NeutronRouter o, List<String> fields) {
        return o.extractFields(fields);
    }

    private NeutronCRUDInterfaces getNeutronInterfaces(boolean flag) {
        NeutronCRUDInterfaces answer = new NeutronCRUDInterfaces().fetchINeutronRouterCRUD(this);
        if (answer.getRouterInterface() == null) {
            throw new ServiceUnavailableException(serviceUnavailable());
        }
        if (flag) {
            answer = answer.fetchINeutronNetworkCRUD(this);
            if (answer.getNetworkInterface() == null) {
                throw new ServiceUnavailableException("Network CRUD Interface "
                    + RestMessages.SERVICEUNAVAILABLE.toString());
            }
        }
        return answer;
    }

    @Override
    protected INeutronRouterCRUD getNeutronCRUD() {
        return getNeutronInterfaces(false).getRouterInterface();
    }

    private NeutronCRUDInterfaces getAttachInterfaces() {
        NeutronCRUDInterfaces answer = new NeutronCRUDInterfaces().fetchINeutronRouterCRUD(this);
        if (answer.getRouterInterface() == null) {
            throw new ServiceUnavailableException(serviceUnavailable());
        }
        answer = answer.fetchINeutronPortCRUD(this).fetchINeutronSubnetCRUD(this);
        if (answer.getPortInterface() == null) {
            throw new ServiceUnavailableException("Port CRUD Interface "
                    + RestMessages.SERVICEUNAVAILABLE.toString());
        }
        if (answer.getSubnetInterface() == null) {
            throw new ServiceUnavailableException("Subnet CRUD Interface "
                    + RestMessages.SERVICEUNAVAILABLE.toString());
        }
        return answer;
    }

    @Override
    protected NeutronRouterRequest newNeutronRequest(NeutronRouter o) {
        return new NeutronRouterRequest(o);
    }

    @Override
    protected Object[] getInstances() {
        return NeutronUtil.getInstances(INeutronRouterAware.class, this);
    }

    @Override
    protected int canCreate(Object instance, NeutronRouter singleton) {
        INeutronRouterAware service = (INeutronRouterAware) instance;
        return service.canCreateRouter(singleton);
    }

    @Override
    protected void created(Object instance, NeutronRouter singleton) {
        INeutronRouterAware service = (INeutronRouterAware) instance;
        service.neutronRouterCreated(singleton);
    }

    @Override
    protected int canUpdate(Object instance, NeutronRouter delta, NeutronRouter original) {
        INeutronRouterAware service = (INeutronRouterAware) instance;
        return service.canUpdateRouter(delta, original);
    }

    @Override
    protected void updated(Object instance, NeutronRouter updated) {
        INeutronRouterAware service = (INeutronRouterAware) instance;
        service.neutronRouterUpdated(updated);
    }

    @Override
    protected int canDelete(Object instance, NeutronRouter singleton) {
        INeutronRouterAware service = (INeutronRouterAware) instance;
        return service.canDeleteRouter(singleton);
    }

    @Override
    protected void deleted(Object instance, NeutronRouter singleton) {
        INeutronRouterAware service = (INeutronRouterAware) instance;
        service.neutronRouterDeleted(singleton);
    }

    /**
     * Returns a list of all Routers */

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    //@TypeHint(OpenStackRouters.class)
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAUTHORIZED, condition = "Unauthorized"),
            @ResponseCode(code = HttpURLConnection.HTTP_NOT_IMPLEMENTED, condition = "Not Implemented"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response listRouters(
            // return fields
            @QueryParam("fields") List<String> fields,
            // note: openstack isn't clear about filtering on lists, so we aren't handling them
            @QueryParam("id") String queryID,
            @QueryParam("name") String queryName,
            @QueryParam("admin_state_up") Boolean queryAdminStateUp,
            @QueryParam("status") String queryStatus,
            @QueryParam("tenant_id") String queryTenantID,
            @QueryParam("external_gateway_info") String queryExternalGatewayInfo,
            // pagination
            @QueryParam("limit") String limit,
            @QueryParam("marker") String marker,
            @QueryParam("page_reverse") String pageReverse
            // sorting not supported
            ) {
        INeutronRouterCRUD routerInterface = getNeutronInterfaces(false).getRouterInterface();
        if (routerInterface == null) {
            throw new ServiceUnavailableException(serviceUnavailable());
        }
        List<NeutronRouter> allRouters = routerInterface.getAllRouters();
        List<NeutronRouter> ans = new ArrayList<NeutronRouter>();
        Iterator<NeutronRouter> i = allRouters.iterator();
        while (i.hasNext()) {
            NeutronRouter oSS = i.next();
            if ((queryID == null || queryID.equals(oSS.getID())) &&
                    (queryName == null || queryName.equals(oSS.getName())) &&
                    (queryAdminStateUp == null || queryAdminStateUp.equals(oSS.getAdminStateUp())) &&
                    (queryStatus == null || queryStatus.equals(oSS.getStatus())) &&
                    (queryExternalGatewayInfo == null || queryExternalGatewayInfo.equals(oSS.getExternalGatewayInfo())) &&
                    (queryTenantID == null || queryTenantID.equals(oSS.getTenantID()))) {
                if (fields.size() > 0) {
                    ans.add(extractFields(oSS,fields));
                } else {
                    ans.add(oSS);
                }
            }
        }
        //TODO: apply pagination to results
        return Response.status(HttpURLConnection.HTTP_OK).entity(
                new NeutronRouterRequest(ans)).build();
    }

    /**
     * Returns a specific Router */

    @Path("{routerUUID}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    //@TypeHint(OpenStackRouters.class)
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAUTHORIZED, condition = "Unauthorized"),
            @ResponseCode(code = HttpURLConnection.HTTP_FORBIDDEN, condition = "Forbidden"),
            @ResponseCode(code = HttpURLConnection.HTTP_NOT_FOUND, condition = "Not Found"),
            @ResponseCode(code = HttpURLConnection.HTTP_NOT_IMPLEMENTED, condition = "Not Implemented"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response showRouter(
            @PathParam("routerUUID") String routerUUID,
            // return fields
            @QueryParam("fields") List<String> fields) {
        return show(routerUUID, fields);
    }

    /**
     * Creates new Routers */

    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    //@TypeHint(OpenStackRouters.class)
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_CREATED, condition = "Created"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response createRouters(final NeutronRouterRequest input) {
        getNeutronInterfaces(true); // ensure that network service is loaded
        return create(input);
    }

    @Override
    protected void updateDelta(String uuid, NeutronRouter delta, NeutronRouter original) {
        delta.setID(uuid);
        delta.setTenantID(original.getTenantID());
    }

    /**
     * Updates a Router */

    @Path("{routerUUID}")
    @PUT
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    //@TypeHint(OpenStackRouters.class)
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
            @ResponseCode(code = HttpURLConnection.HTTP_NOT_FOUND, condition = "Not Found"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response updateRouter(
            @PathParam("routerUUID") String routerUUID,
            NeutronRouterRequest input
            ) {
        getNeutronInterfaces(true); // ensure that network service is loaded
        return update(routerUUID, input);
    }

    /**
     * Deletes a Router */

    @Path("{routerUUID}")
    @DELETE
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_NO_CONTENT, condition = "No Content"),
            @ResponseCode(code = HttpURLConnection.HTTP_NOT_FOUND, condition = "Not Found"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response deleteRouter(
            @PathParam("routerUUID") String routerUUID) {
        return delete(routerUUID);
    }

    /**
     * Adds an interface to a router */

    @Path("{routerUUID}/add_router_interface")
    @PUT
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    //@TypeHint(OpenStackRouterInterfaces.class)
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response addRouterInterface(
            @PathParam("routerUUID") String routerUUID,
            NeutronRouter_Interface input
            ) {
        NeutronCRUDInterfaces interfaces = getAttachInterfaces();
        INeutronRouterCRUD routerInterface = interfaces.getRouterInterface();

        NeutronRouter target = routerInterface.getRouter(routerUUID);
        Object[] instances = getInstances();
        if (instances != null) {
            for (Object instance : instances) {
                INeutronRouterAware service = (INeutronRouterAware) instance;
                int status = service.canAttachInterface(target, input);
                if (status < HTTP_OK_BOTTOM || status > HTTP_OK_TOP) {
                    return Response.status(status).build();
                }
            }
        }

        target.addInterface(input.getPortUUID(), input);
        if (instances != null) {
            for (Object instance : instances) {
                INeutronRouterAware service = (INeutronRouterAware) instance;
                service.neutronRouterInterfaceAttached(target, input);
            }
        }

        return Response.status(HttpURLConnection.HTTP_OK).entity(input).build();
    }


    private int checkDownstreamDetach(NeutronRouter target, NeutronRouter_Interface input) {
        Object[] instances = getInstances();
        if (instances != null) {
            for (Object instance : instances) {
                INeutronRouterAware service = (INeutronRouterAware) instance;
                int status = service.canDetachInterface(target, input);
                if (status < HTTP_OK_BOTTOM || status > HTTP_OK_TOP) {
                    return status;
                }
            }
        }
        return HTTP_OK_BOTTOM;
    }

    /**
     * Removes an interface to a router */

    @Path("{routerUUID}/remove_router_interface")
    @PUT
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    //@TypeHint(OpenStackRouterInterfaces.class)
    @StatusCodes({
            @ResponseCode(code = HttpURLConnection.HTTP_OK, condition = "Operation successful"),
            @ResponseCode(code = HttpURLConnection.HTTP_UNAVAILABLE, condition = "No providers available") })
    public Response removeRouterInterface(
            @PathParam("routerUUID") String routerUUID,
            NeutronRouter_Interface input
            ) {
        NeutronCRUDInterfaces interfaces = getAttachInterfaces();
        INeutronRouterCRUD routerInterface = interfaces.getRouterInterface();
        Object[] instances = getInstances();

        NeutronRouter target = routerInterface.getRouter(routerUUID);
        input.setID(target.getID());
        input.setTenantID(target.getTenantID());
        int status = checkDownstreamDetach(target, input);
        if (status != HTTP_OK_BOTTOM) {
            return Response.status(status).build();
        }
        target.removeInterface(input.getPortUUID());
        if (instances != null) {
            for (Object instance : instances) {
                INeutronRouterAware service = (INeutronRouterAware) instance;
                service.neutronRouterInterfaceDetached(target, input);
            }
        }
        return Response.status(HttpURLConnection.HTTP_OK).entity(input).build();
    }
}
