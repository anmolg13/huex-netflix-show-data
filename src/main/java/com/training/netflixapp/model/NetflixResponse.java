package com.training.netflixapp.model;

import com.training.netflixapp.dto.NetflixDto;
import com.training.netflixapp.entity.NetflixEntity;

import java.util.List;

public class NetflixResponse {
    List<NetflixDto> netflixList;

    public List<NetflixEntity> getNetflixEntityList() {
        return netflixEntityList;
    }

    public void setNetflixEntityList(List<NetflixEntity> netflixEntityList) {
        this.netflixEntityList = netflixEntityList;
    }

    List<NetflixEntity> netflixEntityList;

    public List<NetflixDto> getNetflixList() {
        return netflixList;
    }

    public void setNetflixList(List<NetflixDto> netflixList) {
        this.netflixList = netflixList;
    }
}
