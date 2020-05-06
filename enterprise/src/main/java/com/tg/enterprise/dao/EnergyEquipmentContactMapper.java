package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.EnergyEquipmentContactProvider;
import com.tg.enterprise.model.EnergyEquipmentContact;
import com.tg.enterprise.vo.EnergyEquipmentContactVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用能设备采集数据关联定义列表 VO
 *
 * @author panxinchao
 * @Copyright 苏州太谷电力股份有限公司
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier            @date			@Content
 * panxinchao			2018-12-05			新增
 */

public interface EnergyEquipmentContactMapper {

    /**
     * 根据主键ID查询
     *
     * @param id 主键ID
     * @author panxinchao
     * @date 2018-12-05
     * @return EnergyEquipmentContact
     */
    @Select("select * from energy_equipment_contact where equipmentCode = #{equipmentCode} and companyId = #{companyId}")
    EnergyEquipmentContact selectByCode(@Param("equipmentCode") String equipmentCode, @Param("companyId") int companyId);

    /**
     * 全查列表
     *
     * @param energyEquipmentContact 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return List<EnergyEquipmentContact>
     */
    @Select("<script>select * from energy_equipment_contact where  1 = 1" +
            "<if test=\"energyEquipmentContact.id != null \"> AND id = #{energyEquipmentContact.id}</if>" +
            "<if test=\"energyEquipmentContact.equipmentCode != null and energyEquipmentContact.equipmentCode != '' \"> AND equipmentCode = #{energyEquipmentContact.equipmentCode}</if>" +
            "<if test=\"energyEquipmentContact.equipmentId != null and energyEquipmentContact.equipmentId != '' \"> AND equipmentId = #{energyEquipmentContact.equipmentId}</if>" +
            "<if test=\"energyEquipmentContact.companyId != null \"> AND companyId = #{energyEquipmentContact.companyId}</if>" +
            "<if test=\"energyEquipmentContact.remark != null and energyEquipmentContact.remark != '' \"> AND remark = #{energyEquipmentContact.remark}</if>" +
            "<if test=\"energyEquipmentContact.code != null and energyEquipmentContact.code != '' \"> AND code = #{energyEquipmentContact.code}</if>" +
            "<if test=\"energyEquipmentContact.code_unit != null and energyEquipmentContact.code_unit != '' \"> AND code_unit = #{energyEquipmentContact.code_unit}</if>" +
            "<if test=\"energyEquipmentContact.collectType != null and energyEquipmentContact.collectType != '' \"> AND collectType = #{energyEquipmentContact.collectType}</if>" +
            "<if test=\"energyEquipmentContact.category != null and energyEquipmentContact.category != '' \"> AND category = #{energyEquipmentContact.category}</if>" +
            "<if test=\"energyEquipmentContact.energyUse != null and energyEquipmentContact.energyUse != '' \"> AND energyUse = #{energyEquipmentContact.energyUse}</if>" +
            "<if test=\"energyEquipmentContact.industryCode != null and energyEquipmentContact.industryCode != '' \"> AND industryCode = #{energyEquipmentContact.industryCode}</if>" +
            "<if test=\"energyEquipmentContact.inputType != null and energyEquipmentContact.inputType != '' \"> AND inputType = #{energyEquipmentContact.inputType}</if>" +
            "<if test=\"energyEquipmentContact.terminalId != null and energyEquipmentContact.terminalId != '' \"> AND terminalId = #{energyEquipmentContact.terminalId}</if>" +
            "</script>")
    List<EnergyEquipmentContact> selectList(@Param("energyEquipmentContact") EnergyEquipmentContact energyEquipmentContact);

