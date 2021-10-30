package com.github.changebooks.quartz.ui.service.impl;

import com.github.changebooks.quartz.ui.model.Task;
import com.github.changebooks.quartz.ui.service.TriggerService;
import com.github.changebooks.quartz.ui.util.Constants;
import com.github.changebooks.quartz.ui.util.TaskUtils;
import com.google.common.base.Preconditions;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author changebooks
 */
@Service
public class TriggerServiceImpl implements TriggerService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public Task getTask(String name, String group) throws SchedulerException {
        TaskUtils.checkArgument(name, group);

        TriggerKey key = new TriggerKey(name, group);

        Trigger trigger = scheduler.getTrigger(key);
        Trigger.TriggerState state = scheduler.getTriggerState(key);

        Task result = TaskUtils.map(trigger);
        if (Objects.nonNull(result)) {
            result.setState(state);
        }

        return result;
    }

    @Override
    public boolean isExist(String name, String group) throws SchedulerException {
        TaskUtils.checkArgument(name, group);

        TriggerKey key = new TriggerKey(name, group);

        Trigger trigger = scheduler.getTrigger(key);
        return Objects.nonNull(trigger);
    }

    @Override
    public Trigger build(Task task) {
        Preconditions.checkNotNull(task, Constants.MSG_TASK_REQUIRED);

        String name = task.getName();
        String group = task.getGroup();
        String cronExpression = task.getCronExpression();

        TaskUtils.checkArgument(name, group);
        Preconditions.checkArgument(!StringUtils.isEmpty(cronExpression), Constants.MSG_CRON_EXPRESSION_REQUIRED);

        return TriggerBuilder.
                newTrigger().
                withIdentity(name, group).
                withDescription(task.getDescription()).
                withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).
                build();
    }

    @Override
    public boolean unSchedule(String name, String group) throws SchedulerException {
        TaskUtils.checkArgument(name, group);

        TriggerKey key = new TriggerKey(name, group);
        return scheduler.unscheduleJob(key);
    }

    @Override
    public void resume(String name, String group) throws SchedulerException {
        TaskUtils.checkArgument(name, group);

        TriggerKey key = new TriggerKey(name, group);
        scheduler.resumeTrigger(key);
    }

    @Override
    public void pause(String name, String group) throws SchedulerException {
        TaskUtils.checkArgument(name, group);

        TriggerKey key = new TriggerKey(name, group);
        scheduler.pauseTrigger(key);
    }

}
