package com.gridgainexemple;

import org.junit.Test;

import static org.junit.Assert.*;

public class GridgainConnectorTest {

    @Test
    public void getCache() {
        GridgainConnector connector = new GridgainConnector();
        var cache = connector.getCache();

    }
}