    /**
     * 条件分页列表
     *
     * @param energyEquipmentContact 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return List<EnergyEquipmentContact>
     */
    @Select("<script>select * from energy_equipment_contact where  1 = 1" +
            "<if test=\"energyEquipmentContact.id != null \"> AND id = #{energyEquipmentContact.id}</if>" +
            "<if test=\"energyEquipmentContact.equipmentCode != null and energyEquipmentContact.equipmentCode != '' \"> AND equipmentCode = #{energyEquipmentContact.equipmentCode}</if>" +
            "<if test=\"energyEquipmentContact.equipmentId != null and energyEquipmentContact.equipmentId != '' \"> AND equipmentId = #{energyEquipmentContact.equipmentId}</if>" +
            "<if test=\"energyEquipmentContact.companyId != null \"> AND companyId = #{energyEquipmentContact.companyId}</if>" +
            "<if test=\"energyEquipmentContact.remark != null and energyEquipmentContact.remark != '' \"> AND remark = #{energyEquipmentContact.remark}</if>" +
            "<if test=\"energyEquipmentContact.code != null and energyEquipmentContact.code != '' \"> AND code = #{energyEquipmentContact.code}</if>" +
            "<if test=\"energyEquipmentContact.code_unit != null and energyEquipmentContact.code_unit != '' \"> AND code_unit = #{energyEquipmentContact.code_unit}</if>" +
            "<if test=\"energyEquipmentContact.collectType != null and energyEquipmentContact.collectType != '' \"> AND collectType = #{energyEquipmentContact.collectType}</if>" +
            "<if test=\"energyEquipmentContact.category != null and energyEquipmentContact.category != '' \"> AND category = #{energyEquipmentContact.category}</if>" +
            "<if test=\"energyEquipmentContact.energyUse != null and energyEquipmentContact.energyUse != '' \"> AND energyUse = #{energyEquipmentContact.energyUse}</if>" +
            "<if test=\"energyEquipmentContact.industryCode != null and energyEquipmentContact.industryCode != '' \"> AND industryCode = #{energyEquipmentContact.industryCode}</if>" +
            "<if test=\"energyEquipmentContact.inputType != null and energyEquipmentContact.inputType != '' \"> AND inputType = #{energyEquipmentContact.inputType}</if>" +
            "<if test=\"energyEquipmentContact.terminalId != null and energyEquipmentContact.terminalId != '' \"> AND terminalId = #{energyEquipmentContact.terminalId}</if>" +
            "</script>")
    List<EnergyEquipmentContactVO> selectForPage(@Param("energyEquipmentContact") EnergyEquipmentContactVO energyEquipmentContact);

    /**
     * 插入
     *
     * @param energyEquipmentContact 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return int                        插入成功所影响的行数
     */
    @Insert("insert into energy_equipment_contact" +
            "(equipmentCode,equipmentId,companyId,remark,code,code_unit,collectType,category,energyUse,industryCode,inputType,terminalId,name,statType) values " +
            "(#{energyEquipmentContact.equipmentCode}," +
            "#{energyEquipmentContact.equipmentId}," +
            "#{energyEquipmentContact.companyId}," +
            "#{energyEquipmentContact.remark}," +
            "#{energyEquipmentContact.code}," +
            "#{energyEquipmentContact.code_unit}," +
            "#{energyEquipmentContact.collectType}," +
            "#{energyEquipmentContact.category}," +
            "#{energyEquipmentContact.energyUse}," +
            "#{energyEquipmentContact.industryCode},"+
            "#{energyEquipmentContact.inputType},"+
            "#{energyEquipmentContact.terminalId},"+
            "#{energyEquipmentContact.name},"
            + "#{energyEquipmentContact.statType})")
    int insert(@Param("energyEquipmentContact") EnergyEquipmentContact energyEquipmentContact);

    /**
     * 批量插入
     *
     * @author panxinchao
     * @date 2018-12-05
     * @param    energyEquipmentContactList        参数含义
     * @return int                        插入成功所影响的行数
     */
    @Insert("<script>insert into energy_equipment_contact" +
            "( equipmentCode, equipmentId, companyId, remark, code, code_unit, collectType, category, energyUse, industryCode, inputType, terminalId,name) values " +
            "<foreach collection='list' item='item' open='(' separator='),(' close=')'>" +
            "#{item.equipmentcode}," +
            "#{item.equipmentId}," +
            "#{item.companyId}," +
            "#{item.remark}," +
            "#{item.code}," +
            "#{item.code_unit}," +
            "#{item.collectType}," +
            "#{item.category}," +
            "#{item.energyUse}," +
            "#{item.industryCode}," +
            "#{item.inputType}," +
            "#{item.terminalId}," +
            "#{item.name}</foreach>" +
            "</script>")
    int insertBatch(@Param("list") List<EnergyEquipmentContact> energyEquipmentContactList);

