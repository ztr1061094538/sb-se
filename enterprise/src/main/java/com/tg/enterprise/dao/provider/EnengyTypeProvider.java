package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.EnergyType;
import org.apache.ibatis.jdbc.SQL;

/**
 * 能源类型 Provider
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-14
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-14			新增
 */
public class EnengyTypeProvider {

    /**
     * 全查列表
     */
    public String selectList(final EnergyType energyType) {
        return new SQL() {
            {
                SELECT("*");
                FROM("energy_type");
                if (energyType != null) {
                    if (energyType.getCode() != null) {
                        WHERE("code = #{code}");
                    }
                    if (energyType.getName() != null) {
                        WHERE("name = #{name}");
                    }
                    if (energyType.getPcode() != null) {
                        WHERE("pcode = #{pcode}");
                    }
                    if (energyType.getUnit() != null) {
                        WHERE("unit = #{unit}");
                    }
                    if (energyType.getClasscode() != null) {
                        WHERE("classCode = #{classcode}");
                    }
                    if (energyType.getNhzbdw() != null) {
                        WHERE("nhzbdw = #{nhzbdw}");
                    }
                    if (energyType.getType() != null) {
                        WHERE("type = #{type}");
                    }
                    if (energyType.getZbckz() != null) {
                        WHERE("zbckz = #{zbckz}");
                    }
                    if (energyType.getZbxs() != null) {
                        WHERE("zbxs = #{zbxs}");
                    }
                    if (energyType.getDwzbxs() != null) {
                        WHERE("dwzbxs = #{dwzbxs}");
                    }
                    if(energyType.getCategory() != null) {
                        WHERE("category = #{category}");
                    }
                }
            }
        }.toString();
    }

    /**
     * 更新
     */
    public String update(final EnergyType energyType) {
        return new SQL() {
            {
                UPDATE("energy_type");
                if (energyType != null) {
                    if (energyType.getCode() != null) {
                        SET("code = #{code}");
                    }
                    if (energyType.getName() != null) {
                        SET("name = #{name}");
                    }
                    if (energyType.getPcode() != null) {
                        SET("pcode = #{pcode}");
                    }
                    if (energyType.getUnit() != null) {
                        SET("unit = #{unit}");
                    }
                    if (energyType.getClasscode() != null) {
                        SET("classCode = #{classcode}");
                    }
                    if (energyType.getNhzbdw() != null) {
                        SET("nhzbdw = #{nhzbdw}");
                    }
                    if (energyType.getType() != null) {
                        SET("type = #{type}");
                    }
                    if (energyType.getZbckz() != null) {
                        SET("zbckz = #{zbckz}");
                    }
                    if (energyType.getZbxs() != null) {
                        SET("zbxs = #{zbxs}");
                    }
                    if (energyType.getDwzbxs() != null) {
                        SET("dwzbxs = #{dwzbxs}");
                    }
                    if(energyType.getCategory() != null) {
                        SET("category = #{category}");
                    }
                }
                WHERE("code = #{code}");
            }
        }.toString();
    }

    /**
     * 条件删除
     */
    public String delete(final EnergyType energyType) {
        return new SQL() {
            {
                DELETE_FROM("energy_type");
                if (energyType != null) {
                    if (energyType.getCode() != null) {
                        WHERE("code = #{code}");
                    }
                    if (energyType.getName() != null) {
                        WHERE("name = #{name}");
                    }
                    if (energyType.getPcode() != null) {
                        WHERE("pcode = #{pcode}");
                    }
                    if (energyType.getUnit() != null) {
                        WHERE("unit = #{unit}");
                    }
                    if (energyType.getClasscode() != null) {
                        WHERE("classCode = #{classcode}");
                    }
                    if (energyType.getNhzbdw() != null) {
                        WHERE("nhzbdw = #{nhzbdw}");
                    }
                    if (energyType.getType() != null) {
                        WHERE("type = #{type}");
                    }
                    if (energyType.getZbckz() != null) {
                        WHERE("zbckz = #{zbckz}");
                    }
                    if (energyType.getZbxs() != null) {
                        WHERE("zbxs = #{zbxs}");
                    }
                    if (energyType.getDwzbxs() != null) {
                        WHERE("dwzbxs = #{dwzbxs}");
                    }
                    if(energyType.getCategory() != null) {
                        WHERE("category = #{category}");
                    }
                }
            }
        }.toString();
    }

}
