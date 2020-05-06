package com.tg.enterprise.config;

import com.tg.enterprise.biz.IAreaBiz;
import com.tg.enterprise.biz.IRegionBiz;
import com.tg.enterprise.biz.ISysDictionaryBiz;
import com.tg.enterprise.model.Area;
import com.tg.enterprise.model.Region;
import com.tg.enterprise.vo.CodeNode;
import com.tg.enterprise.vo.CodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ServerContext {

	@Autowired
	private ISysDictionaryBiz iSysDictionaryBiz;
	
	@Autowired
	private IRegionBiz iAreaBiz;
	
	//最后的树形map数据字典
	public static Map<String, List<CodeNode>> dictionary = new HashMap<String, List<CodeNode>>();
	//所有的有子节点的code_no
	public static Map<String, CodeNode> parentList = new HashMap<String, CodeNode>();

	//所有的数据字典下拉
	public static Map<String,CodeNode> dictMap = new HashMap<String,CodeNode>();

	public static Map<String, CodeVO> areaMap = new HashMap<>();
	@Value("${em2.config1711.region}")
	String region;
	public void initDict(){
		Long startTime = System.currentTimeMillis();
		List<CodeNode> sysList = iSysDictionaryBiz.queryList(null);
		for (CodeNode codeNode:sysList ) {
			parentList.put(codeNode.getParent_code(),codeNode);
			dictMap.put(codeNode.getCode(),codeNode);
		}
		List<CodeNode> dictList = new ArrayList<CodeNode>();
		for (CodeNode codeNode:sysList ) {
			if(codeNode.getParent_code()!=null&&"0".equals(codeNode.getParent_code())){
				dictList.add(codeNode);
			}
		}
		for (CodeNode codeNode:dictList ) {
			codeNode.setChildList(getChild(codeNode.getCode(),sysList));
		}
		dictionary.put("dict",dictList);
		log.info("数据字典写入内存，共耗时" + (System.currentTimeMillis() - startTime) + "ms");
	}
	@PostConstruct
	public void init() {
		Map<String, List<CodeNode>> dictionary1 = new HashMap<String, List<CodeNode>>();
		Map<String, CodeNode> parentList1 = new HashMap<String, CodeNode>();
		Map<String,CodeNode> dictMap1 = new HashMap<String,CodeNode>();
		Map<String,CodeVO> areaMap1 = new HashMap<>();
		Long startTime = System.currentTimeMillis();
		List<CodeNode> sysList = iSysDictionaryBiz.queryList(null);
		for (CodeNode codeNode:sysList ) {
			parentList1.put(codeNode.getParent_code(),codeNode);
			dictMap1.put(codeNode.getCode(),codeNode);
		}
		
		//最后的list
		List<CodeNode> dictList = new ArrayList<CodeNode>();
		for (CodeNode codeNode:sysList ) {
			if(codeNode.getParent_code()!=null&&"0".equals(codeNode.getParent_code())){
				dictList.add(codeNode);
			}
		}
		for (CodeNode codeNode:dictList ) {
			codeNode.setChildList(getChild(codeNode.getCode(),sysList));
		}
		dictionary1.put("dict",dictList);
		List<Region> areas = iAreaBiz.selectByPCode(region);
		for (Region area : areas)
		{
			CodeVO codeVO = new CodeVO();
			codeVO.setCode(area.getCode());
			codeVO.setName(area.getFullName());
			codeVO.setParent_code(area.getPcode());
			areaMap1.put(area.getCode(), codeVO);
		}
		log.info("数据字典写入内存，共耗时" + (System.currentTimeMillis() - startTime) + "ms");
		dictionary = dictionary1;
		parentList = parentList1;
		dictMap = dictMap1;
		areaMap = areaMap1;
	}


	private List<CodeNode> getChild(String id,List<CodeNode> rootMenu){
		//子数据字典
		List<CodeNode> childList = new ArrayList<CodeNode>();
		for (CodeNode codeNode : rootMenu) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (codeNode.getParent_code()!=null&&!"".equals(codeNode.getParent_code())) {
				if (codeNode.getParent_code().equals(id)) {
					childList.add(codeNode);
				}
			}
		}
		// 把子菜单的子菜单再循环一遍
		for (CodeNode codeNode : childList) {// 没有url子菜单还有子菜单
			if (parentList.containsKey(codeNode.getCode())) {
				// 递归
				codeNode.setChildList(getChild(codeNode.getCode(), rootMenu));
			}
		}
		// 递归退出条件
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}

}
