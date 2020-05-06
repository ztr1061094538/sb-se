package com.tg.enterprise.util;

public class ErrorCode {
    public static final int OK = 0;

    public static final int INVALID_ACCOUNT = 1;

    public static final int PASSWORD_ERROR = 2;

    public static final int NOT_LOGIN = 3;

    public static final int INVALID_PARAM = 4;

    public static final int OBJECT_DUPLICATE = 5;

    public static final int OPT_UNAUTHORIZED = 6;

    public static final int ILLEGALFUNCTION = 7;

    public static final int LOGIN_NAME_HAS_EXISTED = 8;

    public static final int INNER_ERROR = 500;

    /**
     * 操作数据库失败
     */
    public static final int BG_DATABASE_ERROR = 101;

    /**
     * 获取数据失败
     */
    public static final int BG_GETDATA_ERROR = 102;

    /**
     * 删除失败，先删除关联数据
     */
    public static final int BG_DELETE_ERROR = 103;

    /**
     * 数据校验错误
     */
    public static final int VALIDATE_ERROR = 104;

    /**
     * excel解析报错
     */
    public static final int EXCEL_ERROR = 105;

    /**
     * 微服务调用出错
     */
    public static final int MICRO_SERVICE_INVOKE_ERROR = 106;

    /**
     * 上传错误
     */
    public static final int UPLOAD_ERROR = 107;

    /**
     * 创建文件夹失败
     */
    public static final int MKDIRS_FAIL = 108;
}
