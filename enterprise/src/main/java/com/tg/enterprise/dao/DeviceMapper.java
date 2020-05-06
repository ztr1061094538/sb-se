package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.DeviceProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import com.tg.enterprise.model.Device;
import java.util.List;

/**
 * 设备档案 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-14
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-14			新增
 */ 

public interface DeviceMapper{
	
	/**
	 * 根据主键ID查询
	 */
	@Select("select * from t_device where id = #{id}")
	Device selectById(@Param("id") Integer id);
	
	/**
	 * 全查列表
	 */
	@SelectProvider(type = DeviceProvider.class, method = "selectList")
	List<Device> selectList(Device device);
	
	/**
	 * 插入		
	 */
	@Insert("insert into t_device" + 
			"(enterprise_id,device_num,name,type,address,begin_date,state) values " +
			"(#{device.enterprise_id}," +
			"#{device.device_num}," +
			"#{device.name}," +
			"#{device.type}," +
			"#{device.address}," +
			"#{device.begin_date}," +
			"#{device.state})")
	int insert(@Param("device") Device device);
	
	/**
	 * 批量插入
	 */
	@Insert("<script>insert into t_device" + 
			"(enterprise_id, device_num, name, type, address, begin_date, state) values " +
			"<foreach collection='list' item='item' open='(' separator='),(' close=')'>" + 
			"#{item.enterprise_id}," +
			"#{item.device_num}," +
			"#{item.name}," +
			"#{item.type}," +
			"#{item.address}," +
			"#{item.begin_date}," +
			"#{item.state}</foreach>" + 
			"</script>")
	int insertBatch(@Param("list") List<Device> deviceList);
	
	/**
	 * 更新		
	 */
	@UpdateProvider(type = DeviceProvider.class,method= "update")
	int update(Device device);

	/**
	 * 条件删除	
	 */
    @DeleteProvider(type = DeviceProvider.class, method = "delete")
	int delete(Device device);

	/**
	 * 根据主键ID删除		
	 */
	@Delete("delete from t_device where id = #{id}")
	int deleteById(@Param("id") Integer id);
	
	/**
	 * 批量删除		
	 */
	@Delete("<script>delete from t_device where id in " +
			"<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
			"#{id}" +
			"</foreach>" +
			"</script>")
	int delByIds(@Param("list") List<Integer> list);
}