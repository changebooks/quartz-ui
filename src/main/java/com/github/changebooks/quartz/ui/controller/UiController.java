package com.github.changebooks.quartz.ui.controller;

import com.github.changebooks.quartz.ui.model.Task;
import com.github.changebooks.quartz.ui.service.TaskService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 用户界面
 *
 * @author changebooks
 */
@Controller
public class UiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UiController.class);

    /**
     * 模板：任务列表
     */
    private static final String TEMPLATE_INDEX = "index";

    /**
     * 模板：新建任务
     */
    private static final String TEMPLATE_CREATE = "create";

    /**
     * 分组列表
     */
    private static final String GROUP_LIST = "groupList";

    /**
     * 当前分组
     */
    private static final String SELECTED_GROUP = "selectedGroup";

    /**
     * 任务列表
     */
    private static final String TASK_LIST = "taskList";

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = TEMPLATE_INDEX)
    public String index(String group, Model model) {
        List<String> groupList = null;
        String selectedGroup = null;
        List<Task> taskList = null;

        try {
            groupList = taskService.getGroups();

            selectedGroup = StringUtils.trimAllWhitespace(group);
            if (!StringUtils.isEmpty(selectedGroup)) {
                taskList = taskService.findList(selectedGroup);
            }
        } catch (SchedulerException e) {
            LOGGER.error("index failed, group: {}, throwable: ", group, e);
        }

        model.addAttribute(GROUP_LIST, groupList);
        model.addAttribute(SELECTED_GROUP, selectedGroup);
        model.addAttribute(TASK_LIST, taskList);

        return TEMPLATE_INDEX;
    }

    @RequestMapping(value = TEMPLATE_CREATE)
    public String create() {
        return TEMPLATE_CREATE;
    }

}
