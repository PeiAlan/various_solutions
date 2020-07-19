package com.ellison.cache.service.impl;

import com.ellison.cache.entity.Provinces;
import com.ellison.cache.service.ProvincesService;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 缓存穿透
 *
 * 缓存穿透：模拟一个不存在的key值，绕过Redis直接查询MySQL，MySQL中没有此值，但一直被查询。
 * 解决方案：使用布隆过滤器，经过过滤器处理后，只存在0和1，查询时，先通过布隆过滤器查询，当返回0时，表示数据库中没有数据，直接拒绝查询
 */
//@Service("provincesService")
public class ProvincesServiceImpl4 extends ProvincesServiceImpl implements ProvincesService {
    private BloomFilter<String> bf =null; //等效成一个set集合

    @PostConstruct //对象创建后，自动调用本方法
    public void init(){//在bean初始化完成后，实例化bloomFilter,并加载数据
        List<Provinces> provinces = this.list();

        //当成一个SET----- 占内存，比hashset占得小很多
        bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), provinces.size());// 32个
        for (Provinces p : provinces) {
            bf.put(p.getProvinceid());
        }
    }

    @Override
    @Cacheable(value = "province")
    public Provinces detail(String provinceid) {
        //先判断布隆过滤器中是否存在该值，值存在才允许访问缓存和数据库
        if(!bf.mightContain(provinceid)){
            System.out.println("非法访问--------"+System.currentTimeMillis());
            return null;
        }
        System.out.println("数据库中得到数据--------"+System.currentTimeMillis());
        Provinces provinces = super.detail(provinceid);

        return provinces;
    }

    @Override
    @CachePut(value = "province",key = "#entity.provinceid")
    public Provinces update(Provinces entity) {
        super.update(entity);
        return entity;
    }

    @Override
    @CacheEvict(value = "province",key = "#entity.provinceid")
    public Provinces add(Provinces entity) {
        super.add(entity);
        bf.put(entity.getProvinceid());//新生成，加入过滤器
        return entity;
    }

    @Override
    @CacheEvict("province")
    public void delete(String provinceid) {
        super.delete(provinceid);
    }
}