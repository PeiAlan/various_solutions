package com.ellison.cache.service.impl;

import com.ellison.cache.dao.CitiesDao;
import com.ellison.cache.entity.Cities;
import com.ellison.cache.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("citiesService")
public class CitiesServiceImpl implements CitiesService {
    @Autowired
    private CitiesDao citiesDao;

    @Override
    public int update(final Cities entity){
        return citiesDao.updateByEntity(entity);
    }

    @Override
    public int add(final Cities entity){
        return citiesDao.insert(entity);
    }

    @Override
    @Cacheable(value = "city")
    public List<Cities> list(String provinceid){
        return citiesDao.list(provinceid);
    }



}