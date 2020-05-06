package com.tg.enterprise.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.Device;
import com.tg.enterprise.biz.IDeviceBiz;
import com.tg.enterprise.dao.DeviceMapper;
import java.util.List;


/**
 * 设备档案 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-14
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-14			新增
 */ 
@Service("DeviceBizImpl")
public class DeviceBizImpl implements IDeviceBiz
{
	
	@Autowired
	private DeviceMapper deviceMapper;
	
	/**
	 * 根据主键ID查询		
	 */
	@Override
	public Device selectById(Integer id)
	{
		return deviceMapper.selectById(id);
	}

	/**
	 * 全查列表	
	 */
	@Override
	public List<Device> selectList(Device device)
	{
		return deviceMapper.selectList(device);
	}
	
	/**
	 * 条件分页列表
	 */
	@Override
	public PageInfo<Device> selectForPage(Device device, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(deviceMapper.selectList(device));
	}

	/**
	 * 插入
	 */
	@Override
	public int insert(Device device)
	{
		return deviceMapper.insert(device);
	}

	/**
	 * 批量插入
	 */
	@Override
	public int insertBatch(List<Device> deviceList)
	{
		return deviceMapper.insertBatch(deviceList);
	}

	/**
	 * 更新		
	 */
	@Override
	public int update(Device device)
	{
		return deviceMapper.update(device);
	}

	/**
	 * 条件删除		
	 */
	@Override
	public int delete(Device device)
	{
		return deviceMapper.delete(device);
	}
	
	/**
	 * 根据主键删除		
	 */
	@Override
	public int deleteById(Integer id)
	{
		return deviceMapper.deleteById(id);
	}
	
	
	/**
     * 批量删除
     */
    @Override
    public int delByIds(List<Integer> ids)
	{
        return deviceMapper.delByIds(ids);
    }
}