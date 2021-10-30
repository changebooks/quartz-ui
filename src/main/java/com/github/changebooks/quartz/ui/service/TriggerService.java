package com.github.changebooks.quartz.ui.service;

import com.github.changebooks.quartz.ui.model.Task;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

/**
 * 触发器
 *
 * @author changebooks
 */
public interface TriggerService {
    /**
     * 获取触发器
     *
     * @param name  名称
     * @param group 分组
     * @return Task
     */
    Task getTask(String name, String group) throws SchedulerException;

    /**
     * 存在触发器？
     *
     * @param name  名称
     * @param group 分组
     * @return 存在？
     */
    boolean isExist(String name, String group) throws SchedulerException;

    /**
     * 新建
     *
     * @param task Task
     * @return Trigger
     */
    Trigger build(Task task);

    /**
     * 删除
     *
     * @param name  名称
     * @param group 分组
     * @return 成功？
     */

    /**
     * 删除
     *
     * @param name  名称
     * @param group 分组
     * @return 成功？
     */
    boolean unSchedule(String name, String group) throws SchedulerException;

    /**
     * 恢复
     *
     * @param name  名称
     * @param group 分组
     */
    void resume(String name, String group) throws SchedulerException;

    /**
     * 暂停
     *
     * @param name  名称
     * @param group 分组
     */
    void pause(String name, String group) throws SchedulerException;

}
