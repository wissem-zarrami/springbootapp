package com.inpocket.vitaverse.forum.service;

import com.inpocket.vitaverse.forum.entity.Comment;
import com.inpocket.vitaverse.forum.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(Comment comment);
    Comment updateComment(Comment comment);
    Optional<Comment> getCommentById(long commentId);
    void deleteComment(long commentId);


    Comment createCommentAndSetCreatorAndPost(Comment comment, Long userId, Long postId) ;
    List<Comment> findByPostPostId(long post_postId);

    Comment createReplyAndSetCreator(Long commentId, Comment reply, Long userId) ;


    }
