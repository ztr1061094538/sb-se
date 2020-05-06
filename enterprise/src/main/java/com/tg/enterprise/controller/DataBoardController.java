package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.*;
import com.tg.enterprise.config.ServerContext;
import com.tg.enterprise.model.*;
import com.tg.enterprise.util.*;
import com.tg.enterprise.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: enterprise
 * @author: fuwenxiang
 * @create: 2019-12-03
 **/
@RestController
@RequestMapping("/dataBoard")
//@SuppressWarnings("all")
public class DataBoardController {

    @Autowired
    IProcessCodeBiz processCodeBiz;

    @Autowired
    IEnergyEquipmentContactBiz energyEquipmentContactBiz;

    @Autowired
    IEnergyProcessTargetBiz energyProcessTargetBiz;

    @Autowired
    IEnergyTypeBiz energyTypeBiz;

    @Autowired
    IMeteringInstrumentBiz meteringInstrumentBiz;

    @Autowired
    IMeteringTypeBiz meteringTypeBiz;

    @Autowired
    IEnterpriseBiz enterpriseBiz;

    @Autowired
    IEnergyEquipmentBiz energyEquipmentBiz;

    @Autowired
    IEnergyBiz energyBiz;

    @Autowired
    private IWorkTaskInfoBiz workTaskInfoBiz;

    @Autowired
    private IUploadLogBiz uploadLogBiz;

    @Autowired
    private IEntTypeBiz entTypeBiz;

    @Autowired
    private IIndustryBiz industryBiz;

    @Autowired
    private ITerminalBiz terminalBiz;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

    private static DateTimeFormatter formatterYMD = DateTimeFormatter.ofPattern("yyyyMMdd");

