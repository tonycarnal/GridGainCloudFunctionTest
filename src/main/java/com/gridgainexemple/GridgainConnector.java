package com.gridgainexemple;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientException;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GridgainConnector {

    public static String  ClusterEndPoint = "34.83.119.208:10800";

    public ClientCache getCache(){
        ClientConfiguration cfg = new ClientConfiguration().setAddresses(ClusterEndPoint);
        try {
            IgniteClient client = Ignition.startClient(cfg);
            ClientCache<Integer, String> cache = client.cache("myCache");
            return cache;
        } catch (ClientException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
