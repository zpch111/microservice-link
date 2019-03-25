package pers.zpc.ml.interceptor.http;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.zpc.ml.cons.LogCons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zpc
 * intercept the http requst and response to add log event.
 * rans or generate logId,and spanId
 */
@Configuration
public class HttpHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String logId = request.getHeader(LogCons.LOG_ID);
        if (logId == null || "".equals(logId)) {
            logId = LogCons.generateId();
        }
        MDC.put(LogCons.LOG_ID, logId);
        String spanId = LogCons.generateId();
        MDC.put(LogCons.SPAN_ID, spanId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //remove MDC value
        MDC.remove(LogCons.LOG_ID);
        MDC.remove(LogCons.SPAN_ID);
    }
}
