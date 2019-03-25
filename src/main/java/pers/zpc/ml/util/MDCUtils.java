package pers.zpc.ml.util;

import org.slf4j.MDC;
import pers.zpc.ml.cons.LogCons;
import pers.zpc.ml.entity.LogUnitEntity;

/**
 * @author zpc
 * init or destroy MDC value
 */
public class MDCUtils {
    public static void init(LogUnitEntity logUnit){
        MDC.put(LogCons.LOG_ID,logUnit.getLogId());
        MDC.put(LogCons.SPAN_ID,logUnit.getSpanId());
        MDC.put(LogCons.PARENT_SPAN_ID,logUnit.getParentSpanId());
    }
    public static void destroy(){
        MDC.remove(LogCons.LOG_ID);
        MDC.remove(LogCons.SPAN_ID);
        MDC.remove(LogCons.PARENT_SPAN_ID);
    }
    public static LogUnitEntity generator(){
        String logId = MDC.get(LogCons.LOG_ID);
        String spanId = MDC.get(LogCons.SPAN_ID);
        LogUnitEntity logUnit = new LogUnitEntity();
        logUnit.setLogId(logId);
        if(spanId == null || "".equals(spanId)){
            spanId = LogCons.generateId();
            logUnit.setSpanId(spanId);
        }else{
            logUnit.setParentSpanId(spanId);
            logUnit.setSpanId(LogCons.generateId());
        }
        return logUnit;
    }
}
