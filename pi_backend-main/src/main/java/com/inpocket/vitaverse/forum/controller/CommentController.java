package com.inpocket.vitaverse.forum.controller;

import com.inpocket.vitaverse.forum.entity.Comment;
import com.inpocket.vitaverse.forum.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/Comments")
public class CommentController {
    CommentService commentService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment){
       return commentService.createComment(comment);
    }
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Comment updateComment(@RequestBody Comment comment){
        return commentService.updateComment(comment);
    }
    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Comment> getCommentById(@PathVariable("commentId") long commentId){
        return commentService.getCommentById(commentId);
    }
    @DeleteMapping("/delete/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("commentId") long commentId){
        commentService.deleteComment(commentId);
    }

    @PostMapping("/add/{userId}/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createCommentAndSetCreatorAndPost(@RequestBody Comment comment,
                                                     @PathVariable("userId") Long userId,
                                                     @PathVariable("postId") Long postId){
        return commentService.createCommentAndSetCreatorAndPost(comment, userId, postId);
    }

    @GetMapping("byPostId/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> findByPostPostId(@PathVariable("postId") long postId){
        return commentService.findByPostPostId(postId);
    }

    @PostMapping("/addReply/{commentId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createReplyAndSetCreator(@PathVariable("commentId") Long commentId,
                                            @PathVariable("userId") Long userId,
                                            @RequestBody Comment reply) {
        return commentService.createReplyAndSetCreator(commentId, reply, userId);
    }



}
