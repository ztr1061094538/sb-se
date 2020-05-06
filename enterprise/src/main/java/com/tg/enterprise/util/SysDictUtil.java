package com.tg.enterprise.util;

import com.tg.enterprise.config.ServerContext;
import com.tg.enterprise.vo.CodeNode;
import com.tg.enterprise.vo.CodeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */
public class SysDictUtil {

    /**
     * 根据数据字典编号获取子项
     * @param parent_Code_no
     * @return
     */
    public static List<CodeNode> getChildListByPId(String parent_Code_no){
        List<CodeNode> childList = new ArrayList<CodeNode>();
        getChildList(childList,parent_Code_no, ServerContext.dictionary.get("dict"));
        return childList;
    }

    public static String getCodeNode(String code){
        CodeNode node = ServerContext.dictMap.get(code);
        return node.getName();
    }

    private static void getChildList(List<CodeNode> childList, String parent_Code_no,List<CodeNode> rootMenu){
        for (CodeNode codeNode:rootMenu){
            if(parent_Code_no.equals(codeNode.getCode())){
                childList.addAll(codeNode.getChildList());
                return;
            }else if(codeNode.getChildList()!= null&&codeNode.getChildList().size()!=0){
                getChildList(childList,parent_Code_no,codeNode.getChildList());
            }
        }
    }
    
    public static CodeVO getAreaByCode(String code)
    {
    	return ServerContext.areaMap.get(code);
    }
    
}
