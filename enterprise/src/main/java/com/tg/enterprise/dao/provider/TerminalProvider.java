package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.Terminal;
import com.tg.enterprise.vo.TerminalReqVo;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Set;


public class TerminalProvider {

    public String queryPageList(final TerminalReqVo entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("upload_item");
                if (entity != null) {
                    if (entity.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (entity.getParentId() != null) {
                        WHERE("parentId = #{parentId}");
                    }
                    if (entity.getEnterpriseId() != null) {
                        WHERE("enterpriseId = #{enterpriseId}");
                    }
                    if (entity.getSeeType() != null) {
                    	WHERE("seeType = #{seeType}");
                    }
                    if (entity.getName() != null) {
                    	WHERE("name LIKE CONCAT('%', #{name},'%')");
                    }
                    if (entity.getTerminalId() != null) {
                    	WHERE("terminalId LIKE CONCAT('%', #{terminalId},'%')");
                    }
                    if (entity.getDataCode() != null) {
                    	WHERE("dataCode = #{dataCode}");
                    }
                    if (entity.getMetering_id() != null) {
                    	WHERE("metering_id = #{metering_id}");
                    }
                    if (entity.getIds()!=null&&entity.getIds().size()>0){
                        StringBuffer sql= new StringBuffer("(");
                        List<Integer> idList =entity.getIds();
                        for (Integer id : idList)
                        {
                            if (idList.indexOf(id) > 0)
                                sql.append(",");

                            sql.append("'").append(id).append("'");
                        }
                        sql.append(")");
                        WHERE("id in "+sql);
                    }
                    if (entity.getTerminalIds()!=null&&entity.getTerminalIds().size()>0){
                        StringBuffer sql= new StringBuffer("(");
                        Set<String> idList =entity.getTerminalIds();
                        int k = 0;
                        for (String id : idList)
                        {
                            if (k > 0)
                                sql.append(",");

                            sql.append("'").append(id).append("'");
                            k++;
                        }
                        sql.append(")");
                        WHERE("terminalId in "+sql);
                    }
                }
            }
        }.toString();
    }

    public String selectList(final TerminalReqVo entity) {
        return new SQL() {
            {
                SELECT("*");
                FROM("upload_item");
                if (entity != null) {
                    if (entity.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (entity.getParentId() != null) {
                        WHERE("parentId = #{parentId}");
                    }
                    if (entity.getEnterpriseId() != null) {
                        WHERE("enterpriseId = #{enterpriseId}");
                    }
                    if (entity.getSeeType() != null) {
                        WHERE("seeType = #{seeType}");
                    }
                    if (entity.getName() != null) {
                        WHERE("name LIKE CONCAT('%', #{name},'%')");
                    }
                    if (entity.getTerminalId() != null) {
                        WHERE("terminalId LIKE CONCAT('%', #{terminalId},'%')");
                    }
                    if (entity.getDataCode() != null) {
                        WHERE("dataCode = #{dataCode}");
                    }
                    if (entity.getMetering_id() != null) {
                        WHERE("metering_id = #{metering_id}");
                    }
                    if (entity.getIds()!=null&&entity.getIds().size()>0){
                        StringBuffer sql= new StringBuffer("(");
                        List<Integer> idList =entity.getIds();
                        for (Integer id : idList)
                        {
                            if (idList.indexOf(id) > 0)
                                sql.append(",");

                            sql.append("'").append(id).append("'");
                        }
                        sql.append(")");
                        WHERE("id in "+sql);
                    }
                    if (entity.getTerminalIds()!=null&&entity.getTerminalIds().size()>0){
                        StringBuffer sql= new StringBuffer("(");
                        Set<String> idList =entity.getTerminalIds();
                        int k = 0;
                        for (String id : idList)
                        {
                            if (k > 0)
                                sql.append(",");

                            sql.append("'").append(id).append("'");
                            k++;
                        }
                        sql.append(")");
                        WHERE("terminalId in "+sql);
                    }
                }
            }
        }.toString();
    }

    public String update(final Terminal entity) {
        return new SQL() {
            {
                UPDATE("upload_item");
                if (entity != null) {
                    if (entity.getName() != null) {
                    	SET("name = #{name}");
                    }
                    if (entity.getParentId() != null) {
                    	SET("parentId = #{parentId}");
                    }
                    if (entity.getSeeType() != null) {
                    	SET("seeType = #{seeType}");
                    }
                    if (entity.getTerminalId() != null) {
                    	SET("terminalId = #{terminalId}");
                    }
                    if (entity.getEnterpriseId() != null) {
                    	SET("enterpriseId = #{enterpriseId}");
                    }
                    if (entity.getDataCode() != null) {
                    	SET("dataCode = #{dataCode}");
                    }
                    if (entity.getRate() != null) {
                    	SET("rate = #{rate}");
                    }
                    if (entity.getMetering_id() != null) {
                    	SET("metering_id = #{metering_id}");
                    }
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
