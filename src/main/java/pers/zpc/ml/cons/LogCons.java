package pers.zpc.ml.cons;

import java.util.UUID;

public class LogCons {
    public static final String LOG_ID = "logId";
    public static final String SPAN_ID= "spanId";
    public static final String PARENT_SPAN_ID = "parentSpanId";
    public static String generateId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
