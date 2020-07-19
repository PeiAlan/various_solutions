package com.ellison.cache.service;


import com.ellison.cache.entity.Cities;

import java.util.List;

public interface CitiesService {
    public int update(final Cities entity);
    public int add(final Cities entity);
    public List<Cities> list(String provinceid);
}