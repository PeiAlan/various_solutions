package com.ellison.cache.service.impl;

import com.ellison.cache.entity.Provinces;
import com.ellison.cache.service.ProvincesService;
import org.springframework.cache.annotation.*;

/**
 * springcache优雅实现缓存
 */
//@Service("provincesService")
@CacheConfig(cacheNames="province") //通用配置
public class ProvincesServiceImpl2 extends ProvincesServiceImpl implements ProvincesService {
//    @Cacheable(value = "province",
//            key = "#root.targetClass.simpleName+':'+#root.methodName+':'+#provinceid")
    @Override
    @Cacheable// value指定当前接口，要使用哪一个缓存器 --- 如果该缓存器不存在，则创建一个
    public Provinces detail(String provinceid) {//一个接口方法，对应一个缓存器
        return super.detail(provinceid);
    }

    //这个AOP，是先删缓存，先改数据库？
    @Override
    @CachePut(key = "#entity.provinceid")
    public Provinces update(Provinces entity) {
        return super.update(entity);
    }

    @Override
    @CacheEvict
    public void delete(String provinceid) {
        super.delete(provinceid);
    }

    //组合配置
    @Override
    @Caching(put = {
            @CachePut(key = "#entity.provinceid"),
            @CachePut(key = "#entity.provinceid")}
    )
    public Provinces add(Provinces entity) {
        return super.add(entity);
    }

}