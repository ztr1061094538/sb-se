package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.TerminalProvider;
import com.tg.enterprise.model.Terminal;
import com.tg.enterprise.vo.TerminalReqVo;
import com.tg.enterprise.vo.TerminalVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TerminalMapper {
	@SelectProvider(type = TerminalProvider.class, method = "queryPageList")
	public List<Terminal> selectBySelective(TerminalReqVo bean);

	@Select("select * from upload_item where id = #{id}")
	public Terminal selectById(@Param("id") Integer id);

	@Select("delete from upload_item where id = #{id}")
	public int delete(@Param("id") Integer id);

	@Delete("<script>delete from  upload_item  where id in "
			+ "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" + "#{item}"
			+ "</foreach>"
			+ "</script>")
	public int deletes(@Param("ids") List<Integer> ids);

	@Insert("insert into upload_item(parentId,name,terminalId,enterpriseId,dataCode,rate,seeType,metering_id) " + 
			"values(#{bean.parentId},#{bean.name},#{bean.terminalId},#{bean.enterpriseId},#{bean.dataCode},#{bean.rate},#{bean.seeType},#{bean.metering_id})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()",keyProperty="bean.id",before=false,resultType=Integer.class)
	public int insert(@Param("bean") Terminal bean);

	@UpdateProvider(type = TerminalProvider.class, method = "update")
	public int update(Terminal bean);

	@SelectProvider(type = TerminalProvider.class, method = "selectList")
	List<TerminalVo> selectList(TerminalReqVo param);
}
