package com.tg.enterprise.dao.provider;

import com.tg.enterprise.util.AnalysisEntityEnum;
import com.tg.enterprise.util.AnalysisTypeEnum;
import com.tg.enterprise.util.EnergyTypeRouter;
import com.tg.enterprise.util.SQLUtils;
import com.tg.enterprise.vo.AnalysisTerminalQueryVO;
import org.apache.ibatis.jdbc.SQL;

public class AnalysisTerminalMapperProvider 
{
	public String  getTerminalDataByDate(AnalysisTerminalQueryVO queryVO)
	{
		return new SQL(){
			{
				SELECT("*");
				String tablename = EnergyTypeRouter.getAnalysisTableName(queryVO.getDataCode(), AnalysisTypeEnum.DATE, AnalysisEntityEnum.TERMINAL);
				FROM(tablename);
				WHERE("upload_date = #{uploadTime}");
				if (!queryVO.getTerminalIds().isEmpty())
				{
					WHERE(SQLUtils.getInSql("terminal_id", queryVO.getTerminalIds()));
				}
			}
		}.toString() ;
	}
}
