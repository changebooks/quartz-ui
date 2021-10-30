package com.github.changebooks.quartz.ui.util;

import org.apache.http.Consts;

import java.nio.charset.Charset;

/**
 * 常量
 *
 * @author changebooks
 */
public interface Constants {
    /**
     * 字符编码
     */
    Charset CHARSET = Consts.UTF_8;

    /**
     * 请求接口
     */
    String KEY_URL = "URL";

    /**
     * 错误码
     */
    int SUCCESS_NUM = 0;
    int METHOD_NOT_ALLOWED = 405;
    int SYSTEM_RUN_ERR = 500;
    int ARGS_ERR = 1001;

    /**
     * 错误信息
     */
    String MSG_SUCCESS = "OK";
    String MSG_SYSTEM_RUN_ERR = "系统运行异常";
    String MSG_ARGS_ERR = "参数错误";
    String MSG_NAME_REQUIRED = "名称必须";
    String MSG_GROUP_REQUIRED = "分组必须";
    String MSG_URL_REQUIRED = "请求接口必须";
    String MSG_CRON_EXPRESSION_REQUIRED = "时间表达式必须";
    String MSG_TASK_REQUIRED = "任务必须";
    String MSG_JOB_DUPLICATED = "指令已存在";
    String MSG_TRIGGER_DUPLICATED = "触发器已存在";

}
