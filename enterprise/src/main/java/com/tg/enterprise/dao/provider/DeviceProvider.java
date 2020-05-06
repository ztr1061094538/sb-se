package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.Device;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * 设备档案 Provider
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-14
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-14			新增
 */ 
public class DeviceProvider {

	/**
	 * 全查列表
	 */
    public String selectList(final Device device) {
        return new SQL() {
            {
                SELECT("*");
                FROM("t_device");
                if (device != null) {
                    if (device.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (device.getEnterprise_id() != null) {
                        WHERE("enterprise_id = #{enterprise_id}");
                    }
                    if (StringUtils.isNotBlank(device.getDevice_num())) {
                        WHERE("device_num = #{device_num}");
                    }
                    if (StringUtils.isNotBlank(device.getName())) {
                        WHERE("name = #{name}");
                    }
                    if (StringUtils.isNotBlank(device.getType())) {
                        WHERE("type = #{type}");
                    }
                    if (StringUtils.isNotBlank(device.getAddress())) {
                        WHERE("address = #{address}");
                    }
                    if (device.getBegin_date() != null) {
                        WHERE("begin_date = #{begin_date}");
                    }
                    if (device.getState() != null) {
                        WHERE("state = #{state}");
                    }
                }
            }
        }.toString();
    }
	
	/**
	 * 更新
	 */
	public String update(final Device device) {
        return new SQL() {
            {
                UPDATE("t_device");
                if (device != null) {
                    if (device.getId() != null) {
                        SET("id = #{id}");
                    }
                    if (device.getDevice_num() != null) {
                        SET("device_num = #{device_num}");
                    }
                    if (device.getName() != null) {
                        SET("name = #{name}");
                    }
                    if (device.getType() != null) {
                        SET("type = #{type}");
                    }
                    if (device.getAddress() != null) {
                        SET("address = #{address}");
                    }
                    if (device.getBegin_date() != null) {
                        SET("begin_date = #{begin_date}");
                    }
                    if (device.getState() != null) {
                        SET("state = #{state}");
                    }
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
	
	/**
	 * 条件删除
	 */
	public String delete(final Device device) {
        return new SQL() {
            {
                DELETE_FROM("t_device");
                if (device != null) {
                    if (device.getId() != null) {
                        WHERE("id = #{id}");
                    }
                    if (StringUtils.isNotBlank(device.getDevice_num())) {
                        WHERE("device_num = #{device_num}");
                    }
                    if (StringUtils.isNotBlank(device.getName())) {
                        WHERE("name = #{name}");
                    }
                    if (StringUtils.isNotBlank(device.getType())) {
                        WHERE("type = #{type}");
                    }
                    if (StringUtils.isNotBlank(device.getAddress())) {
                        WHERE("address = #{address}");
                    }
                    if (device.getBegin_date() != null) {
                        WHERE("begin_date = #{begin_date}");
                    }
                    if (device.getState() != null) {
                        WHERE("state = #{state}");
                    }
                }
            }
        }.toString();
    }

}
