package com.training.netflixapp;

import com.training.netflixapp.controller.NetflixController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTests {

    @Autowired
    private NetflixController netflixController;

    @Test
    void contextLoads() {
        assertThat(netflixController).isNotNull();
    }
}
