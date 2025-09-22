/**
 * 
 */
package com.example.wiki.menu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller
@RequestMapping(value = "/")
public class MenuController {

    /**
     * 初期画面表示（http://localhost:9090/）
     * @param modelAndView
     * @return modelAndView
     */
    @GetMapping(value = "/")
    ModelAndView initMenu(ModelAndView mv) {
        mv.setViewName("menu/menu");
        return mv;
    }
}
