package com.github.changebooks.quartz.ui.controller;

import com.github.changebooks.quartz.ui.model.Task;
import com.github.changebooks.quartz.ui.model.TaskCreate;
import com.github.changebooks.quartz.ui.service.TaskService;
import com.github.changebooks.quartz.ui.util.Result;
import com.github.changebooks.quartz.ui.util.TaskUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 任务
 *
 * @author changebooks
 */
@RequestMapping(value = {"task"})
@RestController
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService service;

    /**
     * 全部分组
     */
    @GetMapping(value = "/group")
    public Result<List<String>> group() throws SchedulerException {
        List<String> list = service.getGroups();
        return Result.toSuccess(list);
    }

    /**
     * 指定分组的全部任务
     */
    @GetMapping(value = "/list")
    public Result<List<Task>> list(@RequestParam("group") String group) throws SchedulerException {
        List<Task> list = service.findList(group);
        return Result.toSuccess(list);
    }

    /**
     * 获取任务
     */
    @GetMapping(value = "/view")
    public Result<Task> view(@RequestParam("name") String name, @RequestParam("group") String group) throws SchedulerException {
        Task task = service.getTask(name, group);
        return Result.toSuccess(task);
    }

    /**
     * 新建任务
     */
    @PostMapping(value = "/create")
    public Result<Date> create(@RequestBody TaskCreate req) throws SchedulerException {
        LOGGER.info("create trace, req: {}", req);

        Task task = TaskUtils.map(req);

        Date firstTime = service.create(task);
        return Result.toSuccess(firstTime);
    }

    /**
     * 删除任务
     */
    @DeleteMapping(value = "/delete")
    public Result<Boolean> delete(@RequestParam("name") String name, @RequestParam("group") String group) throws SchedulerException {
        LOGGER.info("delete trace, name: {}, group: {}", name, group);

        boolean r = service.delete(name, group);
        return Result.toSuccess(r);
    }

    /**
     * 恢复任务
     */
    @PostMapping(value = "/resume")
    public Result<?> resume(@RequestParam("name") String name, @RequestParam("group") String group) throws SchedulerException {
        LOGGER.info("resume trace, name: {}, group: {}", name, group);

        service.resume(name, group);
        return Result.toSuccess(null);
    }

    /**
     * 暂停任务
     */
    @PostMapping(value = "/pause")
    public Result<?> pause(@RequestParam("name") String name, @RequestParam("group") String group) throws SchedulerException {
        LOGGER.info("pause trace, name: {}, group: {}", name, group);

        service.pause(name, group);
        return Result.toSuccess(null);
    }

}
