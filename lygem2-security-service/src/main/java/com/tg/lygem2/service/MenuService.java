package com.tg.lygem2.service;

import com.tg.lygem2.bean.mapper.MenuMapper;
import com.tg.lygem2.vo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Cacheable(cacheNames="allMenu", key="'lygem2@2020'")
    public List<Menu> getAllMenu() 
    {
        return menuMapper.findAll();
    }

}
