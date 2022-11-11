package com.example.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Writers {

    private List<Writers.ThreadRequest> requestList;

    public Writers(int countWriters, long startRange, long endRange, String host, int port) {

        requestList = new ArrayList<>();

        long step = (endRange - startRange) / countWriters;
        long from = startRange;

        for (int i = 0; i < countWriters; i++) {
            requestList.add(new Writers.ThreadRequest(from, from + step, host, port));
            from += step;
        }
    }

    public void start() {
        for (Writers.ThreadRequest t : requestList) {
            t.start();
        }
    }

    private static class ThreadRequest extends Thread {
        private long from;
        private final long to;
        private final String host;
        private final int port;

        public ThreadRequest(long from, long to, String host, int port) {
            this.from = from;
            this.to = to;
            this.host = host;
            this.port = port;
        }

        public void run() {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Amount> request;

            for (; from < to; from++) {
                URL url = null;
                try {
                    url = new URL("http", host, port, "/add");
                } catch (MalformedURLException e) {
                    System.err.println(e.getMessage());
                }
                request = new HttpEntity<>(new Amount(from, from));
                ResponseEntity<Amount> response = restTemplate.exchange(url.toString(), HttpMethod.POST, request, Amount.class);

                System.out.println(
                        Thread.currentThread().getName() +
                        " Writer " +
                        " : " +
                        "code: " +
                        response.getStatusCodeValue() +
                        " Amount: " + response.getBody().toString()
                );
            }
        }
    }
}
