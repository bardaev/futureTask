package com.example.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Readers {

    private List<Readers.ThreadRequest> requestList;
    public Readers(int countReaders, long startRange, long endRange, String host, int port) {
        requestList = new ArrayList<>();

        long step = (endRange - startRange) / countReaders;
        long from = startRange;

        for (int i = 0; i < countReaders; i++) {
            requestList.add(new ThreadRequest(from, from + step, host, port));
            from += step;
        }
    }

    public void start() {
        for (Readers.ThreadRequest t : requestList) {
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
            ResponseEntity<Amount> response;

            for (; from < to; from++) {
                URL url = null;
                try {
                    url = new URL("http", host, port, "/get/" + from);
                } catch (MalformedURLException e) {
                    System.err.println(e.getMessage());
                }
                response = restTemplate.getForEntity(url.toString(), Amount.class);

//                String resp = restTemplate.getForObject(url.toString(), String.class);
                Amount amount = response.getBody();
                System.out.println(
                        Thread.currentThread().getName() +
                        " Reader " +
                        " : " +
                        response.getStatusCode() +
                        " " +
                        amount.toString()
                );
            }
        }
    }
}
