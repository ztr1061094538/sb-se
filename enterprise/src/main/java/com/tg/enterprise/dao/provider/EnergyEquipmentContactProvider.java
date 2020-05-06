package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.EnergyEquipmentContact;
import com.tg.enterprise.util.SQLUtils;
import com.tg.enterprise.vo.EnergyProcessVO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-12-03
 **/
public class EnergyEquipmentContactProvider {

    public String selectList1(final EnergyEquipmentContact entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("energy_equipment_contact");
                if (entity != null) {
                    if (entity.getCodeList() != null && !entity.getCodeList().isEmpty()) {
                        SQLUtils.getInSql("code", entity.getCodeList());
                    }
                }
            }
        }.toString();
    }
}
