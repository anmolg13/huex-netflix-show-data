package com.training.netflixapp.util;

import com.training.netflixapp.dto.NetflixDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NetflixReader implements Iterable<NetflixDto> {

    Logger logger = LoggerFactory.getLogger("NetflixReader.class");
    private final FileInputStream fin;
    private BufferedReader br;

    public NetflixReader(String fileName) throws IOException {
        this.fin = new FileInputStream(fileName);
        this.br = new BufferedReader(new InputStreamReader(fin));
        String header = br.readLine();
    }

    public void reset() throws IOException {
        this.fin.getChannel().position(0);
        this.br = new BufferedReader(new InputStreamReader(fin));
        String header = br.readLine();
    }

    public void close() throws IOException {
        br.close();
    }

    @Override
    public Iterator<NetflixDto> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                try {
                    return br.ready();
                } catch (IOException e) {
                    return false;
                }
            }

            @Override
            public NetflixDto next() {
                try {
                    String[] strings = br.readLine().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    List<String> stringList = Arrays.stream(strings).map(str -> {
                        int len = str.length();
                        if (len <= 1) {
                            return str.trim();
                        }

                        if (str.charAt(0) == '"' && str.charAt(len - 1) == '"') {
                            return str.substring(1, len - 1).trim();
                        }
                        return str.trim();
                    }).collect(Collectors.toList());

                    if (stringList.size() == 12) {
                        var netflixDto = new NetflixDto();
                        netflixDto.setShowId(stringList.get(0));
                        netflixDto.setType(stringList.get(1));
                        netflixDto.setTitle(stringList.get(2));
                        netflixDto.setDirector(stringList.get(3));
                        netflixDto.setCast(stringList.get(4));
                        netflixDto.setCountry(stringList.get(5));
                        try {
                            netflixDto.setDateAdded(LocalDate.parse(stringList.get(6), DateTimeFormatter.ofPattern("MMMM d, yyyy")));
                        } catch (DateTimeParseException e) {
                            netflixDto.setDateAdded(LocalDate.MIN);
                        }
                        netflixDto.setReleaseYear(stringList.get(7));
                        netflixDto.setRating(stringList.get(8));
                        netflixDto.setDuration(Integer.parseInt(stringList.get(9).split(" ")[0]));
                        netflixDto.setListedIn(Arrays.stream(stringList.get(10).split(",")).map(String::trim).collect(Collectors.toList()));
                        netflixDto.setDescription(stringList.get(11));
                        return netflixDto;
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    return null;
                }
            }
        };
    }

    public Stream<NetflixDto> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }
}
