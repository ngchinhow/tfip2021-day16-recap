package com.tfip2021.module2.controller;

import java.util.ArrayList;

import com.tfip2021.module2.model.ToDoItem;
import com.tfip2021.module2.service.ToDoRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(
    path = { "/item" },
    produces = { "text/html" }
)
public class ToDoResource {
    private ArrayList<ToDoItem> todoList = new ArrayList<ToDoItem>();
    
    @Autowired
    private ToDoRedis service;

    @PostMapping(path = { "/list" })
    public String addToDoItem(@ModelAttribute ToDoItem todoItem, Model model) {
        todoList.add(todoItem);
        model.addAttribute("todoitem", new ToDoItem());
        model.addAttribute("list", todoList);
        return "index";
    }

    @PostMapping(path = { "/database" })
    public String saveToDatabase(Model model) {
        model.addAttribute("list", todoList);
        service.saveToDoList(todoList);
        return "saved";
    }
}
