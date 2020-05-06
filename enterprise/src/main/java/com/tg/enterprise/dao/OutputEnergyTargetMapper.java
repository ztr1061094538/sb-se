package com.tg.enterprise.dao;

import com.tg.enterprise.dao.provider.OutputEnergyTargetProvider;
import com.tg.enterprise.model.OutputEnergyTarget;
import com.tg.enterprise.vo.OutputEnergyQueryVO;
import com.tg.enterprise.vo.TargetQueryVO;
import com.tg.enterprise.vo.TargetRespVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OutputEnergyTargetMapper {

    @Select("select * from output_energy_target where product = #{product} and upload_date=#{upload_date} and enterprise_id=#{enterprise_id}")
    OutputEnergyTarget selectById(@Param("product") String product, @Param("enterprise_id") int enterprise_id, @Param("upload_date") Long upload_date);

    @Insert("insert into output_energy_target (product,unit,yield_value,output_value,unit_yield,remark,enterprise_id,upload_date)"+
            "values (#{entity.product},#{entity.unit},#{entity.yield_value},#{entity.output_value},#{entity.unit_yield},#{entity.remark},#{entity.enterprise_id},#{entity.upload_date})")
    int add(@Param("entity") OutputEnergyTarget entity);

    @SelectProvider(type = OutputEnergyTargetProvider.class, method = "queryPageList")
    List<OutputEnergyTarget> queryPageList(OutputEnergyTarget entity);

    @SelectProvider(type = OutputEnergyTargetProvider.class, method = "queryList")
    List<OutputEnergyTarget> queryList(OutputEnergyQueryVO entity);

    @UpdateProvider(type=OutputEnergyTargetProvider.class,method="update")
    int update(OutputEnergyTarget entity);

    @Delete("<script>DELETE  from output_energy_target  where id in "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach></script>")
    int delByIds(@Param("ids") List<Long> ids);

    @SelectProvider(type = OutputEnergyTargetProvider.class, method = "selectOutputList")
    List<TargetRespVO> selectOutputList(TargetQueryVO targetQueryVO);

    @SelectProvider(type = OutputEnergyTargetProvider.class, method = "selectSumOutput")
    TargetRespVO selectSumOutput(TargetQueryVO targetQueryVO);
}