    @ApiOperation(value = "生产工序/用能设备用能柱状图", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getProcessChart", method = RequestMethod.POST)
    public ResponseVO<LineVO<BigDecimal>> getProcessChart() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ResponseVO<LineVO<BigDecimal>> responseVO = new ResponseVO<>();
        LineVO<BigDecimal> lineVO = new LineVO<>();
        ChartDataVO<BigDecimal> chartDataVO = new ChartDataVO<>();
        List<String> categories = new ArrayList<>();
        List<ChartKeyValuePairVO<BigDecimal>> series = new ArrayList<>();
        ChartKeyValuePairVO<BigDecimal> powerPairVO = new ChartKeyValuePairVO<>();
        List<BigDecimal> powerList = new ArrayList<>();
        ChartKeyValuePairVO<BigDecimal> gasPairVO = new ChartKeyValuePairVO<>();
        List<BigDecimal> gasList = new ArrayList<>();
        ChartKeyValuePairVO<BigDecimal> coalPairVO = new ChartKeyValuePairVO<>();
        List<BigDecimal> coalList = new ArrayList<>();
        ChartKeyValuePairVO<BigDecimal> otherPairVO = new ChartKeyValuePairVO<>();
        List<BigDecimal> otherList = new ArrayList<>();

        List<TerminalVo> terminals = terminalBiz.selectList(null);
        Map<String, TerminalVo> terminalVoMap = new HashMap<>();
        for (TerminalVo terminal : terminals)
        {
            terminalVoMap.put(terminal.getTerminalId(), terminal);
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String upload_time = DateUtil.getLocalDateStr(cal.getTime(), formatter);

        EnergyType energyType = new EnergyType();
        energyType.setType(2);
        List<EnergyType> energyTypes = energyTypeBiz.selectList(energyType);
        Map<String, EnergyType> energyTypeMap = energyTypes.stream().collect(Collectors.toMap(EnergyType::getCode, e -> e));

        //生产工序
        List<ProcessCode> processCodes = processCodeBiz.selectList(new ProcessCode());
        if (processCodes != null && processCodes.size() > 0) {
            Map<String, ProcessCode> processCodeMap = processCodes.stream().collect(Collectors.toMap(ProcessCode::getCode, e -> e));
            //用能设备采集数据
            EnergyEquipmentContact energyEquipmentContact = new EnergyEquipmentContact();
            energyEquipmentContact.setCodeList(new ArrayList<>(processCodeMap.keySet()));
            List<EnergyEquipmentContact> contactList = energyEquipmentContactBiz.selectList1(energyEquipmentContact);
            Map<Long, EnergyEquipmentContact> contactMap = new HashMap<>();
            Map<String, List<EnergyEquipmentContact>> codeContactMap = new HashMap<>();
            for (EnergyEquipmentContact equipmentContact : contactList) {
                contactMap.put(equipmentContact.getId(), equipmentContact);
                if (!codeContactMap.containsKey(equipmentContact.getCode())) {
                    codeContactMap.put(equipmentContact.getCode(), new ArrayList<>());
                }
                codeContactMap.get(equipmentContact.getCode()).add(equipmentContact);
            }

            //重点工序/用能设备能耗
            EnergyProcessVO energyProcessVO = new EnergyProcessVO();
            energyProcessVO.setUpload_date(Long.valueOf(upload_time));
            energyProcessVO.setProcess_ids(new ArrayList<>(contactMap.keySet()));
            List<EnergyProcessTarget> energyProcessTargets = energyProcessTargetBiz.selectListByProcess(energyProcessVO);

            Map<Long, EnergyProcessTarget> processTargetMap = energyProcessTargets.stream().
                    collect(Collectors.toMap(EnergyProcessTarget::getProcess_id, e -> e));

            for (ProcessCode processCode : processCodes) {
                categories.add(processCode.getName());
                List<EnergyEquipmentContact> contacts = codeContactMap.get(processCode.getCode()) == null ?
                        new ArrayList<>() : codeContactMap.get(processCode.getCode());
                //系统对接数据
                List<EnergyEquipmentContact> systemDatas = contacts.stream().filter(contact -> !"5".equals(contact.getInputType())).collect(Collectors.toList());
                Map<String, List<EnergyEquipmentContact>> categoryMap = systemDatas.stream().collect(Collectors.groupingBy(EnergyEquipmentContact::getCategory));
                //手工填报数据
                contacts = contacts.stream().filter(contact -> "5".equals(contact.getInputType())).collect(Collectors.toList());
                List<EnergyEquipmentContact> coalContacts = contacts.stream().filter(contact -> EnergyDataCode.COAL.getDataCode().equals(contact.getCategory())).collect(Collectors.toList());
                List<EnergyEquipmentContact> otherContacts = contacts.stream().filter(contact -> !EnergyDataCode.COAL.getDataCode().equals(contact.getCategory())).collect(Collectors.toList());
                //coalContacts.stream().collect(Collectors.summarizingDouble(Ener))

                BigDecimal totalPower = BigDecimal.ZERO;
                BigDecimal totalGas = BigDecimal.ZERO;
                BigDecimal totalCoal = BigDecimal.ZERO;
                BigDecimal totalOther = BigDecimal.ZERO;
                //BigDecimal totalWater = BigDecimal.ZERO;

                for (EnergyDataCode energyDataCode : EnergyTypeRouter.getDataCodesWithEnergy()) {
                    //List<EnergyEquipmentContact> contactList1 = new ArrayList<>();
                    AnalysisTerminal powers = new AnalysisTerminal();
                    switch (energyDataCode) {
                        case POWER:
                            powers = getAnalysisTerminal(terminals, terminalVoMap, upload_time, categoryMap, energyDataCode);
                            totalPower = (powers == null || powers.getData_value() == null ) ? totalPower : totalPower.add(powers.getData_value());
                            break;
                        case GAS:
                            powers = getAnalysisTerminal(terminals, terminalVoMap, upload_time, categoryMap, energyDataCode);
                            totalGas = (powers == null || powers.getData_value() == null ) ? totalGas : totalGas.add(powers.getData_value());
                            break;
                        case COAL:
                            powers = getAnalysisTerminal(terminals, terminalVoMap, upload_time, categoryMap, energyDataCode);
                            totalCoal = (powers == null || powers.getData_value() == null ) ? totalCoal : totalCoal.add(powers.getData_value().multiply(energyTypeMap.get(EnergyDataCode.COAL.getDataCode()).getZbxs()));
                            for (EnergyEquipmentContact contact : coalContacts) {
                                EnergyType type = energyTypeMap.get(contact.getCategory());
                                EnergyProcessTarget energyProcessTarget = processTargetMap.get(contact.getId());
                                totalCoal = totalCoal.add(energyProcessTarget == null ? BigDecimal.ZERO : energyProcessTarget.getData_value().multiply(type.getZbxs()));
                            }
                            break;
                        case OTHER:
                            for (EnergyEquipmentContact contact : otherContacts) {
                                EnergyType type = energyTypeMap.get(contact.getCategory());
                                EnergyProcessTarget energyProcessTarget = processTargetMap.get(contact.getId());
                                totalOther = totalOther.add(energyProcessTarget == null ? BigDecimal.ZERO : energyProcessTarget.getData_value().multiply(type.getZbxs()));
                            }
                            break;
                        default:
                    }
                }
                powerList.add(totalPower.multiply(energyTypeMap.get(EnergyDataCode.POWER.getDataCode()) == null ? BigDecimal.valueOf(0) : energyTypeMap.get(EnergyDataCode.POWER.getDataCode()).getZbxs()).divide(BigDecimal.valueOf(10000)).setScale(2,BigDecimal.ROUND_HALF_UP));
                gasList.add(totalGas.multiply(energyTypeMap.get(EnergyDataCode.GAS.getDataCode()) == null ? BigDecimal.valueOf(0) : energyTypeMap.get(EnergyDataCode.GAS.getDataCode()).getZbxs()).divide(BigDecimal.valueOf(10000)).setScale(2,BigDecimal.ROUND_HALF_UP));
                coalList.add(totalCoal.setScale(2,BigDecimal.ROUND_HALF_UP));
                otherList.add(totalOther.setScale(2,BigDecimal.ROUND_HALF_UP));
            }
        }
        powerPairVO.setName("用电量");
        powerPairVO.setData(powerList);
        gasPairVO.setName("用气量");
        gasPairVO.setData(gasList);
        coalPairVO.setName("煤炭用量");
        coalPairVO.setData(coalList);
        otherPairVO.setName("其它用量");
        otherPairVO.setData(otherList);
        series.add(powerPairVO);
        series.add(gasPairVO);
        series.add(coalPairVO);
        series.add(otherPairVO);
        chartDataVO.setCategories(categories);
        chartDataVO.setSeries(series);
        chartDataVO.setYzTitle("单位(吨标煤)");
        lineVO.setChartData(chartDataVO);
        responseVO.setData(lineVO);
        return responseVO;
    }

    private AnalysisTerminal getAnalysisTerminal(List<TerminalVo> terminals, Map<String, TerminalVo> terminalVoMap, String upload_time, Map<String, List<EnergyEquipmentContact>> categoryMap, EnergyDataCode energyDataCode) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        AnalysisTerminal powers = null;
        List<EnergyEquipmentContact> contactList1 = categoryMap.get(energyDataCode.getDataCode());
        List<String> ids = new ArrayList<>();
        getTerminalIds(terminals, terminalVoMap, contactList1, ids);
        if(ids.size() > 0) {
            EnergyQueryVO energyQueryVO = new EnergyQueryVO();
            String tableName = EnergyTypeRouter.getAnalysisTableName(energyDataCode, AnalysisTypeEnum.MONTH, AnalysisEntityEnum.TERMINAL);
            energyQueryVO.setTableName(tableName);
            energyQueryVO.setUpload_date(Long.valueOf(upload_time));
            energyQueryVO.setTerminalIdSet(new HashSet<>(ids));
            powers = energyBiz.getSumTerminalConsumption(energyQueryVO);
        }
        return powers;
    }

