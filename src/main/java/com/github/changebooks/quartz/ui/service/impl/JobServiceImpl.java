package com.github.changebooks.quartz.ui.service.impl;

import com.github.changebooks.quartz.ui.model.Task;
import com.github.changebooks.quartz.ui.service.JobExecutor;
import com.github.changebooks.quartz.ui.service.JobService;
import com.github.changebooks.quartz.ui.util.Constants;
import com.github.changebooks.quartz.ui.util.TaskUtils;
import com.google.common.base.Preconditions;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author changebooks
 */
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public List<String> getGroups() throws SchedulerException {
        List<String> result = scheduler.getJobGroupNames();
        if (CollectionUtils.isEmpty(result)) {
            return null;
        } else {
            return result.
                    stream().
                    filter(group -> !StringUtils.isEmpty(group)).
                    distinct().
                    collect(Collectors.toList());
        }
    }

    @Override
    public List<String> getNames(String group) throws SchedulerException {
        Preconditions.checkArgument(!StringUtils.isEmpty(group), Constants.MSG_GROUP_REQUIRED);

        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(group);

        Set<JobKey> keySet = scheduler.getJobKeys(matcher);
        if (CollectionUtils.isEmpty(keySet)) {
            return null;
        } else {
            return keySet.
                    stream().
                    map(JobKey::getName).
                    filter(name -> !StringUtils.isEmpty(name)).
                    distinct().
                    collect(Collectors.toList());
        }
    }

    @Override
    public Task getTask(String name, String group) throws SchedulerException {
        TaskUtils.checkArgument(name, group);

        JobKey key = new JobKey(name, group);

        JobDetail job = scheduler.getJobDetail(key);
        return TaskUtils.map(job);
    }

    @Override
    public boolean isExist(String name, String group) throws SchedulerException {
        TaskUtils.checkArgument(name, group);

        JobKey key = new JobKey(name, group);

        JobDetail job = scheduler.getJobDetail(key);
        return Objects.nonNull(job);
    }

    @Override
    public JobDetail build(Task task) {
        Preconditions.checkNotNull(task, Constants.MSG_TASK_REQUIRED);

        String name = task.getName();
        String group = task.getGroup();
        String url = task.getUrl();

        TaskUtils.checkArgument(name, group);
        Preconditions.checkArgument(!StringUtils.isEmpty(url), Constants.MSG_URL_REQUIRED);

        JobDataMap dataMap = new JobDataMap();
        dataMap.put(Constants.KEY_URL, url);

        return JobBuilder.
                newJob(JobExecutor.class).
                withIdentity(name, group).
                withDescription(task.getDescription()).
                setJobData(dataMap).
                storeDurably().
                build();
    }

    @Override
    public boolean delete(String name, String group) throws SchedulerException {
        TaskUtils.checkArgument(name, group);

        JobKey key = new JobKey(name, group);
        return scheduler.deleteJob(key);
    }

    @Override
    public void resume(String name, String group) throws SchedulerException {
        TaskUtils.checkArgument(name, group);

        JobKey key = new JobKey(name, group);
        scheduler.resumeJob(key);
    }

    @Override
    public void pause(String name, String group) throws SchedulerException {
        TaskUtils.checkArgument(name, group);

        JobKey key = new JobKey(name, group);
        scheduler.pauseJob(key);
    }

}
