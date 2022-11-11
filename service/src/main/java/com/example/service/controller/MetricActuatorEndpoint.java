package com.example.service.controller;

import com.example.service.aop.MetricControllers;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Component
@RestControllerEndpoint(id = "mc")
public class MetricActuatorEndpoint {

    @GetMapping(path = "/get")
    public @ResponseBody ResponseEntity<Map<String, Double>> getAmountMetric() {
        Map<String, Double> metrics = new HashMap<>();
        metrics.put("Average count requests per minute", MetricControllers.getRequest.getOneMinuteRate());
        metrics.put("Average count requests per five minute", MetricControllers.getRequest.getFiveMinuteRate());
        metrics.put("Count requests", (double) MetricControllers.getRequest.getCount());
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }

    @GetMapping(path = "/add")
    public @ResponseBody ResponseEntity<Map<String, Double>> addAmountMetric() {
        Map<String, Double> metrics = new HashMap<>();
        metrics.put("Average count requests per minute", MetricControllers.addRequest.getOneMinuteRate());
        metrics.put("Average count requests per five minute", MetricControllers.addRequest.getFiveMinuteRate());
        metrics.put("Count requests", (double) MetricControllers.addRequest.getCount());
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }

    @GetMapping(path = "/total")
    public @ResponseBody ResponseEntity<Map<String, Long>> totalMetric() {
        Map<String, Long> metrics = new HashMap<>();
        metrics.put("Total count requests", MetricControllers.totalRequest.getCount());
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }

    @DeleteMapping(path = "/reset")
    public ResponseEntity<String> reset() {
        MetricControllers.metrics.remove(MetricControllers.GET_METRIC);
        MetricControllers.metrics.remove(MetricControllers.ADD_METRIC);
        MetricControllers.metrics.remove(MetricControllers.TOTAL_METRIC);

        MetricControllers.getRequest = MetricControllers.metrics.meter(MetricControllers.GET_METRIC);
        MetricControllers.addRequest = MetricControllers.metrics.meter(MetricControllers.ADD_METRIC);
        MetricControllers.totalRequest = MetricControllers.metrics.counter(MetricControllers.TOTAL_METRIC);

        return new ResponseEntity<>("Metrics reset", HttpStatus.OK);
    }
}
