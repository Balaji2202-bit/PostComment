package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDTo;

import java.util.List;

public interface CommentService
{
    CommentDTo createComment(long postId, CommentDTo commentDTo);
    List<CommentDTo> getCommentsByPostId(long postId);
    CommentDTo getCommentById(Long postId,Long commentId);
    CommentDTo updateComment(Long postId, long commentId, CommentDTo commentRequest);
}
