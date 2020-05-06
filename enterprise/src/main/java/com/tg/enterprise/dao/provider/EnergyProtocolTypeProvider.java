package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.EnergyType;
import org.apache.ibatis.jdbc.SQL;

public class EnergyProtocolTypeProvider {

    public String queryList(final EnergyType entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("energy_type");
                if (entity != null) {
                    if (entity.getCode() != null) {
                        WHERE("code=#{code}");
                    }
                }
                WHERE("type=2");
            }
        }.toString();
    }
}
