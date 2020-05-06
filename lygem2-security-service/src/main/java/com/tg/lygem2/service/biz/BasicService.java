package com.tg.lygem2.service.biz;

import com.tg.lygem2.bean.mapper.BridgeMapper;
import com.tg.lygem2.bean.mapper.MenuMapper;
import com.tg.lygem2.bean.mapper.RoleMapper;
import com.tg.lygem2.bean.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-10
 **/
public class BasicService{
    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected BridgeMapper bridgeMapper;

    @Autowired
    protected RoleMapper roleMapper;

    @Autowired
    protected MenuMapper menuMapper;

}
