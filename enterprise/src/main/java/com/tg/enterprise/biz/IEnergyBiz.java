package com.tg.enterprise.biz;

import com.tg.enterprise.model.AnalysisCompany;
import com.tg.enterprise.model.AnalysisTerminal;
import com.tg.enterprise.util.AnalysisTypeEnum;
import com.tg.enterprise.util.EnergyDataCode;
import com.tg.enterprise.vo.EnergyQueryVO;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */
public interface IEnergyBiz
{
    /**
     * 获取监测点数据
     * @param upload_date 时间
     * @return
     */
    List<AnalysisTerminal> getTermianlList(EnergyQueryVO upload_date);

    /**
     * 获取企业数据
     * @param upload_date 时间
     * @return
     */
    List<AnalysisCompany> getCompanyList(EnergyQueryVO upload_date);


    AnalysisTerminal getSumTerminalConsumption(EnergyQueryVO energyQueryVO);
}
