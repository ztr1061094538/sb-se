package com.tg.enterprise.vo;

import com.tg.enterprise.model.TreeMap;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SankeyData 
{
	private List<SankeyName> names;
	
	private List<TreeMap> links;
}
