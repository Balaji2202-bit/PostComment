package com.springboot.blog.payload;

import lombok.Data;

@Data
public class CommentDTo
{
    private long id;

    private String name;
    private String email;
    private String  body;
}
