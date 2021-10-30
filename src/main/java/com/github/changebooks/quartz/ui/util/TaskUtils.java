package com.github.changebooks.quartz.ui.util;

import com.github.changebooks.quartz.ui.model.Task;
import com.github.changebooks.quartz.ui.model.TaskCreate;
import com.google.common.base.Preconditions;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * 任务
 *
 * @author changebooks
 */
public final class TaskUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskUtils.class);

    private TaskUtils() {
    }

    /**
     * 执行指令
     *
     * @param task Task
     * @return 成功？
     */
    public static boolean execute(Task task) throws IOException {
        Preconditions.checkNotNull(task, Constants.MSG_TASK_REQUIRED);

        String url = task.getUrl();
        Preconditions.checkArgument(!StringUtils.isEmpty(url), Constants.MSG_URL_REQUIRED);

        HttpUtils.Result result = HttpUtils.reqPost(url);
        if (result.isOk()) {
            LOGGER.info("execute successful, task: {}, response: {}", task, result.getData());
            return true;
        } else {
            LOGGER.error("execute failed, http post failed, task: {}, statusCode: {}, response: {}",
                    task, result.getStatusCode(), result.getData());
            return false;
        }
    }

    /**
     * 校验参数
     */
    public static void checkArgument(Task task) {
        Preconditions.checkNotNull(task, Constants.MSG_TASK_REQUIRED);

        String name = task.getName();
        String group = task.getGroup();
        String cronExpression = task.getCronExpression();
        String url = task.getUrl();

        checkArgument(name, group);
        Preconditions.checkArgument(!StringUtils.isEmpty(cronExpression), Constants.MSG_CRON_EXPRESSION_REQUIRED);
        Preconditions.checkArgument(!StringUtils.isEmpty(url), Constants.MSG_URL_REQUIRED);
    }

    /**
     * 校验参数
     */
    public static void checkArgument(String name, String group) {
        Preconditions.checkArgument(!StringUtils.isEmpty(name), Constants.MSG_NAME_REQUIRED);
        Preconditions.checkArgument(!StringUtils.isEmpty(group), Constants.MSG_GROUP_REQUIRED);
    }

    /**
     * TaskCreate -> Task
     */
    public static Task map(TaskCreate req) {
        if (Objects.isNull(req)) {
            return null;
        }

        String name = req.getName();
        String group = req.getGroup();
        String description = req.getDescription();
        String url = req.getUrl();
        String cronExpression = req.getCronExpression();

        name = StringUtils.trimAllWhitespace(name);
        group = StringUtils.trimAllWhitespace(group);
        description = StringUtils.trimWhitespace(description);
        url = StringUtils.trimWhitespace(url);
        cronExpression = StringUtils.trimWhitespace(cronExpression);

        Task result = new Task();

        result.setName(name);
        result.setGroup(group);
        result.setDescription(description);
        result.setUrl(url);
        result.setCronExpression(cronExpression);

        return result;
    }

    /**
     * JobDetail -> Task
     */
    public static Task map(JobDetail job) {
        if (Objects.isNull(job)) {
            return null;
        }

        String name = null;
        String group = null;
        String description = job.getDescription();
        String url = null;

        JobKey key = job.getKey();
        if (Objects.nonNull(key)) {
            name = key.getName();
            group = key.getGroup();
        }

        JobDataMap dataMap = job.getJobDataMap();
        if (Objects.nonNull(dataMap)) {
            url = dataMap.getString(Constants.KEY_URL);
        }

        Task result = new Task();

        result.setName(name);
        result.setGroup(group);
        result.setDescription(description);
        result.setUrl(url);

        return result;
    }

    /**
     * Trigger -> Task
     */
    public static Task map(Trigger trigger) {
        if (Objects.isNull(trigger)) {
            return null;
        }

        String name = null;
        String group = null;
        String description = trigger.getDescription();
        String cronExpression = null;
        Date prevTime = trigger.getPreviousFireTime();
        Date nextTime = trigger.getNextFireTime();

        TriggerKey key = trigger.getKey();
        if (Objects.nonNull(key)) {
            name = key.getName();
            group = key.getGroup();
        }

        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            cronExpression = cronTrigger.getCronExpression();
        }

        Task result = new Task();

        result.setName(name);
        result.setGroup(group);
        result.setDescription(description);
        result.setCronExpression(cronExpression);
        result.setPrevTime(prevTime);
        result.setNextTime(nextTime);

        return result;
    }

}
