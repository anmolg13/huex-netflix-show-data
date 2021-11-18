package com.training.netflixapp.service;

import com.training.netflixapp.model.NetflixRequest;
import com.training.netflixapp.model.NetflixResponse;
import com.training.netflixapp.repository.PostgresConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class NetflixServiceTests {

    private NetflixService netflixService;

    @BeforeEach
    void initUseCase() {
        PostgresConnection postgresConnection = new PostgresConnection();
        netflixService = new NetflixService(postgresConnection);
    }

    @Test
    void testShowsFromCsv1() throws Exception {
        NetflixResponse netflixResponse = netflixService.displayRecords("csv file", 5, "Movie", "India", null, null);
        Assertions.assertEquals(5, netflixResponse.getNetflixList().size());
    }

    @Test
    void testShowsFromCsv2() throws Exception{
        NetflixResponse netflixResponse=netflixService.displayRecords("csv file", 5, "Movie", "India", null, null);
        Assertions.assertEquals(0,netflixResponse.getNetflixEntityList().size());
    }

    @Test
    void testShowsFromDb() throws Exception {
        NetflixResponse netflixResponse = netflixService.displayRecords("db", 2, "TV Show", "India", null, null);
        Assertions.assertEquals(2, netflixResponse.getNetflixEntityList().size());
    }

    @Test
    void addShow1() {
        NetflixRequest netflixRequest = new NetflixRequest();
        netflixRequest.setCountry("India");
        String date = "2000-10-10";
        LocalDate localDate = LocalDate.parse(date);
        netflixRequest.setDateAdded(localDate);
        netflixRequest.setTitle("Friends");
        netflixRequest.setType("TV Show");
        List<String> listedIn = new ArrayList<>();
        listedIn.add("Comedy");
        listedIn.add("Horror");
        netflixRequest.setListedIn(listedIn);
        Assertions.assertEquals("Record inserted", netflixService.addRecord(netflixRequest).getMessage());
    }

    @Test
    void addShow2() {
        NetflixRequest netflixRequest = new NetflixRequest();
        netflixRequest.setCountry("India");
        String date = "2000-10-10";
        LocalDate localDate = LocalDate.parse(date);
        netflixRequest.setDateAdded(localDate);
        netflixRequest.setType("TV Show");
        List<String> listedIn = new ArrayList<>();
        listedIn.add("Comedy");
        listedIn.add("Horror");
        netflixRequest.setListedIn(listedIn);
        Assertions.assertEquals("Record inserted", netflixService.addRecord(netflixRequest).getMessage());
    }
}
