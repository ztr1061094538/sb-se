package com.tg.enterprise.vo;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */
public class CodeNode {
    private String code;
    private String parent_code;
    private String code_no;
    private String name;
    private List<CodeNode> childList;

    public String getCode_no() {
        return code_no;
    }

    public void setCode_no(String code_no) {
        this.code_no = code_no;
    }

    public String getParent_code() {
        return parent_code;
    }

    public void setParent_code(String parent_code) {
        this.parent_code = parent_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CodeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<CodeNode> childList) {
        this.childList = childList;
    }
}
