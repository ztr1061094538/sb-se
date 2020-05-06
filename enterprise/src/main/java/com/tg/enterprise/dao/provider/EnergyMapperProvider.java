package com.tg.enterprise.dao.provider;

import com.tg.enterprise.util.SQLUtils;
import com.tg.enterprise.vo.EnergyQueryVO;
import lombok.NonNull;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/16.
 */
public class EnergyMapperProvider {

    public String queryListByUploadDate(@NonNull final EnergyQueryVO queryVO)
    {
        return new SQL() {
            {
                SELECT("*");
                FROM(queryVO.getTableName());
                if (queryVO.getUpload_date() != null)
                {
                    WHERE("upload_date = #{upload_date} ");
                }

                if(queryVO.getTerminalIdSet() != null && !queryVO.getTerminalIdSet().isEmpty()) {
                    WHERE(SQLUtils.getInSql("enterprise_id", new ArrayList<>(queryVO.getTerminalIdSet())));
                }
                if (queryVO.getStartDate() != null && queryVO.getEndDate() != null)
                {
                	WHERE("upload_date >= #{startDate} and upload_date <=#{endDate}");
                }
                ORDER_BY("upload_date asc");
            }
        }.toString();
    }

    public String getSumTerminalConsumption(@NonNull final EnergyQueryVO queryVO)
    {
        return new SQL() {
            {
                SELECT("sum(data_value) as data_value");
                FROM(queryVO.getTableName());
                if (queryVO.getStartDate() != null && queryVO.getEndDate() != null)
                {
                	WHERE("upload_date >= #{startDate} and upload_date <=#{endDate}");
                }
                if(queryVO.getTerminalIdSet() != null && !queryVO.getTerminalIdSet().isEmpty()) {
                    WHERE(SQLUtils.getInSql("terminal_id", new ArrayList<>(queryVO.getTerminalIdSet())));
                    /*StringBuilder sb = new StringBuilder("terminal_id in (");
                    sb.append(String.join(",", queryVO.getTerminalIdSet())).append(")");
                    WHERE(sb.toString());*/
                }
            }
        }.toString();
    }
}
