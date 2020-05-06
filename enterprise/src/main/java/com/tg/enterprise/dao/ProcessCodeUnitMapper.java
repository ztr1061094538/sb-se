package com.tg.enterprise.dao;

import com.tg.enterprise.model.ProcessCodeUnit;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 生产工序单元表 VO
 *
 * @author panxinchao
 * @Copyright 苏州太谷电力股份有限公司
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier            @date			@Content
 * panxinchao			2018-12-05			新增
 */

public interface ProcessCodeUnitMapper {

    /**
     * 根据主键ID查询
     *
     * @param id 主键ID
     * @author panxinchao
     * @date 2018-12-05
     * @return ProcessCodeUnit
     */
    @Select("select * from process_code_unit where id = #{id}")
    ProcessCodeUnit selectById(@Param("id") Long id);
    
    @Select("select * from process_code_unit where codeUnit =#{codeUnit} and companyId = #{companyId} and cid = #{cid}")
    ProcessCodeUnit selectByCode(@Param("codeUnit") String codeUnit, @Param("companyId") int companyId, @Param("cid") Long cid);

    /**
     * 全查列表
     *
     * @param processCodeUnit 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return List<ProcessCodeUnit>
     */
    @Select("<script>select * from process_code_unit where  1 = 1" +
            "<if test=\"processCodeUnit.id != null \"> AND id = #{processCodeUnit.id}</if>" +
            "<if test=\"processCodeUnit.codeUnit != null and processCodeUnit.codeUnit != '' \"> AND codeUnit = #{processCodeUnit.codeUnit}</if>" +
            "<if test=\"processCodeUnit.nameUnit != null and processCodeUnit.nameUnit != '' \"> AND nameUnit = #{processCodeUnit.nameUnit}</if>" +
            "<if test=\"processCodeUnit.companyId != null \"> AND companyId = #{processCodeUnit.companyId}</if>" +
            "<if test=\"processCodeUnit.cid != null \"> AND cid = #{processCodeUnit.cid}</if>" +
            "<if test=\"processCodeUnit.commDate != null \"> AND commDate = #{processCodeUnit.commDate}</if>" +
            "<if test=\"processCodeUnit.designedCapacity != null and processCodeUnit.designedCapacity != '' \"> AND designedCapacity = #{processCodeUnit.designedCapacity}</if>" +
            "<if test=\"processCodeUnit.remark != null and processCodeUnit.remark != '' \"> AND remark = #{processCodeUnit.remark}</if>" +
            "<if test=\"processCodeUnit.industryCode != null and processCodeUnit.industryCode != '' \"> AND industryCode = #{processCodeUnit.industryCode}</if>" +
            "</script>")
    List<ProcessCodeUnit> selectList(@Param("processCodeUnit") ProcessCodeUnit processCodeUnit);

    /**
     * 条件分页列表
     *
     * @param processCodeUnit 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return List<ProcessCodeUnit>
     */
    @Select("<script>select * from process_code_unit where  1 = 1" +
            "<if test=\"processCodeUnit.id != null \"> AND id = #{processCodeUnit.id}</if>" +
            "<if test=\"processCodeUnit.codeUnit != null and processCodeUnit.codeUnit != '' \"> AND codeUnit = #{processCodeUnit.codeUnit}</if>" +
            "<if test=\"processCodeUnit.nameUnit != null and processCodeUnit.nameUnit != '' \"> AND nameUnit = #{processCodeUnit.nameUnit}</if>" +
            "<if test=\"processCodeUnit.companyId != null \"> AND companyId = #{processCodeUnit.companyId}</if>" +
            "<if test=\"processCodeUnit.cid != null \"> AND cid = #{processCodeUnit.cid}</if>" +
            "<if test=\"processCodeUnit.commDate != null \"> AND commDate = #{processCodeUnit.commDate}</if>" +
            "<if test=\"processCodeUnit.designedCapacity != null and processCodeUnit.designedCapacity != '' \"> AND designedCapacity = #{processCodeUnit.designedCapacity}</if>" +
            "<if test=\"processCodeUnit.remark != null and processCodeUnit.remark != '' \"> AND remark = #{processCodeUnit.remark}</if>" +
            "<if test=\"processCodeUnit.industryCode != null and processCodeUnit.industryCode != '' \"> AND industryCode = #{processCodeUnit.industryCode}</if>" +
            "</script>")
    List<ProcessCodeUnit> selectForPage(@Param("processCodeUnit") ProcessCodeUnit processCodeUnit);

    /**
     * 插入
     *
     * @param processCodeUnit 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return int                        插入成功所影响的行数
     */
    @Insert("insert into process_code_unit" +
            "(codeUnit,nameUnit,companyId,cid,commDate,designedCapacity,remark,industryCode) values " +
            "(#{processCodeUnit.codeUnit}," +
            "#{processCodeUnit.nameUnit}," +
            "#{processCodeUnit.companyId}," +
            "#{processCodeUnit.cid}," +
            "#{processCodeUnit.commDate}," +
            "#{processCodeUnit.designedCapacity}," +
            "#{processCodeUnit.remark}," +
            "#{processCodeUnit.industryCode})")
    int insert(@Param("processCodeUnit") ProcessCodeUnit processCodeUnit);