    /**
     * 更新
     *
     * @param energyEquipmentContact 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return int                        更新成功所影响的行数
     */
    @Update("<script>update energy_equipment_contact" +
            "<trim prefix='set' suffixOverrides=','> " +
            "<if test=\"energyEquipmentContact.equipmentCode != null and energyEquipmentContact.equipmentCode != '' \">equipmentCode = #{energyEquipmentContact.equipmentCode},</if>" +
            "<if test=\"energyEquipmentContact.equipmentId != null and energyEquipmentContact.equipmentId != '' \">equipmentId = #{energyEquipmentContact.equipmentId},</if>" +
            "<if test=\"energyEquipmentContact.remark != null and energyEquipmentContact.remark != '' \">remark = #{energyEquipmentContact.remark},</if>" +
            "<if test=\"energyEquipmentContact.code != null and energyEquipmentContact.code != '' \">code = #{energyEquipmentContact.code},</if>" +
            "<if test=\"energyEquipmentContact.code_unit != null and energyEquipmentContact.code_unit != '' \">code_unit = #{energyEquipmentContact.code_unit},</if>" +
            "<if test=\"energyEquipmentContact.collectType != null and energyEquipmentContact.collectType != '' \">collectType = #{energyEquipmentContact.collectType},</if>" +
            "<if test=\"energyEquipmentContact.category != null and energyEquipmentContact.category != '' \">category = #{energyEquipmentContact.category},</if>" +
            "<if test=\"energyEquipmentContact.energyUse != null and energyEquipmentContact.energyUse != '' \">energyUse = #{energyEquipmentContact.energyUse},</if>" +
            "<if test=\"energyEquipmentContact.industryCode != null and energyEquipmentContact.industryCode != '' \">industryCode = #{energyEquipmentContact.industryCode},</if>" +
            "<if test=\"energyEquipmentContact.inputType != null and energyEquipmentContact.inputType != '' \">inputType = #{energyEquipmentContact.inputType},</if>" +
            "<if test=\"energyEquipmentContact.terminalId != null \">terminalId = #{energyEquipmentContact.terminalId},</if>" +
            "<if test=\"energyEquipmentContact.name != null and energyEquipmentContact.name != '' \">name = #{energyEquipmentContact.name},</if>" +
            "<if test=\"energyEquipmentContact.statType != null\">statType = #{energyEquipmentContact.statType}</if>" +
            "</trim>" +
            "where id = #{energyEquipmentContact.id} and companyId = #{energyEquipmentContact.companyId}" +
            "</script>")
    int update(@Param("energyEquipmentContact") EnergyEquipmentContact energyEquipmentContact);

    /**
     * 条件删除
     *
     * @param energyEquipmentContact 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return int                        删除成功所影响的行数
     */
    @Delete("<script>delete from energy_equipment_contact where  1 = 1" +
            "<if test=\"energyEquipmentContact.id != null \"> AND id = #{energyEquipmentContact.id}</if>" +
            "<if test=\"energyEquipmentContact.equipmentCode != null and energyEquipmentContact.equipmentCode != '' \"> AND equipmentCode = #{energyEquipmentContact.equipmentCode}</if>" +
            "<if test=\"energyEquipmentContact.equipmentId != null and energyEquipmentContact.equipmentId != '' \"> AND equipmentId = #{energyEquipmentContact.equipmentId}</if>" +
            "<if test=\"energyEquipmentContact.companyId != null \"> AND companyId = #{energyEquipmentContact.companyId}</if>" +
            "<if test=\"energyEquipmentContact.remark != null and energyEquipmentContact.remark != '' \"> AND remark = #{energyEquipmentContact.remark}</if>" +
            "<if test=\"energyEquipmentContact.code != null and energyEquipmentContact.code != '' \"> AND code = #{energyEquipmentContact.code}</if>" +
            "<if test=\"energyEquipmentContact.code_unit != null and energyEquipmentContact.code_unit != '' \"> AND code_unit = #{energyEquipmentContact.code_unit}</if>" +
            "<if test=\"energyEquipmentContact.collectType != null and energyEquipmentContact.collectType != '' \"> AND collectType = #{energyEquipmentContact.collectType}</if>" +
            "<if test=\"energyEquipmentContact.category != null and energyEquipmentContact.category != '' \"> AND category = #{energyEquipmentContact.category}</if>" +
            "<if test=\"energyEquipmentContact.energyUse != null and energyEquipmentContact.energyUse != '' \"> AND energyUse = #{energyEquipmentContact.energyUse}</if>" +
            "<if test=\"energyEquipmentContact.industryCode != null and energyEquipmentContact.industryCode != '' \"> AND industryCode = #{energyEquipmentContact.industryCode}</if>" +
            "<if test=\"energyEquipmentContact.inputType != null and energyEquipmentContact.inputType != '' \"> AND inputType = #{energyEquipmentContact.inputType}</if>" +
            "<if test=\"energyEquipmentContact.terminalId != null and energyEquipmentContact.terminalId != '' \"> AND terminalId = #{energyEquipmentContact.terminalId}</if>" +
            "</script>")
    int delete(@Param("energyEquipmentContact") EnergyEquipmentContact energyEquipmentContact);

    /**
     * 根据主键ID删除
     *
     * @param id 主键ID
     * @author panxinchao
     * @date 2018-12-05
     * @return int        删除成功所影响的行数
     */
    @Delete("delete from energy_equipment_contact where id = #{id}")
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除
     *
     * @param list 主键ID
     * @author panxinchao
     * @date 2018-12-05
     * @return int        删除成功所影响的行数
     */
    @Delete("<script>delete from energy_equipment_contact where id in " +
            "<foreach collection='list' item='id' open='(' separator='),(' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int delByIds(@Param("list") List<Long> list);

    @SelectProvider(type = EnergyEquipmentContactProvider.class, method = "selectList1")
    List<EnergyEquipmentContact> selectList1(EnergyEquipmentContact energyEquipmentContact);
}