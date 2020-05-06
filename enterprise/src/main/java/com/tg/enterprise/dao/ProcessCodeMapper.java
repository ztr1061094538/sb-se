package com.tg.enterprise.dao;

import com.tg.enterprise.model.ProcessCode;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 生产工序表 VO
 *
 * @author panxinchao
 * @Copyright 苏州太谷电力股份有限公司
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier            @date			@Content
 * panxinchao			2018-12-05			新增
 */

public interface ProcessCodeMapper {

    /**
     * 根据主键ID查询
     *
     * @param id 主键ID
     * @author panxinchao
     * @date 2018-12-05
     * @return ProcessCode
     */
    @Select("select * from process_code where id = #{id}")
    ProcessCode selectById(@Param("id") Long id);
    
    @Select("select * from process_code where code = #{code} and companyId = #{companyId}")
    ProcessCode selectByCode(@Param("code") String code, @Param("companyId") int companyId);

    /**
     * 全查列表
     *
     * @param processCode 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return List<ProcessCode>
     */
    @Select("<script>select * from process_code where  1 = 1" +
            "<if test=\"processCode.id != null \"> AND id = #{processCode.id}</if>" +
            "<if test=\"processCode.code != null and processCode.code != '' \"> AND code = #{processCode.code}</if>" +
            "<if test=\"processCode.name != null and processCode.name != '' \"> AND name = #{processCode.name}</if>" +
            "<if test=\"processCode.companyId != null \"> AND companyId = #{processCode.companyId}</if>" +
            "<if test=\"processCode.remark != null and processCode.remark != '' \"> AND remark = #{processCode.remark}</if>" +
            "<if test=\"processCode.industryCode != null and processCode.industryCode != '' \"> AND industryCode = #{processCode.industryCode}</if>" +
            "</script>")
    List<ProcessCode> selectList(@Param("processCode") ProcessCode processCode);

    /**
     * 条件分页列表
     *
     * @param processCode 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return List<ProcessCode>
     */
    @Select("<script>select * from process_code where  1 = 1" +
            "<if test=\"processCode.id != null \"> AND id = #{processCode.id}</if>" +
            "<if test=\"processCode.code != null and processCode.code != '' \"> AND code = #{processCode.code}</if>" +
            "<if test=\"processCode.name != null and processCode.name != '' \"> AND name = #{processCode.name}</if>" +
            "<if test=\"processCode.companyId != null \"> AND companyId = #{processCode.companyId}</if>" +
            "<if test=\"processCode.remark != null and processCode.remark != '' \"> AND remark = #{processCode.remark}</if>" +
            "<if test=\"processCode.industryCode != null and processCode.industryCode != '' \"> AND industryCode = #{processCode.industryCode}</if>" +
            "</script>")
    List<ProcessCode> selectForPage(@Param("processCode") ProcessCode processCode);

    /**
     * 插入
     *
     * @param processCode 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return int                        插入成功所影响的行数
     */
    @Insert("insert into process_code" +
            "(code,name,companyId,remark,industryCode) values " +
            "(#{processCode.code}," +
            "#{processCode.name}," +
            "#{processCode.companyId}," +
            "#{processCode.remark}," +
            "#{processCode.industryCode})")
    int insert(@Param("processCode") ProcessCode processCode);

    /**
     * 批量插入
     *
     * @author panxinchao
     * @date 2018-12-05
     * @param    processCodeList        参数含义
     * @return int                        插入成功所影响的行数
     */
    @Insert("<script>insert into process_code" +
            "( code, name, companyId, remark, industryCode) values " +
            "<foreach collection='list' item='item' open='(' separator='),(' close=')'>" +
            "#{item.code}," +
            "#{item.name}," +
            "#{item.companyId}," +
            "#{item.remark}," +
            "#{item.industryCode}</foreach>" +
            "</script>")
    int insertBatch(@Param("list") List<ProcessCode> processCodeList);

    /**
     * 更新
     *
     * @param processCode 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return int                        更新成功所影响的行数
     */
    @Update("<script>update process_code" +
            "<trim prefix='set' suffixOverrides=','> " +
            "<if test=\"processCode.code != null\">code = #{processCode.code},</if>" +
            "<if test=\"processCode.name != null\">name = #{processCode.name},</if>" +
            "<if test=\"processCode.companyId != null \">companyId = #{processCode.companyId},</if>" +
            "<if test=\"processCode.remark != null\">remark = #{processCode.remark},</if>" +
            "<if test=\"processCode.industryCode != null\">industryCode = #{processCode.industryCode}</if>" +
            "</trim>" +
            "where id = #{processCode.id} " +
            "</script>")
    int update(@Param("processCode") ProcessCode processCode);

    /**
     * 条件删除
     *
     * @param processCode 参数含义
     * @author panxinchao
     * @date 2018-12-05
     * @return int                        删除成功所影响的行数
     */
    @Delete("<script>delete from process_code where  1 = 1" +
            "<if test=\"processCode.id != null \"> AND id = #{processCode.id}</if>" +
            "<if test=\"processCode.code != null and processCode.code != '' \"> AND code = #{processCode.code}</if>" +
            "<if test=\"processCode.name != null and processCode.name != '' \"> AND name = #{processCode.name}</if>" +
            "<if test=\"processCode.companyId != null \"> AND companyId = #{processCode.companyId}</if>" +
            "<if test=\"processCode.remark != null and processCode.remark != '' \"> AND remark = #{processCode.remark}</if>" +
            "<if test=\"processCode.industryCode != null and processCode.industryCode != '' \"> AND industryCode = #{processCode.industryCode}</if>" +
            "</script>")
    int delete(@Param("processCode") ProcessCode processCode);

    /**
     * 根据主键ID删除
     *
     * @param id 主键ID
     * @author panxinchao
     * @date 2018-12-05
     * @return int        删除成功所影响的行数
     */
    @Delete("delete from process_code where id = #{id}")
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除
     *
     * @param list 主键ID
     * @author panxinchao
     * @date 2018-12-05
     * @return int        删除成功所影响的行数
     */
    @Delete("<script>delete from process_code where id in " +
            "<foreach collection='list' item='id' open='(' separator='),(' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int delByIds(@Param("list") List<Long> list);
}