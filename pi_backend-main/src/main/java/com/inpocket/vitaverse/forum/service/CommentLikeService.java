package com.inpocket.vitaverse.forum.service;


import com.inpocket.vitaverse.forum.entity.Comment;
import com.inpocket.vitaverse.forum.entity.CommentLike;
import com.inpocket.vitaverse.forum.entity.Vote;
import com.inpocket.vitaverse.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface CommentLikeService {
    CommentLike createCommentLike(CommentLike commentLike);
    CommentLike updateCommentLike(CommentLike commentLike);
    Optional<CommentLike> getCommentLikeById(long commentLikeId);
    void deleteCommentLike(long commentLikeId);

    CommentLike createOrUpdateCommentLikeAndSetUserAndComment( Long commentId, Long userId);
    List<CommentLike> findByComment_CommentId(long comment_commentId);

    Optional<CommentLike>  findByUserUserIdAndComment_CommentId(Long user_userId, long comment_commentId);


}
