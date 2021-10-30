package com.github.changebooks.quartz.ui.service;

import com.github.changebooks.quartz.ui.model.Task;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 指令
 *
 * @author changebooks
 */
public interface JobService {
    /**
     * 全部分组
     *
     * @return list
     */
    List<String> getGroups() throws SchedulerException;

    /**
     * 全部名称
     *
     * @param group 分组
     * @return list
     */
    List<String> getNames(String group) throws SchedulerException;

    /**
     * 获取指令
     *
     * @param name  名称
     * @param group 分组
     * @return Task
     */
    Task getTask(String name, String group) throws SchedulerException;

    /**
     * 存在指令？
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
     * @return JobDetail
     */
    JobDetail build(Task task);

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
