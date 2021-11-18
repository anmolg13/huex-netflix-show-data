package com.training.netflixapp.service;

import com.training.netflixapp.dto.NetflixDto;
import com.training.netflixapp.entity.NetflixEntity;
import com.training.netflixapp.model.DefaultResponse;
import com.training.netflixapp.model.NetflixRequest;
import com.training.netflixapp.model.NetflixResponse;
import com.training.netflixapp.repository.PostgresConnection;
import com.training.netflixapp.util.NetflixReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NetflixService {

    Logger logger = LoggerFactory.getLogger("NetflixService.class");

    private final PostgresConnection postgresConnection;

    public NetflixService(PostgresConnection postgresConnection) {
        this.postgresConnection = postgresConnection;
    }

    public NetflixResponse displayRecords(final String dataSource, final Integer count, final String type, String country, LocalDate startDate, LocalDate endDate) throws IOException, SQLException {
        try {
            if (dataSource.equals("csv file")) {
                logger.info("Data from CSV");
                var reader = new NetflixReader("netflix_titles.csv");

                Stream<NetflixDto> stream = reader.stream().filter(Objects::nonNull);
                if (type != null) {
                    stream = stream.filter(item -> type.equals(item.getType()));
                }
                if (country != null) {
                    stream = stream.filter(item -> country.equals(item.getCountry()));
                }
                if (startDate != null) {
                    stream = stream.filter(item -> item.getDateAdded().isAfter(startDate));
                }
                if (endDate != null) {
                    stream = stream.filter(item -> item.getDateAdded().isBefore(endDate));
                }
                if (count != null) {
                    stream = stream.limit(count);
                }

                List<NetflixDto> netflixDtoList = stream.collect(Collectors.toList());

                var response = new NetflixResponse();
                response.setNetflixList(netflixDtoList);
                response.setNetflixEntityList(new ArrayList<>());
                return response;
            } else if (dataSource.equals("db")) {
                logger.info("Data from Db");
                var query = "SELECT * FROM NETFLIX.SHOWS ";
                if (type != null) {
                    query = query + "WHERE type='" + type + "' ";
                }
                if (type != null && country != null) {
                    query = query + "AND country='" + country + "' ";
                }
                if (type == null && country != null) {

                    query = query + "WHERE country='" + country + "' ";
                }
                if (country != null && startDate != null) {
                    query = query + "AND date_added>='" + startDate + "' ";
                }
                if (country == null && startDate != null) {
                    query = query + "WHERE date_added>='" + startDate + "' ";
                }
                if (startDate != null && endDate != null) {
                    query = query + "AND date<='" + endDate + "' ";
                }
                if (startDate == null && endDate != null) {
                    query = query + "WHERE date<='" + endDate + "' ";
                }
                if (count != null) {
                    query = query + " FETCH FIRST " + count + " ROW ONLY";
                }
                List<NetflixEntity> netflixEntities = postgresConnection.select(query);
                var response = new NetflixResponse();
                response.setNetflixEntityList(netflixEntities);
                response.setNetflixList(new ArrayList<>());
                return response;
            }
        } catch (NullPointerException nullPointerException)
        {
            logger.error(nullPointerException.getMessage());

        }
        return null;
    }

    public DefaultResponse addRecord(NetflixRequest request) {
        var netflixEntity = new NetflixEntity();

        netflixEntity.setType(request.getType());
        netflixEntity.setTitle(request.getTitle());
        netflixEntity.setDateAdded(request.getDateAdded());
        netflixEntity.setListedIn(String.join(",", request.getListedIn()));
        netflixEntity.setCountry(request.getCountry());

        var response = new DefaultResponse();
        try {
            boolean res = postgresConnection.insert(netflixEntity);
            if (res) {
                response.setMessage("Record inserted");
            } else {
                response.setMessage("Insert failed");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setMessage("Insert failed: " + e.getMessage());
        }

        return response;
    }
}
