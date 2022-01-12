package com.tfip2021.module2.service;

import java.util.ArrayList;
import java.util.List;

import com.tfip2021.module2.model.ToDoItem;

public interface ToDoRepo {
    public List<ToDoItem> listToDoItems();
    public void saveToDoList(ArrayList<ToDoItem> list);
}
