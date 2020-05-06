package com.tg.enterprise.dao.provider;

import com.tg.enterprise.model.PlatformParam;
import org.apache.ibatis.jdbc.SQL;

/**
 * 上传参数 Provider
 * @Copyright 苏州太谷电力股份有限公司
 * @author fuwenxiang
 * @date 2019-11-12
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * fuwenxiang			2019-11-12			新增
 */ 
public class PlatformParamProvider {

	/**
	 * 全查列表
	 */
    public String selectList(final PlatformParam platformParam) {
        return new SQL() {
            {
                SELECT("*");
                FROM("platform_param");
                if (platformParam != null) {
                    if (platformParam.getName() != null) {
                        WHERE("name = #{name}");
                    }
                    if (platformParam.getValue() != null) {
                        WHERE("value = #{value}");
                    }
                }
            }
        }.toString();
    }
	
	/**
	 * 更新
	 */
	public String update(final PlatformParam platformParam) {
        return new SQL() {
            {
                UPDATE("platform_param");
                if (platformParam != null) {
                    if (platformParam.getName() != null) {
                        SET("name = #{name}");
                    }
                    if (platformParam.getValue() != null) {
                        SET("value = #{value}");
                    }
                }
                WHERE("name = #{name}");
            }
        }.toString();
    }

}
