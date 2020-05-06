package com.tg.enterprise.util;

import com.tg.enterprise.vo.TerminalTreeEnergyNodeVo;
import com.tg.enterprise.vo.TerminalTreeNodeVo;
import com.tg.enterprise.vo.TerminalVo;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MonitorUtil {
	public static final String MONITOR_SEETYPE_PCODE = "190000";
	public static final String MONITOR_SEETYPE_VIRTUAL = "190003";
	public static final String MONITOR_SEETYPE_TRANSFORMER = "190002";
	public static final String MONITOR_SEETYPE_DEVICE = "190001";
	
	/**
	 * 获取监测点下面监测点的树形结构(针对于监测点携带了能源读数或者告警等信息)
	 * @param monitors
	 * @param parentId
	 * @param isFilterVirtualNodes:是否需要过滤监测点
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static List<TerminalTreeEnergyNodeVo> getChildEnergyTree(List<TerminalVo> monitors, Integer parentId,
			boolean isFilterVirtualNodes)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<TerminalTreeEnergyNodeVo> nodes = getChildEnergyTree(monitors, parentId);
		if (isFilterVirtualNodes) {
			List<TerminalTreeEnergyNodeVo> fliter = new ArrayList<>();
			fliterVirtualNodes(fliter, nodes);
			return fliter;
		} else {
			return nodes;
		}

	}
	
	/**
	 * 获取监测点下面监测点的树形结构(针对于监测点携带了能源读数或者告警等信息)
	 * @param monitors
	 * @param parentId
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static List<TerminalTreeEnergyNodeVo> getChildEnergyTree(List<TerminalVo> monitors, Integer parentId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<TerminalTreeEnergyNodeVo> childs = new ArrayList<TerminalTreeEnergyNodeVo>();
		if (monitors != null && !monitors.isEmpty()) {
			for (TerminalVo monitor : monitors) {
				if (parentId.equals(monitor.getParentId())) {
					TerminalTreeEnergyNodeVo subTerminalTreeNodeVo = new TerminalTreeEnergyNodeVo();
					PropertyUtils.copyProperties(subTerminalTreeNodeVo,monitor);
					subTerminalTreeNodeVo.setChildren(new ArrayList<TerminalTreeEnergyNodeVo>());
					subTerminalTreeNodeVo.getChildren().addAll(getChildEnergyTree(monitors, monitor.getId()));
					childs.add(subTerminalTreeNodeVo);
				}
			}
		}
		return childs;
	}

	/**
	 * 过滤掉虚拟节点
	 * @param filterList
	 * @param nodes
	 */
	public static void fliterVirtualNodes(List<TerminalTreeEnergyNodeVo> filterList,List<TerminalTreeEnergyNodeVo> nodes){
		for (TerminalTreeEnergyNodeVo terminalTreeEnergyNodeVo : nodes) {
			String seeType = terminalTreeEnergyNodeVo.getSeeType();
			if(MONITOR_SEETYPE_VIRTUAL.equals(seeType)) {
				fliterVirtualNodes(filterList,terminalTreeEnergyNodeVo.getChildren());
			}else {
				filterList.add(terminalTreeEnergyNodeVo);
			}
		}
	}
	
	/**
	 * 获取监测点下面监测点的树形结构
	 * @param monitors
	 * @param parentId
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static List<TerminalTreeNodeVo> getChildTree(List<TerminalVo> monitors, Integer parentId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<TerminalTreeNodeVo> childs = new ArrayList<TerminalTreeNodeVo>();
		if (monitors != null && !monitors.isEmpty()) {
			for (TerminalVo monitor : monitors) {
				if (parentId.equals(monitor.getParentId())) {
					TerminalTreeNodeVo subTerminalTreeNodeVo = new TerminalTreeNodeVo();
					PropertyUtils.copyProperties(subTerminalTreeNodeVo,monitor);
					subTerminalTreeNodeVo.setChildren(new ArrayList<TerminalTreeNodeVo>());
					subTerminalTreeNodeVo.getChildren().addAll(getChildTree(monitors, monitor.getId()));
					childs.add(subTerminalTreeNodeVo);
				}
			}
		}
		return childs;
	}
	
	/**
	 * 获取某一个监测点下面，所有的有效节点，用于负荷电量计算（一级物理节点）
	 * 二叉树从根部开始找到第一个物理节点
	 * @param parentId:父节点
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static List<TerminalVo> getSubVaildMonitorsByPId(List<TerminalVo> monitors,Integer parentId,String data_code) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<TerminalTreeNodeVo> terminalTreeNodeVos = getChildTree(monitors, parentId);
		List<TerminalVo> result = new ArrayList<>();
		if(terminalTreeNodeVos!= null && !terminalTreeNodeVos.isEmpty()) {
			for (TerminalTreeNodeVo TerminalTreeNodeVo : terminalTreeNodeVos) {
				findFirstLevelPhysicsMonitor(result,TerminalTreeNodeVo,data_code);
			}
		}
		return result;
	}
	
	/**
	 * 获取某一个监测点下面，所有的物理节点，用于负荷电量计算
	 * 
	 * @param parentId:父节点
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static List<TerminalVo> getSubPhysicsMonitorsByPId(List<TerminalVo> monitors,Integer parentId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<TerminalTreeNodeVo> terminalTreeNodeVos = getChildTree(monitors, parentId);
		List<TerminalVo> result = new ArrayList<>();
		if(terminalTreeNodeVos!= null && !terminalTreeNodeVos.isEmpty()) {
			for (TerminalTreeNodeVo TerminalTreeNodeVo : terminalTreeNodeVos) {
				findPhysicsMonitor(result,TerminalTreeNodeVo);
			}
		}
		return result;
	}
	
	/**
	 * 获取某一个监测点下面，所有的节点
	 * 二叉树找到物理节点结束
	 * @param parentId:父节点
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static List<TerminalVo> getAllSubMonitorsByPId(List<TerminalVo> monitors,Integer parentId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<TerminalTreeNodeVo> terminalTreeNodeVos = getChildTree(monitors, parentId);
		List<TerminalVo> result = new ArrayList<>();
		if(terminalTreeNodeVos!= null && !terminalTreeNodeVos.isEmpty()) {
			for (TerminalTreeNodeVo TerminalTreeNodeVo : terminalTreeNodeVos) {
				findMonitor(result,TerminalTreeNodeVo);
			}
		}
		return result;
	}
	
//	/**
//	 * 获取某一个监测点下面，所有的有效节点，用于负荷电量计算(如果自身就是物理节点，就返回自己)
//	 * 二叉树找到物理节点结束
//	 * @param TerminalTreeNodeVos
//	 * @param monitorId:监察点编号，必须不是-1
//	 * @return
//	 * @throws IllegalAccessException
//	 * @throws InvocationTargetException
//	 * @throws NoSuchMethodException
//	 */
//	public static List<TerminalVo> getVaildMonitors(List<TerminalTreeNodeVo> TerminalTreeNodeVos,Integer monitorId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//		List<TerminalVo> result = new ArrayList<>();
//		if(TerminalTreeNodeVos!= null && !TerminalTreeNodeVos.isEmpty()) {
//			for (TerminalTreeNodeVo TerminalTreeNodeVo : TerminalTreeNodeVos) {
//				TerminalTreeNodeVo resultTerminalTreeNodeVo = findNodes(TerminalTreeNodeVo, monitorId);
//				if(resultTerminalTreeNodeVo !=null) {
//					findFirstLevelPhysicsMonitor(result,resultTerminalTreeNodeVo);
//					break;
//				}
//			}
//		}
//		return result;
//	}
	
//	/**
//	 * 根节点下面，根据监测点id找子节点
//	 * @param TerminalTreeNodeVo
//	 * @param monitorId
//	 * @return
//	 * @throws NoSuchMethodException 
//	 * @throws InvocationTargetException 
//	 * @throws IllegalAccessException 
//	 */
//	private static TerminalTreeNodeVo findNodes(TerminalTreeNodeVo TerminalTreeNodeVo,Integer monitorId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//		TerminalTreeNodeVo resultTerminalTreeNodeVo = null;
//		if(TerminalTreeNodeVo != null) {
//			TerminalVo monitor = new TerminalVo();
//			PropertyUtils.copyProperties(monitor,TerminalTreeNodeVo);
//			if(monitorId.equals(monitor.getId())) {
//				return TerminalTreeNodeVo;
//			}
//			if(TerminalTreeNodeVo.getChildren() != null) {
//				for (Iterator<TerminalTreeNodeVo> iterator = TerminalTreeNodeVo.getChildren().iterator(); iterator.hasNext();) {
//					TerminalTreeNodeVo subTerminalTreeNodeVo = iterator.next();
//					resultTerminalTreeNodeVo =  findNodes(subTerminalTreeNodeVo,monitorId);
//					if(resultTerminalTreeNodeVo != null) {
//						break;
//					}
//				}
//			}
//		}
//		return resultTerminalTreeNodeVo;
//	}
	
	/**
	 * 获取第一层物理节点,一般用于企业电量计算和企业总负荷计算
	 * @param result
	 * @param TerminalTreeNodeVo
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private static void findFirstLevelPhysicsMonitor(List<TerminalVo> result,TerminalTreeNodeVo TerminalTreeNodeVo,String data_code) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(TerminalTreeNodeVo != null) {
			TerminalVo monitor = new TerminalVo();
			PropertyUtils.copyProperties(monitor,TerminalTreeNodeVo);
			if(!monitor.getSeeType().equals(MONITOR_SEETYPE_VIRTUAL)) {
				if(data_code==null||data_code.equals(monitor.getDataCode())) {
					result.add(monitor);
				}
			}else {
				if(TerminalTreeNodeVo.getChildren() != null) {
					for (Iterator<TerminalTreeNodeVo> iterator = TerminalTreeNodeVo.getChildren().iterator(); iterator.hasNext();) {
						TerminalTreeNodeVo subTerminalTreeNodeVo = iterator.next();
						findFirstLevelPhysicsMonitor(result,subTerminalTreeNodeVo,data_code);
					}
				}
			}
		}
	}
	
	private static void findPhysicsMonitor(List<TerminalVo> result,TerminalTreeNodeVo TerminalTreeNodeVo) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(TerminalTreeNodeVo != null) {
			TerminalVo monitor = new TerminalVo();
			PropertyUtils.copyProperties(monitor,TerminalTreeNodeVo);
			if(!monitor.getSeeType().equals(MONITOR_SEETYPE_VIRTUAL)) {
				result.add(monitor);
			}
			if(TerminalTreeNodeVo.getChildren() != null) {
				for (Iterator<TerminalTreeNodeVo> iterator = TerminalTreeNodeVo.getChildren().iterator(); iterator.hasNext();) {
					TerminalTreeNodeVo subTerminalTreeNodeVo = iterator.next();
					findPhysicsMonitor(result,subTerminalTreeNodeVo);
				}
			}
		}
	}
	
	private static void findMonitor(List<TerminalVo> result, TerminalTreeNodeVo TerminalTreeNodeVo)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (TerminalTreeNodeVo != null) {
			TerminalVo monitor = new TerminalVo();
			PropertyUtils.copyProperties(monitor, TerminalTreeNodeVo);
			result.add(monitor);
			if (TerminalTreeNodeVo.getChildren() != null) {
				for (Iterator<TerminalTreeNodeVo> iterator = TerminalTreeNodeVo.getChildren().iterator(); iterator
						.hasNext();) {
					TerminalTreeNodeVo subTerminalTreeNodeVo = iterator.next();
					findMonitor(result, subTerminalTreeNodeVo);
				}
			}
		}
	}
}
