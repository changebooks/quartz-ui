package com.github.changebooks.quartz.ui.controller;

import com.github.changebooks.quartz.ui.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author changebooks
 */
@RequestMapping(value = {"test"})
@RestController
public class TestController {
    /**
     * 校验
     * http://localhost:9091/test/check?a=1
     */
    @PostMapping(value = "/check")
    public Result<String> check(@RequestParam("a") String a) {
        return Result.toSuccess("check ok, a = " + a);
    }

}
