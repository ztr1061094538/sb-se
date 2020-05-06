package com.tg.enterprise.biz;

import com.tg.enterprise.model.Device;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * 设备档案 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-14
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-14			新增
 */ 
public interface IDeviceBiz
{
	/**
	 * 根据主键ID查询	
	 */
	Device selectById(Integer id);

	/**
	 * 全查列表	
	 */
	List<Device> selectList(Device device);

	/**
	 * 条件分页列表
	 */
	PageInfo<Device> selectForPage(Device device, Integer offset, Integer count);

	/**
	 * 插入
	 */
	int insert(Device device);

	/**
	 * 批量插入
	 */
	int insertBatch(List<Device> deviceList);

	/**
	 * 更新		
	 */
	int update(Device device);

	/**
	 * 条件删除		
	 */
	int delete(Device device);

	/**
	 * 根据主键ID删除	
	 */
	int deleteById(Integer id);
	
	/**
	 * 根据主键ID删除		
	 */
	int delByIds(List<Integer> ids);
}