package com.tfip2021.module2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tfip2021.module2.model.ToDoItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ToDoRedis implements ToDoRepo {
    private static final String TODOLIST = "todolist";
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<ToDoItem> listToDoItems() {
        return redisTemplate.opsForList().range(TODOLIST, 0, -1).stream().
            map(e -> (ToDoItem) e).
            collect(Collectors.toList());
    }

    @Override
    public void saveToDoList(ArrayList<ToDoItem> list) {
        for (ToDoItem item: list) {
            redisTemplate.opsForList().rightPush(TODOLIST, item);
        }
    }
}
