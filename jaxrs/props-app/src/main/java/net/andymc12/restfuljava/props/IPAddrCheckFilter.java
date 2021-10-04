package net.andymc12.restfuljava.props;

import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.io.IOException;
import java.net.InetAddress;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IPAddrCheckFilter implements ContainerRequestFilter {

    @Context
    HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        InetAddress ipAddress = InetAddress.getByName(request.getRemoteAddr());
        System.out.println("Request from: " + ipAddress);
        if (!allowedIPAddress(ipAddress)) {
            ctx.abortWith(Response.status(UNAUTHORIZED).entity("Request must be from local address").build());
        }
    }

    private boolean allowedIPAddress(InetAddress ipAddress) {
        return ipAddress.isAnyLocalAddress() || ipAddress.isLoopbackAddress();
    }
}
