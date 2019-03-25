package pers.zpc.ml.interceptor.invoke;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.logging.log4j.LogManager;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.zpc.ml.cons.LogCons;


/**
 * to add logUnit to  FeignClient invoke method.
 * trans  the logId to invoked server.
 **/
@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignClientInterceptor();
    }

    @Override
    public void apply(RequestTemplate template) {
        String logId = MDC.get(LogCons.LOG_ID);
        //no logId,do nothing
        if(logId == null){
            return;
        }
        template.header(LogCons.LOG_ID,logId);
    }
}
