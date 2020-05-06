package com.tg.lygem2.bean.mapper.provider;

import com.tg.lygem2.vo.MenuCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-15
 **/
@Slf4j
public class MenuMapperProvider {
    public String getMenuList(final MenuCondition menuCondition) {
        String role = new SQL() {
            {
                SELECT("*");
                FROM("menu");
                if (menuCondition != null) {
                    if (StringUtils.isNotBlank(menuCondition.getEnabled())) {
                        WHERE("enabled = #{enabled}");
                    }
                    if (StringUtils.isNotBlank(menuCondition.getIsMenu())) {
                        WHERE("type = #{isMenu}");
                    }
                    if (StringUtils.isNotBlank(menuCondition.getName())) {
                        WHERE("name = #{name}");
                    }
                    if (StringUtils.isNotBlank(menuCondition.getId())) {
                        WHERE("id = #{id}");
                    }
                }
            }
        }.toString();
        if (log.isInfoEnabled()) log.info("sql is" + role);
        return role;
    }
}
