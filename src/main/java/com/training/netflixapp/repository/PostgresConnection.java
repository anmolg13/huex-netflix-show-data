package com.training.netflixapp.repository;

import com.training.netflixapp.entity.NetflixEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgresConnection {

    private static final String JDBCURL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "ANMOLG@1312";

    Logger logger = LoggerFactory.getLogger("NetflixService.class");


    public boolean insert(NetflixEntity netflixEntity) throws SQLException {
        logger.info("Adding records");
        try (var connection = DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD); PreparedStatement ps = connection.prepareStatement("INSERT INTO NETFLIX.SHOWS (type, name, date_added, listed_in, country) VALUES (?, ?, ?, ?, ?)")) {

            ps.setString(1, netflixEntity.getType());
            ps.setString(2, netflixEntity.getTitle());
            ps.setDate(3, Date.valueOf(netflixEntity.getDateAdded()));
            ps.setString(4, netflixEntity.getListedIn());
            ps.setString(5, netflixEntity.getCountry());
            ps.execute();

            return true;
        } catch (SQLException e) {
            throw new SQLException("Insertion failed: " + e.getMessage());
        }

    }


    public List<NetflixEntity> select(String query) throws SQLException {
        logger.info("Fetching data from Db");
        try (var connection = DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD); Statement statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(query);
            List<NetflixEntity> list = new ArrayList<>();
            while (resultSet.next()) {
                var netflixEntity = new NetflixEntity();
                netflixEntity.setCountry(resultSet.getString("country"));
                var date = resultSet.getDate("date_added");
                var dateAdded = date.toLocalDate();
                netflixEntity.setDateAdded(dateAdded);
                netflixEntity.setTitle(resultSet.getString("name"));
                netflixEntity.setType(resultSet.getString("type"));
                netflixEntity.setListedIn(resultSet.getString("listed_in"));
                list.add(netflixEntity);
            }
            resultSet.close();
            return list;
        } catch (SQLException e) {
            throw new SQLException("Retrieval failed: " + e.getMessage());
        }
    }
}
