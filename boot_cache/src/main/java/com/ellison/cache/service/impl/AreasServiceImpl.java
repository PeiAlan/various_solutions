package com.ellison.cache.service.impl;

import com.ellison.cache.dao.AreasDao;
import com.ellison.cache.entity.Areas;
import com.ellison.cache.service.AreasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("areasService")
public class AreasServiceImpl implements AreasService {
    
    private static final Logger logger = LoggerFactory.getLogger(AreasServiceImpl.class);

    @Autowired
    private AreasDao areasDao;


    @Override
    public int delete(String[] ids){
        return areasDao.deleteById(ids);
    }


    @Override
    public int update(final Areas entity){
        return areasDao.updateByEntity(entity);
    }


    @Override
    public int add(final Areas entity){
        return areasDao.insert(entity);
    }


    @Override
    public List<Areas> list(){
        return areasDao.list();
    }



}