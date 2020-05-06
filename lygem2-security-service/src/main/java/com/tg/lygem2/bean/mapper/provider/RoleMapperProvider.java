package com.tg.lygem2.bean.mapper.provider;

import com.tg.lygem2.vo.RoleCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-15
 **/
@Slf4j
public class RoleMapperProvider {
    public String getRoleList(final RoleCondition roleCondition) {
        String role = new SQL() {
            {
                SELECT("*");
                FROM("role");
                if (roleCondition != null) {
                    if (StringUtils.isNotBlank(roleCondition.getName())) {
                        WHERE("NAME = #{name}");
                    }
                    if (StringUtils.isNotBlank(roleCondition.getDesc())) {
                        WHERE("NAMEZH = #{desc}");
                    }
                }
            }
        }.toString();
        if (log.isInfoEnabled()) log.info("sql is" + role);
        return role;
    }
}
