package com.puwd.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther puwd
 * @Date 2021-1-22 11:26
 * @Description
 */
@RestController
public class ExceptionController {

    @GetMapping("/exception")
    public void s1(HttpServletRequest request) throws Exception {
        throw (Exception)request.getAttribute("exception");
    }
}
