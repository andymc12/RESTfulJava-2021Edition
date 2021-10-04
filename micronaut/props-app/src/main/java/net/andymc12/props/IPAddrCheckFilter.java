package net.andymc12.props;

import java.net.InetAddress;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;

import org.reactivestreams.Publisher;

@Filter("/**")
public class IPAddrCheckFilter implements HttpServerFilter {

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        InetAddress ipAddress = request.getRemoteAddress().getAddress();
        System.out.println("Request from: " + ipAddress);
        if (!allowedIPAddress(ipAddress)) {
            return Publishers.just(HttpResponse.unauthorized().body("Request must be from a local address"));
        }
        
        return chain.proceed(request);
    }

    private boolean allowedIPAddress(InetAddress ipAddress) {
        return ipAddress.isAnyLocalAddress() || ipAddress.isLoopbackAddress();
    }
}