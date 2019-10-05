package com.example.realstate;

import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@MicronautTest
class ApplicationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTest.class);
    @Inject
    EmbeddedServer server;

    @Test
    public void smokeTest() {
        LOGGER.info("Server started at host {} port {}, on URL {}", server.getHost(), server.getPort(), server.getURL());
    }
}
