package com.caching.demo.helpers;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class DemoService {

    private ConcurrentSkipListMap<Integer, Integer> mp = new ConcurrentSkipListMap<>();
    private ConcurrentSkipListMap<Integer, Integer> reqMappings = new ConcurrentSkipListMap<>();

    public void addServer() {
        int id = new Random().nextInt(1000);
        System.out.println("Server generated with id " + id);

        int hashed = getHashedVal(id);
        System.out.println("Server generated with hash " + hashed);

        mp.put(hashed, id);

        //Now, re-route requests to this server

        for(Map.Entry<Integer, Integer> m : reqMappings.entrySet()) {
            int serverId = m.getValue();

            Integer higherKey = mp.higherKey(hashed);
            Integer lowerKey = mp.lowerKey(hashed);
            if (higherKey == null) higherKey = mp.firstKey(); // Wrap-around
            if (lowerKey == null) lowerKey = mp.lastKey(); // Wrap-around

                int hashedServerId = getHashedVal(serverId);

                if(hashedServerId >= lowerKey && hashedServerId <= hashed) {
                    //Needs to be re-routed
                    reqMappings.replace(m.getKey(), id);
                    System.out.println("Re routing req with id " + m.getKey() +
                            " to server id " + id);
                }


        }
    }

    public void serveRequest() {
        int id = new Random().nextInt(1000);
        int hashed = getHashedVal(id);

        System.out.println("Request with hash : " + hashed);

        Map.Entry<Integer, Integer> foundServer = mp.higherEntry(hashed);

        if(foundServer == null) {
            System.out.println("Request with id " + id + " served by server id " +
                    mp.firstEntry().getValue());

            reqMappings.put(id, mp.firstEntry().getValue());
        }
        else {
            System.out.println("Request with id " + id + " served by server id " +
                    foundServer.getValue());

            reqMappings.put(id, foundServer.getValue());
        }
    }

    private int getHashedVal(int id) {
        return Integer.hashCode(id) % 100;
    }
}