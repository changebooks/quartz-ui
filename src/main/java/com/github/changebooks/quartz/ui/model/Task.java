package com.github.changebooks.quartz.ui.model;

import com.github.changebooks.quartz.ui.util.JsonParser;
import org.quartz.Trigger;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务
 *
 * @author changebooks
 */
public final class Task implements Serializable {
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

    /**
     * 当前状态
     */
    private Trigger.TriggerState state;

    /**
     * 上次执行时间
     */
    private Date prevTime;

    /**
     * 下次执行时间
     */
    private Date nextTime;

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

    public Trigger.TriggerState getState() {
        return state;
    }

    public void setState(Trigger.TriggerState state) {
        this.state = state;
    }

    public Date getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(Date prevTime) {
        this.prevTime = prevTime;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

}
