package com.github.changebooks.quartz.ui.model;

import com.github.changebooks.quartz.ui.util.JsonParser;

import java.io.Serializable;

/**
 * 新建任务
 *
 * @author changebooks
 */
public final class TaskCreate implements Serializable {
    /**
     * 名称
     */
    private String name;

    /**
     * 分组
     */
    private String group;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求接口，如：http://localhost:8080/test?a=1&b=2
     */
    private String url;

    /**
     * 时间表达式
     */
    private String cronExpression;

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

}
