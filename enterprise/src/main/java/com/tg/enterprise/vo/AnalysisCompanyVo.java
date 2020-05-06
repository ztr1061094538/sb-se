package com.tg.enterprise.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jikai.sun on 2018/4/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisCompanyVo {
    private Integer enterprise_id;
//    private String type;
    private String nowData;
    private String paramStartTime;
    private String paramEndTime;
}
