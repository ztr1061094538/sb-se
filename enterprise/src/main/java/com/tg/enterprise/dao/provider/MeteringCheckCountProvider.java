package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.MeteringType;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by huangjianbo on 2018/5/11
 */
public class MeteringCheckCountProvider {

    public String queryPageList(final MeteringType entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("metering_type t1");
                if (entity != null) {
                    if (entity.getMetering_type() != null) {
                        WHERE("t1.metering_type like CONCAT('%',#{metering_type},'%')");
                    }
                }
            }
        }.toString();
    }
}
