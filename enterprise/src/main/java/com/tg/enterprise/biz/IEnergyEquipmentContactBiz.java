package com.tg.enterprise.biz;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.model.EnergyEquipmentContact;
import com.tg.enterprise.vo.EnergyEquipmentContactVO;

import java.util.List;

/**
 * 用能设备采集数据关联定义列表 Dao接口 
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
public interface IEnergyEquipmentContactBiz
{
	/**
	 * 根据主键ID查询
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id				主键ID	
	 * @return	EnergyEquipmentContact		
	 */
	EnergyEquipmentContact selectByCode(String equipmentCode, int companyId);

	/**
	 * 全查列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	energyEquipmentContact		参数含义
	 * @return	List<EnergyEquipmentContact>		
	 */
	List<EnergyEquipmentContact> selectList(EnergyEquipmentContact energyEquipmentContact);

	/**
	 * 条件分页列表
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	page					分页对象
	 * @return	Page<EnergyEquipmentContact>		
	 */
	PageInfo<EnergyEquipmentContactVO> selectForPage(EnergyEquipmentContactVO energyEquipmentContact, Integer offset, Integer count);

	/**
	 * 插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	energyEquipmentContact		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	int insert(EnergyEquipmentContact energyEquipmentContact);

	/**
	 * 批量插入
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param	energyEquipmentContactList		参数含义
	 */
	int insertBatch(List<EnergyEquipmentContact> energyEquipmentContactList);

	/**
	 * 更新
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	energyEquipmentContact		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	int update(EnergyEquipmentContact energyEquipmentContact);

	/**
	 * 条件删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	energyEquipmentContact		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	int delete(EnergyEquipmentContact energyEquipmentContact);

	/**
	 * 根据主键ID删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	int deleteById(Long id);
	
	/**
	 * 根据主键ID删除
	 * @author panxinchao 
	 * @date 2018-12-05
	 * @param 	ids		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	int delByIds(List<Long> ids);

    List<EnergyEquipmentContact> selectList1(EnergyEquipmentContact energyEquipmentContact);
}