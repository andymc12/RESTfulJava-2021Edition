package net.andymc12.restfuljava.props;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class IPAddressCheckFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            InetAddress ipAddress = InetAddress.getByName(request.getRemoteAddr());
            System.out.println("Request from: " + ipAddress);
            if (allowedIPAddress(ipAddress)) {
                return true;
            }
            response.setStatus(401);
            response.getWriter().print("Request must be from local address");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean allowedIPAddress(InetAddress ipAddress) {
        return ipAddress.isAnyLocalAddress() || ipAddress.isLoopbackAddress();
    }
}
