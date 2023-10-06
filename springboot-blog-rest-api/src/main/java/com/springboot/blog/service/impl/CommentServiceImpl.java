package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDTo;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl  implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDTo createComment(long postId, CommentDTo commentDTo) {
        Comment comment = mapToEntity(commentDTo);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDTo> getCommentsByPostId(long postId) {
        // retrive comments by postid
        List<Comment> comments = commentRepository.findByPostId(postId);

        // converting list of comment entity to list of DTO
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTo getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!(comment.getPost().getId().equals(post.getId()))) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post ");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDTo updateComment(Long postId, long commentId, CommentDTo commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!(comment.getPost().getId().equals(post.getId()))) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post ");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        return null;
    }
    private CommentDTo mapToDto(Comment comment) {
        CommentDTo commentDTo = new CommentDTo();
        commentDTo.setId(comment.getId());
        commentDTo.setName(comment.getName());
        commentDTo.setEmail(comment.getEmail());
        commentDTo.setBody(comment.getBody());
        return commentDTo;
    }

    private Comment mapToEntity(CommentDTo commentDTo)
    {
        Comment comment = new Comment();
        comment.setId(commentDTo.getId());
        comment.setName(commentDTo.getName());
        comment.setEmail(commentDTo.getEmail());
        comment.setBody(commentDTo.getBody());
        return comment;
    }
}