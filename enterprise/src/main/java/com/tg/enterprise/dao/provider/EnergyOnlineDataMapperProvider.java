package com.tg.enterprise.dao.provider;

import com.tg.enterprise.util.EnergyDataCode;
import com.tg.enterprise.util.EnergyTypeRouter;
import com.tg.enterprise.vo.EnergyOnlineDataQueryVO;
import org.apache.ibatis.jdbc.SQL;

public class EnergyOnlineDataMapperProvider
{
	public String query(final EnergyOnlineDataQueryVO queryVO) throws IllegalAccessException
	{
		return new SQL()
		{
			{
				SELECT("*");
				EnergyDataCode dataCode = EnergyDataCode.parse(queryVO.getEnergyCode());
				FROM(EnergyTypeRouter.getOnlineTableName(dataCode));
				if (queryVO.getTerminalId() != null)
				{
					WHERE("terminal_id = #{terminalId}");
				}
				if (queryVO.getUploadDate() == null)
				{
					WHERE("upload_date between #{startTime} and #{endTime}");
				}
				else
				{
					WHERE("upload_date = #{upload_date}");
				}
				if(queryVO.getIds()!=null&&!queryVO.getIds().isEmpty()) {
					StringBuilder sb = new StringBuilder("terminal_id IN ('");
					sb.append(String.join("','", queryVO.getIds())).append("')");
					WHERE(sb.toString());
				}
				
				if(queryVO.getOrderName()!=null && queryVO.getOrderType()!=null) {
					ORDER_BY("#{orderName} #{orderType}");
				}
			}
		}.toString();
	}
	
	/**
	 * 查出一段时间每个监测点最新的记录
	 * @param queryVO
	 * @return
	 * @throws IllegalAccessException
	 */
	public String queryNewestData(final EnergyOnlineDataQueryVO queryVO) throws IllegalAccessException
	{
		return new SQL()
		{
			{
				SELECT("*");
				EnergyDataCode dataCode = EnergyDataCode.parse(queryVO.getEnergyCode());
				FROM(EnergyTypeRouter.getOnlineTableName(dataCode));
				if (queryVO.getTerminalId() != null)
				{
					WHERE("terminal_id = #{terminalId}");
				}
				if (queryVO.getUploadDate() == null)
				{
					WHERE("upload_date between #{startTime} and #{endTime}");
				}
				else
				{
					WHERE("upload_date = #{uploadDate}");
				}
				if(queryVO.getIds()!=null&&!queryVO.getIds().isEmpty()) {
					StringBuilder sb = new StringBuilder("terminal_id IN ('");
					sb.append(String.join("','", queryVO.getIds())).append("')");
					WHERE(sb.toString());
				}
				
				ORDER_BY("terminal_id,upload_date desc");
			}
		}.toString();
	}
}
