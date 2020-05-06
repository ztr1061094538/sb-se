package com.tg.lygem2.bean.mapper.provider;

import com.tg.lygem2.vo.UserCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-12
 **/
@Slf4j
public class UserMapperProvider {

    public String getUserList(final UserCondition userCondition){
        String role = new SQL() {
            {
                SELECT("*");
                FROM("user");
                if (userCondition != null) {
                    if (StringUtils.isNotBlank(userCondition.getUsername())) {
                        WHERE("USERNAME = #{username}");
                    }
                    if (StringUtils.isNotBlank(userCondition.getName())) {
                        WHERE("ADDRESS = #{address}");
                    }
                    if (StringUtils.isNotBlank(userCondition.getName())) {
                        WHERE("NAME = #{name}");
                    }
                    if (StringUtils.isNotBlank(userCondition.getCity())) {
                        WHERE("CITY = #{city}");
                    }
                    if (StringUtils.isNotBlank(userCondition.getEnabled())) {
                        WHERE("ENABLED = #{enabled}");
                    }
                    if (null != userCondition.getUser_type()) {
                        WHERE("user_type = #{user_type}");
                    }
                }
                WHERE("1=1");
            }
        }.toString();
        if(log.isInfoEnabled())log.info("sql is" + role);
        return role;
    }
}
