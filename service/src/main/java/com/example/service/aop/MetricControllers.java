package com.example.service.aop;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MetricControllers {

    public static final String GET_METRIC = "GET";
    public static final String ADD_METRIC = "ADD";
    public static final String TOTAL_METRIC = "TOTAL";

    public static final MetricRegistry metrics = new MetricRegistry();
    public static Meter getRequest = metrics.meter(GET_METRIC);
    public static Meter addRequest = metrics.meter(ADD_METRIC);
    public static Counter totalRequest = metrics.counter(TOTAL_METRIC);

    @After("@annotation(MetricGetRq)")
    public void metricGet() {
        getRequest.mark();
    }

    @After("@annotation(MetricAddRq)")
    public void metricAdd() {
        addRequest.mark();
    }

    @After("@annotation(TotalRq)")
    public void metricTotal() {
        totalRequest.inc();
    }
}
