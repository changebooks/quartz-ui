package com.github.changebooks.quartz.ui.service;

import com.github.changebooks.quartz.ui.model.Task;
import com.github.changebooks.quartz.ui.util.TaskUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 执行指令
 *
 * @author changebooks
 */
public final class JobExecutor implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobExecutor.class);

    @Override
    public void execute(JobExecutionContext context) {
        if (Objects.isNull(context)) {
            LOGGER.error("execute failed, context can't be null");
            return;
        }

        JobDetail job = context.getJobDetail();
        if (Objects.isNull(job)) {
            LOGGER.error("execute failed, job can't be null, context: {}", context);
            return;
        }

        Task task = TaskUtils.map(job);
        if (Objects.isNull(task)) {
            LOGGER.error("execute failed, task can't be null, context: {}, job: {}", context, job);
            return;
        }

        try {
            boolean r = TaskUtils.execute(task);
        } catch (Throwable tr) {
            LOGGER.error("execute failed, execute task throwable, context: {}, job: {}, task: {}, throwable: ",
                    context, job, task, tr);
        }
    }

}
