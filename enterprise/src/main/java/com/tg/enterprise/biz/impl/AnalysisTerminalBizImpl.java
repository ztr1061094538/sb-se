package com.tg.enterprise.biz.impl;

import com.tg.enterprise.model.TreeMap;
import com.tg.enterprise.util.EnergyDataCode;
import com.tg.enterprise.util.MonitorUtil;
import com.tg.enterprise.biz.IAnalysisTerminalBiz;
import com.tg.enterprise.dao.AnalysisTerminalMapper;
import com.tg.enterprise.model.AnalysisTerminalData;
import com.tg.enterprise.model.Terminal;
import com.tg.enterprise.vo.AnalysisTerminalQueryVO;
import com.tg.enterprise.vo.SankeyData;
import com.tg.enterprise.vo.SankeyName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalysisTerminalBizImpl implements IAnalysisTerminalBiz
{
	@Autowired
	private AnalysisTerminalMapper mapper;
	
	private static String WASTE_NAME = "废能";
	
	private static String PRODUCE_NAME = "有效能源";
	@Override
	public SankeyData getSankey(List<Terminal> terminals,Long date, EnergyDataCode dataCode)
	{
		SankeyData sankeyData = new SankeyData();
		sankeyData.setNames(new ArrayList<>());
		sankeyData.getNames().add(new SankeyName(WASTE_NAME));
		sankeyData.getNames().add(new SankeyName(PRODUCE_NAME));
		Map<Integer, List<Terminal>> terminalMap = new HashMap<>();
		List<String> terminalIds = new ArrayList<>();
		for (Terminal terminal : terminals)
		{
			if (!terminal.getSeeType() .equals(MonitorUtil.MONITOR_SEETYPE_VIRTUAL))
			{
				terminalIds.add(terminal.getTerminalId());
				sankeyData.getNames().add(new SankeyName(terminal.getName()));
			}
			List<Terminal> tmp = terminalMap.get(terminal.getParentId());
			if (tmp == null)
			{
				tmp = new ArrayList<>();
				terminalMap.put(terminal.getParentId(), tmp);
			}
			tmp.add(terminal);
		}
		AnalysisTerminalQueryVO queryVO = new AnalysisTerminalQueryVO();
		queryVO.setUploadTime(date);
		queryVO.setTerminalIds(terminalIds);
		queryVO.setDataCode(dataCode);
		List<AnalysisTerminalData> energys = mapper.getTerminalDataByDate(queryVO);
		Map<String,AnalysisTerminalData> energyMap = new HashMap<>();
		energys.forEach(t -> {
			energyMap.put(t.getTerminal_id(), t);
		});
		List<TreeMap> treemaps = new ArrayList<>();
		for (Terminal terminal : terminals)
		{
			List<Terminal> children = new ArrayList<>();
			AnalysisTerminalData datanow = energyMap.get(terminal.getTerminalId());
			if (datanow == null)//当前无耗能，直接返回
			{
				continue;
			}
			getChildren(children, terminalMap, terminal.getId());
			if (children.isEmpty())
			{
				TreeMap waste = new TreeMap();
				waste.setSource(terminal.getName());
				waste.setTarget(PRODUCE_NAME);
				waste.setValue(datanow.getData_value());
				treemaps.add(waste);
				continue;
			}
			TreeMap waste = new TreeMap();
			
			waste.setSource(terminal.getName());
			BigDecimal sum = BigDecimal.ZERO;
			for (Terminal child : children)
			{
				AnalysisTerminalData data = energyMap.get(child.getTerminalId());
				BigDecimal energy = BigDecimal.ZERO;
				if (data != null)
				{
					energy = data.getData_value();
				}
				if (energy.compareTo(BigDecimal.ZERO) == 0)
				{
					continue;
				}
				sum = sum.add(energy);
				TreeMap one = new TreeMap();
				one.setSource(terminal.getName());
				one.setTarget(child.getName());
				one.setValue(energy);
				treemaps.add(one);
			}
			BigDecimal wasterDecimal = datanow.getData_value().subtract(sum);
			waste.setValue(datanow.getData_value().subtract(sum));
			if(datanow.getData_value().compareTo(BigDecimal.ZERO) != 0) {
				String name = WASTE_NAME +" "+ wasterDecimal.multiply(new BigDecimal(100)).divide(datanow.getData_value(), 2, BigDecimal.ROUND_HALF_UP)+"%";
				waste.setTarget(name);
				for (SankeyName sankeyName : sankeyData.getNames()) {
					if(sankeyName.getName().equals(WASTE_NAME)) {
						sankeyName.setName(name);
						break;
					}
				}
			}else {
				waste.setTarget(WASTE_NAME);
			}
			if (waste.getValue().compareTo(BigDecimal.ZERO) == 0)
			{
				continue;
			}
			treemaps.add(waste);
		}
		sankeyData.setLinks(treemaps);
		return sankeyData;
	}

	public void getChildren(List<Terminal> children ,Map<Integer,List<Terminal>> terminalMap , int parentId)
	{
		List<Terminal> tmp = terminalMap.get(parentId);
		if (tmp == null)
		{
			return;
		}
		for (Terminal child : tmp)
		{
			if (child.getSeeType() .equals(MonitorUtil.MONITOR_SEETYPE_VIRTUAL))
			{
				getChildren(children, terminalMap, child.getId());
			}
			else
			{
				children.add(child);
			}
		}
	}
}
