package com.training.netflixapp.controller;

import com.training.netflixapp.model.DefaultResponse;
import com.training.netflixapp.model.NetflixRequest;
import com.training.netflixapp.model.NetflixResponse;
import com.training.netflixapp.service.NetflixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@RestController
public class NetflixController {

    @Autowired
    private NetflixService netflixService;

    @GetMapping("/tvshows")
    public NetflixResponse displayRecords(
            @RequestParam(required = false) String dataSource,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws IOException, SQLException {
        return netflixService.displayRecords(dataSource, count, type, country, startDate, endDate);
    }

    @PostMapping("/tvshows")
    public DefaultResponse insertRecords(@Valid @RequestBody NetflixRequest request) {
        return netflixService.addRecord(request);
    }
}
