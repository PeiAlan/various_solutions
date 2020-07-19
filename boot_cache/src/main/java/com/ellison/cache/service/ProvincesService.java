package com.ellison.cache.service;


import com.ellison.cache.entity.Provinces;

import java.util.List;

public interface ProvincesService {
    public List<Provinces> list();
    public Provinces detail(String id);
    public Provinces update(Provinces entity);
    public Provinces add(Provinces entity);
    public void delete(String id);
}