    /**
     * 批量插入
     *
     * @author panxinchao
     * @date 2018-12-05
     * @param    processCodeUnitList        参数含义
     * @return int                        插入成功所影响的行数
     */
    @Insert("<script>insert into process_code_unit" +
            "( codeUnit, nameUnit, companyId, cid, commDate, designedCapacity, remark, industryCode) values " +
            "<foreach collection='list' item='item' open='(' separator='),(' close=')'>" +
            "#{item.codeUnit}," +
            "#{item.nameUnit}," +
            "#{item.companyId}," +
            "#{item.cid}," +
            "#{item.commDate}," +
            "#{item.designedCapacity}," +
            "#{item.remark}," +
            "#{item.industryCode}</foreach>" +
            "</script>")
    int insertBatch(@Param("list") List<ProcessCodeUnit> processCodeUnitList);

    /**
     * 更新
     *
     * @param processCodeUnit 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return int                        更新成功所影响的行数
     */
    @Update("<script>update process_code_unit" +
            "<trim prefix='set' suffixOverrides=','> " +
            "<if test=\"processCodeUnit.codeUnit != null and processCodeUnit.codeUnit != ''\">codeUnit = #{processCodeUnit.codeUnit},</if>" +
            "<if test=\"processCodeUnit.nameUnit != null and processCodeUnit.nameUnit != ''\">nameUnit = #{processCodeUnit.nameUnit},</if>" +
            "<if test=\"processCodeUnit.companyId != null \">companyId = #{processCodeUnit.companyId},</if>" +
            "<if test=\"processCodeUnit.cid != null \">cid = #{processCodeUnit.cid},</if>" +
            "<if test=\"processCodeUnit.commDate != null \">commDate = #{processCodeUnit.commDate},</if>" +
            "<if test=\"processCodeUnit.designedCapacity != null and processCodeUnit.designedCapacity != ''\">designedCapacity = #{processCodeUnit.designedCapacity},</if>" +
            "<if test=\"processCodeUnit.remark != null and processCodeUnit.remark != ''\">remark = #{processCodeUnit.remark},</if>" +
            "<if test=\"processCodeUnit.industryCode != null and processCodeUnit.industryCode != ''\">industryCode = #{processCodeUnit.industryCode}</if>" +
            "</trim>" +
            "where id = #{processCodeUnit.id} " +
            "</script>")
    int update(@Param("processCodeUnit") ProcessCodeUnit processCodeUnit);

    /**
     * 条件删除
     *
     * @param processCodeUnit 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return int                        删除成功所影响的行数
     */
    @Delete("<script>delete from process_code_unit where  1 = 1" +
            "<if test=\"processCodeUnit.id != null \"> AND id = #{processCodeUnit.id}</if>" +
            "<if test=\"processCodeUnit.codeUnit != null and processCodeUnit.codeUnit != '' \"> AND codeUnit = #{processCodeUnit.codeUnit}</if>" +
            "<if test=\"processCodeUnit.nameUnit != null and processCodeUnit.nameUnit != '' \"> AND nameUnit = #{processCodeUnit.nameUnit}</if>" +
            "<if test=\"processCodeUnit.companyId != null \"> AND companyId = #{processCodeUnit.companyId}</if>" +
            "<if test=\"processCodeUnit.cid != null \"> AND cid = #{processCodeUnit.cid}</if>" +
            "<if test=\"processCodeUnit.commDate != null \"> AND commDate = #{processCodeUnit.commDate}</if>" +
            "<if test=\"processCodeUnit.designedCapacity != null and processCodeUnit.designedCapacity != '' \"> AND designedCapacity = #{processCodeUnit.designedCapacity}</if>" +
            "<if test=\"processCodeUnit.remark != null and processCodeUnit.remark != '' \"> AND remark = #{processCodeUnit.remark}</if>" +
            "<if test=\"processCodeUnit.industryCode != null and processCodeUnit.industryCode != '' \"> AND industryCode = #{processCodeUnit.industryCode}</if>" +
            "</script>")
    int delete(@Param("processCodeUnit") ProcessCodeUnit processCodeUnit);

    /**
     * 根据主键ID删除
     *
     * @param id 主键ID
     * @author panxinchao
     * @date 2018-12-05
     * @return int        删除成功所影响的行数
     */
    @Delete("delete from process_code_unit where id = #{id}")
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除
     *
     * @param list 主键ID
     * @author panxinchao
     * @date 2018-12-05
     * @return int        删除成功所影响的行数
     */
    @Delete("<script>delete from process_code_unit where id in " +
            "<foreach collection='list' item='id' open='(' separator='),(' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int delByIds(@Param("list") List<Long> list);
}