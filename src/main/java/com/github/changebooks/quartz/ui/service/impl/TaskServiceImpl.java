package com.github.changebooks.quartz.ui.service.impl;

import com.github.changebooks.quartz.ui.model.Task;
import com.github.changebooks.quartz.ui.service.JobService;
import com.github.changebooks.quartz.ui.service.TaskService;
import com.github.changebooks.quartz.ui.service.TriggerService;
import com.github.changebooks.quartz.ui.util.Constants;
import com.github.changebooks.quartz.ui.util.TaskUtils;
import com.google.common.base.Preconditions;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author changebooks
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobService jobService;

    @Autowired
    private TriggerService triggerService;

    @Override
    public List<String> getGroups() throws SchedulerException {
        return jobService.getGroups();
    }

    @Override
    public List<Task> findList(String group) throws SchedulerException {
        List<Task> result = new ArrayList<>();

        List<String> jobNames = jobService.getNames(group);
        if (CollectionUtils.isEmpty(jobNames)) {
            return result;
        }

        for (String name : jobNames) {
            if (StringUtils.isEmpty(name)) {
                continue;
            }

            Task task = getTask(name, group);
            if (Objects.isNull(task)) {
                continue;
            }

            result.add(task);
        }

        return result;
    }

    @Override
    public Task getTask(String name, String group) throws SchedulerException {
        Task result = jobService.getTask(name, group);
        if (Objects.isNull(result)) {
            return null;
        }

        Task triggerTask = triggerService.getTask(name, group);
        if (Objects.nonNull(triggerTask)) {
            result.setCronExpression(triggerTask.getCronExpression());
            result.setState(triggerTask.getState());
            result.setPrevTime(triggerTask.getPrevTime());
            result.setNextTime(triggerTask.getNextTime());
        }

        return result;
    }

    @Override
    public Date create(Task task) throws SchedulerException {
        TaskUtils.checkArgument(task);

        String name = task.getName();
        String group = task.getGroup();

        Preconditions.checkArgument(!jobService.isExist(name, group), Constants.MSG_JOB_DUPLICATED);
        Preconditions.checkArgument(!triggerService.isExist(name, group), Constants.MSG_TRIGGER_DUPLICATED);

        JobDetail job = jobService.build(task);
        Trigger trigger = triggerService.build(task);

        return scheduler.scheduleJob(job, trigger);
    }

    @Override
    public boolean delete(String name, String group) throws SchedulerException {
        pause(name, group);

        return triggerService.unSchedule(name, group) && jobService.delete(name, group);
    }

    @Override
    public void resume(String name, String group) throws SchedulerException {
        jobService.resume(name, group);
        triggerService.resume(name, group);
    }

    @Override
    public void pause(String name, String group) throws SchedulerException {
        jobService.pause(name, group);
        triggerService.pause(name, group);
    }

}