    private void getTerminalIds(List<TerminalVo> terminals, Map<String, TerminalVo> terminalVoMap, List<EnergyEquipmentContact> contactList1, List<String> ids) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if(contactList1 != null && contactList1.size() > 0) {
            for (EnergyEquipmentContact equipmentContact : contactList1) {
                String terminalId = equipmentContact.getTerminalId();
                String[] terminalIds = terminalId.split(",");
                //采集项数据code
                for (String id : terminalIds) {
                    TerminalVo terminalVo = terminalVoMap.get(id);
                    if (terminalVo != null) {
                        if (terminalVo.getSeeType().equals(MonitorUtil.MONITOR_SEETYPE_VIRTUAL)) {
                            List<TerminalVo> physicsTerminals = MonitorUtil.getSubVaildMonitorsByPId(terminals, terminalVo.getId(), terminalVo.getDataCode());
                            if (!physicsTerminals.isEmpty()) {
                                ids.addAll(physicsTerminals.stream().map(TerminalVo::getTerminalId).collect(Collectors.toList()));
                            }
                        } else {
                            ids.add(id);
                        }
                    }
                }
            }
        }
    }

    @ApiOperation(value = "生产工序/用能设备用能表格", consumes = "application/json", response = ListResponseVO.class)
    @RequestMapping(value = "/getProcessTable", method = RequestMethod.POST)
    public ListResponseVO<ProcessTableRespVO> getProcessTable() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ListResponseVO<ProcessTableRespVO> responseVO = new ListResponseVO<>(ErrorCode.OK, "ok");
        List<TerminalVo> terminals = terminalBiz.selectList(null);
        Map<String, TerminalVo> terminalVoMap = new HashMap<>();
        for (TerminalVo terminal : terminals)
        {
            terminalVoMap.put(terminal.getTerminalId(), terminal);
        }

        List<ProcessTableRespVO> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String upload_time = DateUtil.getLocalDateStr(cal.getTime(), formatter);

        EnergyType energyType = new EnergyType();
        energyType.setType(2);
        List<EnergyType> energyTypes = energyTypeBiz.selectList(energyType);
        Map<String, EnergyType> energyTypeMap = energyTypes.stream().collect(Collectors.toMap(EnergyType::getCode, e -> e));

        //生产工序
        List<ProcessCode> processCodes = processCodeBiz.selectList(new ProcessCode());
        if (processCodes != null && processCodes.size() > 0) {
            Map<String, ProcessCode> processCodeMap = processCodes.stream().collect(Collectors.toMap(ProcessCode::getCode, e -> e));
            //用能设备采集数据
            EnergyEquipmentContact energyEquipmentContact = new EnergyEquipmentContact();
            energyEquipmentContact.setCodeList(new ArrayList<>(processCodeMap.keySet()));
            List<EnergyEquipmentContact> contactList = energyEquipmentContactBiz.selectList1(energyEquipmentContact);
            Map<Long, EnergyEquipmentContact> contactMap = new HashMap<>();
            Map<String, List<EnergyEquipmentContact>> codeContactMap = new HashMap<>();
            for (EnergyEquipmentContact equipmentContact : contactList) {
                contactMap.put(equipmentContact.getId(), equipmentContact);
                if (!codeContactMap.containsKey(equipmentContact.getCode())) {
                    codeContactMap.put(equipmentContact.getCode(), new ArrayList<>());
                }
                codeContactMap.get(equipmentContact.getCode()).add(equipmentContact);
            }

            //重点工序/用能设备能耗
            EnergyProcessVO energyProcessVO = new EnergyProcessVO();
            energyProcessVO.setUpload_date(Long.valueOf(upload_time));
            energyProcessVO.setProcess_ids(new ArrayList<>(contactMap.keySet()));
            List<EnergyProcessTarget> energyProcessTargets = energyProcessTargetBiz.selectListByProcess(energyProcessVO);

            Map<Long, EnergyProcessTarget> processTargetMap = energyProcessTargets.stream().
                    collect(Collectors.toMap(EnergyProcessTarget::getProcess_id, e -> e));

            for (ProcessCode processCode : processCodes) {
                ProcessTableRespVO processTable = new ProcessTableRespVO();
                processTable.setProcessName(processCode.getName());
                List<EnergyEquipmentContact> contacts = codeContactMap.get(processCode.getCode()) == null ?
                        new ArrayList<>() : codeContactMap.get(processCode.getCode());
                //系统对接数据
                List<EnergyEquipmentContact> systemDatas = contacts.stream().filter(contact -> !"5".equals(contact.getInputType())).collect(Collectors.toList());
                Map<String, List<EnergyEquipmentContact>> categoryMap = systemDatas.stream().collect(Collectors.groupingBy(EnergyEquipmentContact::getCategory));
                //手工填报数据
                contacts = contacts.stream().filter(contact -> "5".equals(contact.getInputType())).collect(Collectors.toList());
                List<EnergyEquipmentContact> coalContacts = contacts.stream().filter(contact -> EnergyDataCode.COAL.getDataCode().equals(contact.getCategory())).collect(Collectors.toList());
                List<EnergyEquipmentContact> otherContacts = contacts.stream().filter(contact -> !EnergyDataCode.COAL.getDataCode().equals(contact.getCategory())).collect(Collectors.toList());
                //coalContacts.stream().collect(Collectors.summarizingDouble(Ener))

                BigDecimal totalPower = BigDecimal.ZERO;
                BigDecimal totalGas = BigDecimal.ZERO;
                BigDecimal totalCoal = BigDecimal.ZERO;
                BigDecimal totalOther = BigDecimal.ZERO;

                for (EnergyDataCode energyDataCode : EnergyTypeRouter.getDataCodesWithEnergy()) {
                    //List<EnergyEquipmentContact> contactList1 = new ArrayList<>();
                    AnalysisTerminal powers = new AnalysisTerminal();
                    switch (energyDataCode) {
                        case POWER:
                            powers = getAnalysisTerminal(terminals, terminalVoMap, upload_time, categoryMap, energyDataCode);
                            totalPower = (powers == null || powers.getData_value() == null ) ? totalPower : totalPower.add(powers.getData_value());
                            break;
                        case GAS:
                            powers = getAnalysisTerminal(terminals, terminalVoMap, upload_time, categoryMap, energyDataCode);
                            totalGas = (powers == null || powers.getData_value() == null ) ? totalGas : totalGas.add(powers.getData_value());
                            break;
                        case COAL:
                            powers = getAnalysisTerminal(terminals, terminalVoMap, upload_time, categoryMap, energyDataCode);
                            totalCoal = (powers == null || powers.getData_value() == null ) ? totalCoal : totalCoal.add(powers.getData_value().multiply(energyTypeMap.get(EnergyDataCode.COAL.getDataCode()).getZbxs()));
                            for (EnergyEquipmentContact contact : coalContacts) {
                                EnergyType type = energyTypeMap.get(contact.getCategory());
                                EnergyProcessTarget energyProcessTarget = processTargetMap.get(contact.getId());
                                totalCoal = totalCoal.add(energyProcessTarget == null ? BigDecimal.ZERO : energyProcessTarget.getData_value().multiply(type.getZbxs()));
                            }
                            break;
                        case OTHER:
                            for (EnergyEquipmentContact contact : otherContacts) {
                                EnergyType type = energyTypeMap.get(contact.getCategory());
                                EnergyProcessTarget energyProcessTarget = processTargetMap.get(contact.getId());
                                totalOther = totalOther.add(energyProcessTarget == null ? BigDecimal.ZERO : energyProcessTarget.getData_value().multiply(type.getZbxs()));
                            }
                            break;
                        default:
                    }
                }
                processTable.setPower(totalPower.multiply(energyTypeMap.get(EnergyDataCode.POWER.getDataCode()) == null ? BigDecimal.valueOf(0) : energyTypeMap.get(EnergyDataCode.POWER.getDataCode()).getZbxs()).divide(BigDecimal.valueOf(10000)).setScale(2,BigDecimal.ROUND_HALF_UP) + "");
                processTable.setGas(totalGas.multiply(energyTypeMap.get(EnergyDataCode.GAS.getDataCode()) == null ? BigDecimal.valueOf(0) : energyTypeMap.get(EnergyDataCode.GAS.getDataCode()).getZbxs()).divide(BigDecimal.valueOf(10000)).setScale(2,BigDecimal.ROUND_HALF_UP) + "");
                processTable.setCoal(totalCoal.setScale(2,BigDecimal.ROUND_HALF_UP) + "");
                processTable.setOther(totalOther.setScale(2,BigDecimal.ROUND_HALF_UP) + "");
                list.add(processTable);
            }
        }
        responseVO.setData(list);
        return responseVO;
    }

    @ApiOperation(value = "企业计量器具配备表", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getMetering", method = RequestMethod.POST)
    public ResponseVO<JSONArray> getMetering() {
        ResponseVO<JSONArray> responseVO = new ResponseVO<>(ErrorCode.OK, "ok");
        JSONArray array = new JSONArray();

        List<MeteringType> meteringTypes = meteringTypeBiz.selectAll();
        Map<String, String> meteringTypeMap = new HashMap<>();
        for (MeteringType meteringType : meteringTypes) {
            meteringTypeMap.put(meteringType.getId() + "", meteringType.getMetering_type());
        }
        Integer total = meteringInstrumentBiz.getTotal();

        //等级
        List<MeteringRespVO> meteringLevelList = meteringInstrumentBiz.selectByMeteringLevel();
        JSONObject levelObject = new JSONObject();
        JSONArray levelArray = new JSONArray();
        for (MeteringRespVO meteringRespVO : meteringLevelList) {
            JSONObject object = new JSONObject();
            object.put("value", meteringRespVO.getNum());
            object.put("percent", BigDecimal.valueOf(meteringRespVO.getNum()).divide(BigDecimal.valueOf(total), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
            switch (meteringRespVO.getName()) {
                case "1":
                    object.put("name", "一级器具");
                    break;
                case "2":
                    object.put("name", "二级器具");
                    break;
                case "3":
                    object.put("name", "三级器具");
                    break;
                default:
            }
            levelArray.add(object);
        }
        levelObject.put("level", levelArray);
        array.add(levelObject);

        //类型
        List<MeteringRespVO> meteringTypeList = meteringInstrumentBiz.selectByMeteringType();
        JSONObject typeObject = new JSONObject();
        JSONArray typeArray = new JSONArray();
        for (MeteringRespVO meteringRespVO : meteringTypeList) {
            JSONObject object = new JSONObject();
            object.put("value", meteringRespVO.getNum());
            object.put("name", meteringTypeMap.get(meteringRespVO.getName()));
            object.put("percent", BigDecimal.valueOf(meteringRespVO.getNum()).divide(BigDecimal.valueOf(total), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
            typeArray.add(object);
        }
        typeObject.put("type", typeArray);
        array.add(typeObject);
        responseVO.setData(array);
        return responseVO;
    }

    @ApiOperation(value = "企业基本信息", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getEnterpriseInfo", method = RequestMethod.POST)
    public ResponseVO<EnterpriseRespVO> getEnterpriseInfo() {
        ResponseVO<EnterpriseRespVO> respVO = new ResponseVO<>(ErrorCode.OK, "ok");
        //企业基本信息
        Enterprise enterprise = enterpriseBiz.getList(null).get(0);
        EnterpriseRespVO enterpriseRespVO = new EnterpriseRespVO();
        enterpriseRespVO.setName(enterprise.getName());
        enterpriseRespVO.setCode(enterprise.getCode());
        enterpriseRespVO.setType(entTypeBiz.selectNameById(enterprise.getType_code()));
        enterpriseRespVO.setIndustry(industryBiz.selectById(enterprise.getIndustry_code()).getFullName());
        enterpriseRespVO.setEnergy_scale(ServerContext.dictMap.get(enterprise.getEnergy_scale()).getName());

        //主要能耗设备数量
        Integer num = energyEquipmentBiz.selectSumbyEid();
        enterpriseRespVO.setEquipmentNum(num);
        respVO.setData(enterpriseRespVO);
        return respVO;
    }

    @ApiOperation(value = "本月用能概况", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getConsumptionOfMonth", method = RequestMethod.POST)
    public ResponseVO<JSONObject> getConsumptionOfMonth() {
        ResponseVO<JSONObject> responseVO = new ResponseVO<>(ErrorCode.OK, "ok");
        JSONObject object = new JSONObject();
        EnergyType energyType = new EnergyType();
        energyType.setType(2);
        List<EnergyType> energyTypes = energyTypeBiz.selectList(energyType);
        Map<String, BigDecimal> energyTypeMap = energyTypes.stream().collect(Collectors.toMap(EnergyType::getCode, EnergyType::getZbxs));


        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Long upload_time = DateUtil.getYMLongTime(cal.getTime());
        BigDecimal total = BigDecimal.ZERO;
        for (EnergyDataCode value : EnergyDataCode.values()) {
            if (EnergyDataCode.ENERGY == value || EnergyDataCode.STEAM == value || EnergyDataCode.WATER == value ) {
                continue;
            }
            String tableName = EnergyTypeRouter.getAnalysisTableName(value, AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
            EnergyQueryVO energyQueryVO = new EnergyQueryVO();
            energyQueryVO.setTableName(tableName);
            energyQueryVO.setUpload_date(upload_time);
            List<AnalysisCompany> companyList = energyBiz.getCompanyList(energyQueryVO);

            BigDecimal rate = energyTypeMap.get(value.getDataCode()) == null ? BigDecimal.valueOf(0) : energyTypeMap.get(value.getDataCode());
            switch (value) {
                case POWER:
                    rate = rate.divide(BigDecimal.valueOf(10000));
                    break;
                case GAS:
                    rate = rate.divide(BigDecimal.valueOf(10000));
                    break;
                case COAL:
                    rate = rate;
                    break;
                case OTHER:
                    rate = BigDecimal.valueOf(1);
                    break;
                default:
            }

            BigDecimal dataValue = (companyList != null && companyList.size() > 0) ? companyList.get(0).getData_value().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
            String name = EnergyTypeEnum.parse(value.getDataCode()).getName();
            object.put(((name.length() == 1 || name.length() == 3) ? "用" + name.substring(name.length() - 1) : name) + "总量", dataValue);
            total = total.add(dataValue);
        }
        object.put("total", total);
        responseVO.setData(object);
        return responseVO;
    }

    @ApiOperation(value = "年度用能概况", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getConsumptionOfYear", method = RequestMethod.POST)
    public ResponseVO<JSONObject> getConsumptionOfYear() {
        ResponseVO<JSONObject> responseVO = new ResponseVO<>(ErrorCode.OK, "ok");
        JSONObject object = new JSONObject();
        EnergyType energyType = new EnergyType();
        energyType.setType(2);
        List<EnergyType> energyTypes = energyTypeBiz.selectList(energyType);
        Map<String, BigDecimal> energyTypeMap = energyTypes.stream().collect(Collectors.toMap(EnergyType::getCode, EnergyType::getZbxs));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Long endTime = DateUtil.getYMLongTime(cal.getTime());
        cal.set(Calendar.MONTH, 0);
        Long startTime = DateUtil.getYMLongTime(cal.getTime());
        BigDecimal total = BigDecimal.ZERO;
        for (EnergyDataCode value : EnergyDataCode.values()) {
            if (EnergyDataCode.ENERGY == value || EnergyDataCode.STEAM == value || EnergyDataCode.WATER == value ) {
                continue;
            }
            String tableName = EnergyTypeRouter.getAnalysisTableName(value, AnalysisTypeEnum.MONTH, AnalysisEntityEnum.COMPANY);
            EnergyQueryVO energyQueryVO = new EnergyQueryVO();
            energyQueryVO.setTableName(tableName);
            energyQueryVO.setStartDate(startTime);
            energyQueryVO.setEndDate(endTime);
            AnalysisTerminal analysisTerminal = energyBiz.getSumTerminalConsumption(energyQueryVO);

            BigDecimal rate = energyTypeMap.get(value.getDataCode()) == null ? BigDecimal.valueOf(0) : energyTypeMap.get(value.getDataCode());
            switch (value) {
                case POWER:
                    rate = rate.divide(BigDecimal.valueOf(10000));
                    break;
                case GAS:
                    rate = rate.divide(BigDecimal.valueOf(10000));
                    break;
                case COAL:
                    rate = rate;
                    break;
                case OTHER:
                    rate = BigDecimal.valueOf(1);
                    break;
                default:
            }
            BigDecimal dataValue = (analysisTerminal != null) ? analysisTerminal.getData_value().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
            String name = EnergyTypeEnum.parse(value.getDataCode()).getName();
            object.put(((name.length() == 1 || name.length() == 3) ? "用" + name.substring(name.length() - 1) : name) + "总量", dataValue);
            total = total.add(dataValue);
        }
        object.put("total", total);

        responseVO.setData(object);
        return responseVO;
    }

    @ApiOperation(value = "能源消耗量趋势图", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getConsumptionChart", method = RequestMethod.POST)
    public ResponseVO<LineVO<BigDecimal>> getConsumptionChart(@RequestBody ConsumptionChartQueryVO queryVO) throws IllegalAccessException {
        ResponseVO<LineVO<BigDecimal>> responseVO = new ResponseVO<>();
        LineVO<BigDecimal> lineVO = new LineVO<>();
        ChartDataVO<BigDecimal> chartDataVO = new ChartDataVO<>();
        List<String> categories = new ArrayList<>();
        List<ChartKeyValuePairVO<BigDecimal>> series = new ArrayList<>();

        EnergyType energyType = new EnergyType();
        energyType.setType(2);
        List<EnergyType> energyTypes = energyTypeBiz.selectList(energyType);
        Map<String, BigDecimal> energyTypeMap = energyTypes.stream().collect(Collectors.toMap(EnergyType::getCode, EnergyType::getZbxs));
        BigDecimal rate = energyTypeMap.get(queryVO.getDataCode()) == null ? BigDecimal.valueOf(0) : energyTypeMap.get(queryVO.getDataCode());
        switch (EnergyDataCode.parse(queryVO.getDataCode())) {
            case POWER:
                rate = rate.divide(BigDecimal.valueOf(10000));
                break;
            case GAS:
                rate = rate.divide(BigDecimal.valueOf(10000));
                break;
            case STEAM:
                rate = rate.divide(BigDecimal.valueOf(100));
                break;
            default:
        }

        String tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(queryVO.getDataCode()), AnalysisTypeEnum.HOUR, AnalysisEntityEnum.COMPANY);
        Calendar calendar = Calendar.getInstance();
        String endTime = DateUtil.getLocalDateStr(calendar.getTime(), formatterYMD);
        calendar.add(Calendar.DATE, -1);
        String startTime = DateUtil.getLocalDateStr(calendar.getTime(), formatterYMD);
        if (queryVO.getTime() == 2) {
            tableName = EnergyTypeRouter.getAnalysisTableName(EnergyDataCode.parse(queryVO.getDataCode()), AnalysisTypeEnum.DATE, AnalysisEntityEnum.COMPANY);
            calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            endTime = DateUtil.getLocalDateStr(calendar.getTime(), formatter);
            calendar.add(Calendar.MONTH, -1);
            startTime = DateUtil.getLocalDateStr(calendar.getTime(), formatter);
        }
        EnergyQueryVO energyQueryVO = new EnergyQueryVO();
        energyQueryVO.setTableName(tableName);
        energyQueryVO.setStartDate(Long.valueOf(queryVO.getTime() == 1 ? endTime + "00" : endTime + "01"));
        energyQueryVO.setEndDate(Long.valueOf(queryVO.getTime() == 1 ? endTime + "23" : endTime + "31"));
        List<AnalysisCompany> companyList = energyBiz.getCompanyList(energyQueryVO);
        Map<Long, BigDecimal> timeDataValueMap = companyList.stream().collect(Collectors.toMap(AnalysisCompany::getUpload_date, AnalysisCompany::getData_value));

        energyQueryVO.setStartDate(Long.valueOf(queryVO.getTime() == 1 ? startTime + "00" : startTime + "01"));
        energyQueryVO.setEndDate(Long.valueOf(queryVO.getTime() == 1 ? startTime + "23" : startTime + "31"));
        List<AnalysisCompany> lastCompanyList = energyBiz.getCompanyList(energyQueryVO);
        Map<Long, BigDecimal> lastTimeDataValueMap = lastCompanyList.stream().collect(Collectors.toMap(AnalysisCompany::getUpload_date, AnalysisCompany::getData_value));


        ChartKeyValuePairVO<BigDecimal> thisPairVO = new ChartKeyValuePairVO<>();
        List<BigDecimal> thisList = new ArrayList<>();
        ChartKeyValuePairVO<BigDecimal> lastPairVO = new ChartKeyValuePairVO<>();
        List<BigDecimal> lastList = new ArrayList<>();
        switch (queryVO.getTime()) {
            case 1://日
                for (int i = 0; i < 24; i++) {
                    categories.add((i < 10 ? "0" + i + ":00" : i + ":00"));
                    //今日
                    Long today = Long.valueOf(endTime + (i < 10 ? "0" + i : i + ""));
                    //昨日
                    Long yesterday = Long.valueOf(startTime + (i < 10 ? "0" + i : i + ""));
                    thisList.add(timeDataValueMap.get(today) == null ? BigDecimal.ZERO : timeDataValueMap.get(today).multiply(rate));
                    lastList.add(lastTimeDataValueMap.get(yesterday) == null ? BigDecimal.ZERO : lastTimeDataValueMap.get(yesterday).multiply(rate));
                }
                thisPairVO.setName("今天");
                lastPairVO.setName("昨天");
                break;
            case 2:
                for (int i = 1; i <= 31; i += 5) {
                    categories.add((i < 10 ? "0" + i : i + ""));
                    //当月
                    Long thisMonth = Long.valueOf(endTime + (i < 10 ? "0" + i : i + ""));
                    //前月
                    Long lastMonth = Long.valueOf(startTime + (i < 10 ? "0" + i : i + ""));
                    thisList.add(timeDataValueMap.get(thisMonth) == null ? BigDecimal.ZERO : timeDataValueMap.get(thisMonth).multiply(rate));
                    lastList.add(lastTimeDataValueMap.get(lastMonth) == null ? BigDecimal.ZERO : lastTimeDataValueMap.get(lastMonth).multiply(rate));
                }
                thisPairVO.setName("当月");
                lastPairVO.setName("前月");
                break;
            default:
        }

        thisPairVO.setData(thisList);
        lastPairVO.setData(lastList);
        series.add(thisPairVO);
        series.add(lastPairVO);
        chartDataVO.setCategories(categories);
        chartDataVO.setSeries(series);
        chartDataVO.setYzTitle("单位（吨标准煤）");
        lineVO.setChartData(chartDataVO);
        responseVO.setData(lineVO);
        return responseVO;
    }

    @ApiOperation(value = "工作任务列表", consumes = "application/json", response = ListResponseVO.class)
    @RequestMapping(value = "/workTaskInfoList", consumes = "application/json", method = RequestMethod.POST)
    public ListResponseVO<WorkTaskInfo> workTaskInfoList(@RequestBody WorkTaskInfo workTaskInfo) {
        ListResponseVO<WorkTaskInfo> ResponseVO = new ListResponseVO<WorkTaskInfo>(ErrorCode.OK, "ok");
        List<WorkTaskInfo> queryPageList = workTaskInfoBiz.selectList(workTaskInfo);
        ResponseVO.setData(queryPageList);
        return ResponseVO;
    }


    @ApiOperation(value = "国家平台交互日志列表", consumes = "application/json", response = ListResponseVO.class)
    @PostMapping(value = "queryUploadLog", consumes = "application/json")
    public ListResponseVO<UploadLogVO> queryUploadLog() {
        List<UploadLog> results = uploadLogBiz.queryList();
        List<UploadLogVO> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (UploadLog uploadLog : results) {
            UploadLogVO logVO = new UploadLogVO();
            logVO.setId(uploadLog.getId());
            logVO.setStatus(uploadLog.isStatus());
            logVO.setAction(uploadLog.getAction());
            logVO.setUploadTime(format.format(uploadLog.getUploadTime()));
            logVO.setSuccess(uploadLog.isSuccess());
            logVO.setRetryTime(uploadLog.getRetryTime());
            logVO.setRetryFlag(uploadLog.getRetryTime() > 0);
            list.add(logVO);
        }
        ListResponseVO<UploadLogVO> returnValue = new ListResponseVO<>(ErrorCode.OK, "ok");
        returnValue.setData(list);
        return returnValue;
    }

    @ApiOperation(value = "能源种类下拉（有实时能耗）", response = ResponseVO.class)
    @RequestMapping(value = "/getEnergyTypes", method = RequestMethod.GET)
    public ResponseVO<SysDictionaryVO<CodeVO>> getEnergyTypes() {
        SysDictionaryVO<CodeVO> data = new SysDictionaryVO<>();
        List<CodeVO> list = new ArrayList<CodeVO>();
        for (EnergyTypeEnum value : EnergyTypeEnum.values()) {
            if(EnergyTypeEnum.ENERGY == value ||
                    EnergyTypeEnum.COAL == value ||
                    EnergyTypeEnum.OTHER == value ||
                    EnergyTypeEnum.WATER == value) {
                continue;
            }
            CodeVO codeVO = new CodeVO();
            codeVO.setCode(value.getDataCode());
            codeVO.setName(value.getName());
            list.add(codeVO);
        }
        data.setList(list);
        return new ResponseVO<SysDictionaryVO<CodeVO>>(ErrorCode.OK, "ok", data);
    }

}
