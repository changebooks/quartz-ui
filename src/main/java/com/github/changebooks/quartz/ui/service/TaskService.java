package com.github.changebooks.quartz.ui.service;

import com.github.changebooks.quartz.ui.model.Task;
import org.quartz.SchedulerException;

import java.util.Date;
import java.util.List;

/**
 * 任务
 *
 * @author changebooks
 */
public interface TaskService {
    /**
     * 全部分组
     *
     * @return list
     */
    List<String> getGroups() throws SchedulerException;

    /**
     * 指定分组的全部任务
     *
     * @param group 分组
     * @return list
     */
    List<Task> findList(String group) throws SchedulerException;

    /**
     * 获取任务
     *
     * @param name  名称
     * @param group 分组
     * @return Task
     */
    Task getTask(String name, String group) throws SchedulerException;

    /**
     * 新建
     *
     * @param task Task
     * @return 首次执行时间
     */
    Date create(Task task) throws SchedulerException;

    /**
     * 删除
     *
     * @param name  名称
     * @param group 分组
     * @return 成功？
     */
    boolean delete(String name, String group) throws SchedulerException;

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
