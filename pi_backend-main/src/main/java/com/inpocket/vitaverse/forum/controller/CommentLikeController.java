package com.inpocket.vitaverse.forum.controller;

import com.inpocket.vitaverse.forum.entity.CommentLike;
import com.inpocket.vitaverse.forum.service.CommentLikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/CommentLikes")
public class CommentLikeController {
    CommentLikeService commentLikeService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentLike createCommentLike(@RequestBody CommentLike commentLike){
        return commentLikeService.createCommentLike(commentLike);
    }
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public CommentLike updateCommentLike(@RequestBody CommentLike commentLike){
        return commentLikeService.updateCommentLike(commentLike);
    }
    @GetMapping("/{commentLikeId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CommentLike> getCommentLikeById(@PathVariable("commentLikeId") long commentLikeId){
        return commentLikeService.getCommentLikeById(commentLikeId);
    }
    @DeleteMapping("/delete/{commentLikeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentLike(@PathVariable("commentLikeId")long commentLikeId){
         commentLikeService.deleteCommentLike(commentLikeId);
    }

    @PostMapping("/addOrUpdate/{commentId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentLike createOrUpdateCommentLikeAndSetUserAndComment(
                                                                     @PathVariable("commentId") Long commentId,
                                                                     @PathVariable("userId") Long userId) {
        return commentLikeService.createOrUpdateCommentLikeAndSetUserAndComment( commentId, userId);
    }


    @GetMapping("/byComment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentLike> findByComment_CommentId(@PathVariable("commentId") long commentId){
        return commentLikeService.findByComment_CommentId(commentId);
    }

    @GetMapping("/{userId}/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CommentLike> findByUserUserIdAndComment_CommentId(@PathVariable("userId") long userId
            , @PathVariable("commentId") long commentId ){
        return commentLikeService.findByUserUserIdAndComment_CommentId(userId,commentId);
    }

}
