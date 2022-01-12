package com.tfip2021.module2.model;

import java.io.Serializable;

public class ToDoItem implements Serializable {
    private String content;

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
