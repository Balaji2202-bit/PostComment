package com.springboot.blog.controller;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDTo;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController
{
    private CommentService commentService;

    public CommentController(CommentService commentService)
    {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTo> createComment(@PathVariable(value = "postId" ) long postId,@RequestBody CommentDTo commentDTo)
    {
        return  new ResponseEntity<>(commentService.createComment(postId, commentDTo), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTo> getCommentsPostId(@PathVariable(value = "postId") Long postId)
    {
        return  commentService.getCommentsByPostId(postId);
    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTo> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commandId)
    {
        CommentDTo commentDTo=commentService.getCommentById(postId,commandId);
        return new ResponseEntity<>(commentDTo, HttpStatus.OK );
    }
}
