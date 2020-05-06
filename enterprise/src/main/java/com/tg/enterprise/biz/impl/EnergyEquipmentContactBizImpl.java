package com.tg.enterprise.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnergyEquipmentContactBiz;
import com.tg.enterprise.dao.EnergyEquipmentContactMapper;
import com.tg.enterprise.model.EnergyEquipmentContact;
import com.tg.enterprise.vo.EnergyEquipmentContactVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 用能设备采集数据关联定义列表 Dao接口实现 
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
@Service("EnergyEquipmentContactBizImpl")
public class EnergyEquipmentContactBizImpl implements IEnergyEquipmentContactBiz
{
	
	@Resource
	private EnergyEquipmentContactMapper energyEquipmentContactMapper;
	
	/**
	 * 根据主键ID查询
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	EnergyEquipmentContact		
	 */
	@Override
	public EnergyEquipmentContact selectByCode(String equipmentCode, int companyId)
	{
		return energyEquipmentContactMapper.selectByCode(equipmentCode,companyId);
	}

	/**
	 * 全查列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	energyEquipmentContact		参数含义
	 * @return	List<EnergyEquipmentContact>		
	 */
	@Override
	public List<EnergyEquipmentContact> selectList(EnergyEquipmentContact energyEquipmentContact)
	{
		return energyEquipmentContactMapper.selectList(energyEquipmentContact);
	}
	
	/**
	 * 条件分页列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	page					分页对象
	 * @return	Page<EnergyEquipmentContact>		
	 */
	@Override
	public PageInfo<EnergyEquipmentContactVO> selectForPage(EnergyEquipmentContactVO energyEquipmentContact, Integer offset, Integer count)
	{
		PageHelper.startPage(offset, count, true);
		return new PageInfo<>(energyEquipmentContactMapper.selectForPage(energyEquipmentContact));
	}

	/**
	 * 插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	energyEquipmentContact		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	@Override
	public int insert(EnergyEquipmentContact energyEquipmentContact)
	{
		return energyEquipmentContactMapper.insert(energyEquipmentContact);
	}

	/**
	 * 批量插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param	energyEquipmentContactList		参数含义
	 */
	@Override
	public int insertBatch(List<EnergyEquipmentContact> energyEquipmentContactList)
	{
		return energyEquipmentContactMapper.insertBatch(energyEquipmentContactList);
	}

	/**
	 * 更新
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	energyEquipmentContact		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	@Override
	public int update(EnergyEquipmentContact energyEquipmentContact)
	{
		return energyEquipmentContactMapper.update(energyEquipmentContact);
	}

	/**
	 * 条件删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	energyEquipmentContact		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	@Override
	public int delete(EnergyEquipmentContact energyEquipmentContact)
	{
		return energyEquipmentContactMapper.delete(energyEquipmentContact);
	}
	
	/**
	 * 根据主键ID删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	@Override
	public int deleteById(Long id)
	{
		return energyEquipmentContactMapper.deleteById(id);
	}
	
	
	/**
     * 根据主键ID批量删除
     * @author panxinchao 
	 * @date 2018-12-05
     * @param 	ids		主键ID
     * @return	int		删除成功所影响的行数
     */
    @Override
    public int delByIds(List<Long> ids)
	{
        return energyEquipmentContactMapper.delByIds(ids);
    }

	@Override
	public List<EnergyEquipmentContact> selectList1(EnergyEquipmentContact energyEquipmentContact) {
		return energyEquipmentContactMapper.selectList1(energyEquipmentContact);
	}
}