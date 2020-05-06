package com.tg.enterprise.controller;

import com.tg.enterprise.biz.*;
import com.tg.enterprise.model.*;
import com.tg.enterprise.util.*;
import com.tg.enterprise.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 监测点管理
 */
@RestController
@RequestMapping("/terminal/")
@Slf4j
public class TerminalController {

    @Autowired
    private ITerminalBiz terminalBiz;
    @Resource
    private IEnergyTypeBiz biz;
    @Autowired
    private ISysDictionaryBiz sysDictionaryBiz;
    @Autowired
    private IMeteringInstrumentBiz meteringInstrumentBiz;
    @Autowired
    private IAnalysisTerminalBiz analysisTerminalBiz;
    @Autowired
    private IMeteringCheckCountBiz meteringCheckCountBiz;
    @Autowired
    private IEnergyOnlineDataBiz energyOnlineDataBiz;

    /**
     * 查询所有
     *
     * @param data
     * @return
     */
    @ApiOperation(value = "查询所有", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getTree", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<TerminalTreeNodeVo> getTree(@RequestBody PageRequestVO<TerminalReqVo> data, HttpServletRequest request) {
        PageResponseVO<TerminalTreeNodeVo> result = new PageResponseVO<TerminalTreeNodeVo>();


        User user = SessionUtil.getUser(request.getSession());
        if (user != null) {
            data.getParams().setEnterpriseId(user.getCid());
        }
        try {
            List<Terminal> terminals = new ArrayList<>();
            if (StringUtils.isNotBlank(data.getParams().getName())) {
                terminals = terminalBiz.selectByParamService(data.getParams());
                data.getParams().setName(null);
            }
            List<Terminal> list = terminalBiz.selectByParamService(data.getParams());
            List<TerminalVo> resp = new ArrayList<>();
            List<EnergyType> energyTypes = biz.getEnergyName();
            List<SysDictionary> seeTypeCodes = sysDictionaryBiz.getByParentCode(MonitorUtil.MONITOR_SEETYPE_PCODE);
            List<MeteringInstrument> meteringInstruments = meteringInstrumentBiz.queryList(null);
            for (Terminal terminal : list) {
                TerminalVo terminalVo = new TerminalVo();
                PropertyUtils.copyProperties(terminalVo, terminal);
                for (EnergyType energyType : energyTypes) {
                    if (energyType.getCode().equals(terminal.getDataCode())) {
                        terminalVo.setDataCodeName(energyType.getName());
                    }
                }
                for (SysDictionary sysDictionary : seeTypeCodes) {
                    if (sysDictionary.getCode().equals(terminal.getSeeType())) {
                        terminalVo.setSeeTypeName(sysDictionary.getName());
                    }
                }
                if (terminal.getMetering_id() != null) {
                    for (MeteringInstrument meteringInstrument : meteringInstruments) {
                        if (meteringInstrument.getId().compareTo(terminal.getMetering_id()) == 0) {
                            terminalVo.setMetering_name(meteringInstrument.getMetering_name());
                        }
                    }
                }
                resp.add(terminalVo);
            }
            List<TerminalTreeNodeVo> nodes = MonitorUtil.getChildTree(resp, -1);
            List<TerminalTreeNodeVo> nodeList = new ArrayList<>();
            Map<Integer, TerminalTreeNodeVo> idTreeMap = nodes.stream().collect(Collectors.toMap(TerminalTreeNodeVo::getId, Function.identity()));
            if (!terminals.isEmpty()) {
                for (Terminal terminal : terminals) {
                    if (terminal.getParentId() == -1) {
                        if (idTreeMap.containsKey(terminal.getId())) {
                            nodeList.add(idTreeMap.get(terminal.getId()));
                        }
                    }
                }
            } else {
                nodeList = nodes;
            }
            result.setRows(nodeList);
            result.setTotal(nodeList.size());
        } catch (Exception e) {
            log.error("getTree", e);
            result.setCode(ErrorCode.INNER_ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    /**
     * 查询监测点的树形结构
     *
     * @param data
     * @return
     */
    @ApiOperation(value = "查询所有", consumes = "application/json")
    @RequestMapping(value = "/getEnergyTree", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<TerminalTreeEnergyNodeVo> getEnergyTree(@RequestBody PageRequestVO<TerminalReqVo> data, HttpServletRequest request) {
        PageResponseVO<TerminalTreeEnergyNodeVo> result = new PageResponseVO<TerminalTreeEnergyNodeVo>();
        try {
            Integer parentId = data.getParams().getParentId() == null ? -1 : data.getParams().getParentId();
            User user = SessionUtil.getUser(request.getSession());
            if (user != null) {
                data.getParams().setEnterpriseId(user.getCid());
            }
            List<TerminalVo> resp = buildRpsr(data);
            List<TerminalTreeEnergyNodeVo> nodes = MonitorUtil.getChildEnergyTree(resp, parentId, true);
            result.setRows(nodes);
            result.setTotal(nodes.size());
        } catch (Exception e) {
            log.error("getTree", e);
            result.setCode(ErrorCode.INNER_ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    public List<TerminalVo> buildRpsr(PageRequestVO<TerminalReqVo> data) throws Exception {
        TerminalReqVo terminalQueryParams = new TerminalReqVo();
        terminalQueryParams.setDataCode(data.getParams().getDataCode());
        terminalQueryParams.setEnterpriseId(data.getParams().getEnterpriseId());

        List<Terminal> list = terminalBiz.selectByParamService(terminalQueryParams);
        List<TerminalVo> resp = new ArrayList<>();
        List<EnergyType> energyTypes = biz.getEnergyName();
        Map<String, EnergyType> energyTypeMap = energyTypes.stream().collect(Collectors.toMap(EnergyType::getCode, Function.identity()));
        List<SysDictionary> seeTypeCodes = sysDictionaryBiz.getByParentCode(MonitorUtil.MONITOR_SEETYPE_PCODE);
        Map<String, String> seeTypeMap = seeTypeCodes.stream().collect(Collectors.toMap(SysDictionary::getCode, SysDictionary::getName));
        List<MeteringInstrument> meteringInstruments = meteringInstrumentBiz.selectByEnterpriseId(data.getParams().getEnterpriseId());
        Map<Integer, MeteringInstrument> meteringInstrumentMap = meteringInstruments.stream().collect(Collectors.toMap(MeteringInstrument::getId, Function.identity()));
        List<MeteringType> meteringTypes = meteringCheckCountBiz.getAllMeteringType();
        Map<Integer, String> meteringTypeMap = meteringTypes.stream().collect(Collectors.toMap(MeteringType::getId, MeteringType::getMetering_type));
        //获取企业一天以内最近的电表读数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar calendar = Calendar.getInstance();
        Long endTime = Long.parseLong(sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Long startTime = Long.parseLong(sdf.format(calendar.getTime()));
        List<String> ids = new ArrayList<>();
        for (Terminal terminal : list) {
            if (terminal.getTerminalId() != null && !"".equals(terminal.getTerminalId())) {
                ids.add(terminal.getTerminalId());
            }
        }
        List<EnergyOnlineData> energyOnlineDatas = new ArrayList<>();
        if (!ids.isEmpty() && !EnergyDataCode.COAL.getDataCode().equals(data.getParams().getDataCode()) &&
                !EnergyDataCode.OTHER.getDataCode().equals(data.getParams().getDataCode())) {
            EnergyOnlineDataQueryVO queryVo = new EnergyOnlineDataQueryVO();
            queryVo.setEnergyCode(data.getParams().getDataCode());
            queryVo.setEndTime(endTime);
            queryVo.setStartTime(startTime);
            queryVo.setIds(ids);
            energyOnlineDatas = energyOnlineDataBiz.queryNewestData(queryVo);
        }

        Map<String, EnergyOnlineData> map = new HashMap<>();
        for (EnergyOnlineData energyOnlineData : energyOnlineDatas) {
            if (!map.containsKey(energyOnlineData.getTerminal_id())) {
                map.put(energyOnlineData.getTerminal_id(), energyOnlineData);
            }
        }
        for (Terminal terminal : list) {
            TerminalVo terminalVo = new TerminalVo();
            PropertyUtils.copyProperties(terminalVo, terminal);
            terminalVo.setDataCodeName(energyTypeMap.get(terminal.getDataCode()) != null ? energyTypeMap.get(terminal.getDataCode()).getName() : "");
            terminalVo.setSeeTypeName(seeTypeMap.get(terminal.getSeeType()));
            if (terminal.getMetering_id() != null) {
                MeteringInstrument meteringInstrument = meteringInstrumentMap.get(terminal.getMetering_id());
                if (meteringInstrument != null) {
                    terminalVo.setMetering_name(meteringInstrument.getMetering_name());
                    if (meteringInstrument.getNext_calibration() != null) {
                        terminalVo.setNext_calibration(sdfYMD.format(sdf.parse(meteringInstrument.getNext_calibration().toString())));
                    }
                    terminalVo.setMetering_type_name(meteringTypeMap.get(meteringInstrument.getMetering_type()));
                }
            }
            EnergyOnlineData energyOnlineData = map.get(terminal.getTerminalId());
            if (energyOnlineData != null && energyOnlineData.getData_value() != null) {
                terminalVo.setValue(energyOnlineData.getData_value().multiply(terminalVo.getRate()));
            }
            resp.add(terminalVo);
        }
        return resp;
    }

    /**
     * 企业添加监测点
     *
     * @return
     */
    @ApiOperation(value = "企业添加监测点", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse add(@RequestBody Terminal terminal) {
        CommonResponse result = new CommonResponse(ErrorCode.OK, "Ok");
        try {
            if (StringUtils.isBlank(terminal.getTerminalId())) {
                return new CommonResponse(ErrorCode.INVALID_PARAM, "请填写编码！");
            }
            terminal.setEnterpriseId(1);
            TerminalReqVo terminalVo = new TerminalReqVo();
            terminalVo.setTerminalId(terminal.getTerminalId());
            List<Terminal> terminals = terminalBiz.selectByParamService(terminalVo);
            if (terminals.size() > 0) {
                result.setCode(ErrorCode.INVALID_PARAM);
                result.setMsg("添加失败：编码已存在");
                return result;
            }
            if (terminal.getMetering_id() != null) {
                MeteringInstrument meteringInstrument = meteringInstrumentBiz.selectById(terminal.getMetering_id());
                if (meteringInstrument == null) {
                    result.setCode(ErrorCode.INVALID_PARAM);
                    result.setMsg("添加失败：关联计量器具编码不存在");
                } else {
                    TerminalReqVo terminalReq = new TerminalReqVo();
                    terminalReq.setMetering_id(terminal.getMetering_id());
                    List<Terminal> meteringterminals = terminalBiz.selectByParamService(terminalReq);
                    if (meteringterminals.size() > 0) {
                        result.setCode(ErrorCode.INVALID_PARAM);
                        result.setMsg("添加失败：器具已被绑定");
                    }
                }
            }
            if (result.getCode() == ErrorCode.OK) {
                boolean succuss = terminalBiz.insertService(terminal);
                if (!succuss) {
                    result.setCode(ErrorCode.INNER_ERROR);
                    result.setMsg("监测点信息添加失败");
                }
            }
        } catch (Exception e) {
            log.error("add", e);
            result.setCode(ErrorCode.INNER_ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    /**
     * 修改监测点信息
     *
     * @param terminal
     * @return
     */
    @ApiOperation(value = "修改监测点信息", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/edit", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse edit(@RequestBody Terminal terminal) {
        CommonResponse result = new CommonResponse(ErrorCode.OK, "Ok");
        try {
            if (StringUtils.isBlank(terminal.getTerminalId())) {
                return new CommonResponse(ErrorCode.INVALID_PARAM, "请填写编码！");
            }
            if (terminal.getId().intValue() == terminal.getParentId().intValue()) {
                result.setCode(ErrorCode.INVALID_PARAM);
                result.setMsg("修改失败：不能挂载在自身节点上");
                return result;
            }
            TerminalReqVo terminalVo = new TerminalReqVo();
            terminalVo.setTerminalId(terminal.getTerminalId());
            List<Terminal> terminals = terminalBiz.selectByParamService(terminalVo);
            if (terminals.size() > 0 && terminals.get(0).getId().compareTo(terminal.getId()) != 0) {
                result.setCode(ErrorCode.INVALID_PARAM);
                result.setMsg("修改失败：编码已存在");
                return result;
            }
            Terminal ter = terminalBiz.selectByIdService(terminal.getId());
            if (ter.getParentId() == -1 && terminal.getParentId() != -1) {
                return new CommonResponse(ErrorCode.INVALID_PARAM, "顶级节点不能挂载在子节点下");
            }
            if (terminal.getMetering_id() != null) {
                MeteringInstrument meteringInstrument = meteringInstrumentBiz.selectById(terminal.getMetering_id());
                if (meteringInstrument == null) {
                    result.setCode(ErrorCode.INVALID_PARAM);
                    result.setMsg("添加失败：关联计量器具编码不存在");
                    return result;
                } else {
                    TerminalReqVo terminalReq = new TerminalReqVo();
                    terminalReq.setMetering_id(terminal.getMetering_id());
                    List<Terminal> meteringterminals = terminalBiz.selectByParamService(terminalReq);
                    if (meteringterminals.size() > 0
                            && meteringterminals.get(0).getId().compareTo(terminal.getId()) != 0) {
                        result.setCode(ErrorCode.INVALID_PARAM);
                        result.setMsg("添加失败：器具已被绑定");
                        return result;
                    }
                }
            }
            boolean succuss = terminalBiz.updateService(terminal);
            if (!succuss) {
                result.setCode(ErrorCode.INNER_ERROR);
                result.setMsg("监测点信息更新失败");
            }
        } catch (Exception e) {
            log.error("edit", e);
            result.setCode(ErrorCode.INNER_ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    /**
     * 删除某一条数据
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除某一条数据", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/del", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse del(@RequestBody List<Integer> ids) {
        CommonResponse result = new CommonResponse(ErrorCode.OK, "Ok");
        try {
            boolean succuss = terminalBiz.deleteService(ids);
            if (!succuss) {
                result.setCode(ErrorCode.INNER_ERROR);
                result.setMsg("删除监测点失败");
            }
        } catch (Exception e) {
            log.error("del", e);
            result.setCode(ErrorCode.INNER_ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    /**
     * 单个详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "单个详情", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/detail", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<Terminal> detail(@RequestBody Integer id) {
        ResponseVO<Terminal> result = new ResponseVO<Terminal>();
        try {
            Terminal applyData = terminalBiz.selectByIdService(id);
            if (null != applyData) {
                result.setCode(ErrorCode.OK);
                result.setMsg("ok");
                result.setData(applyData);
            } else {
                result.setCode(ErrorCode.BG_GETDATA_ERROR);
                result.setMsg("没有查到数据");
            }
        } catch (Throwable e) {
            log.error("detail", e);
            result.setCode(ErrorCode.INNER_ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    @ApiOperation(value = "计量器具下拉", response = ResponseVO.class)
    @RequestMapping(value = "/getMeters", method = RequestMethod.GET)
    public ResponseVO<SysDictionaryVO<CodeVO>> getMeters() {
        SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
        List<CodeVO> list = new ArrayList<CodeVO>();
        List<MeteringInstrument> meteringInstruments = meteringInstrumentBiz.queryList(null);
        for (MeteringInstrument meteringInstrument : meteringInstruments) {
            CodeVO codeVO = new CodeVO();
            codeVO.setCode(meteringInstrument.getId() + "");
            codeVO.setName(meteringInstrument.getMetering_name());
            list.add(codeVO);
        }
        data.setList(list);
        return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
    }

    @ApiOperation(value = "Sankey图表", consumes = "application/json")
    @RequestMapping(value = "/getSankey", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<SankeyData> getSankey(@RequestBody SankeyQueryVO data) {
        ResponseVO<SankeyData> result = new ResponseVO<>();
        try {
            TerminalReqVo terminalQueryParams = new TerminalReqVo();
            terminalQueryParams.setDataCode(data.getDataCode());
            terminalQueryParams.setEnterpriseId(data.getEnterpriseId());
            List<Terminal> terminals = terminalBiz.selectByParamService(terminalQueryParams);

            Long date = Long.valueOf(data.getDate().replace("-", ""));

            SankeyData sankeyData = analysisTerminalBiz.getSankey(terminals, date, EnergyDataCode.parse(data.getDataCode()));
            result.setCode(ErrorCode.OK);
            result.setMsg("ok");
            result.setData(sankeyData);
        } catch (Exception e) {
            log.error("getTree", e);
            result.setCode(ErrorCode.INNER_ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

}
