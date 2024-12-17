package com.inpocket.vitaverse.forum.service;

import com.inpocket.vitaverse.forum.entity.Comment;
import com.inpocket.vitaverse.forum.entity.CommentLike;
import com.inpocket.vitaverse.forum.entity.Post;
import com.inpocket.vitaverse.forum.entity.Vote;
import com.inpocket.vitaverse.forum.repository.CommentLikeRepository;
import com.inpocket.vitaverse.forum.repository.CommentRepository;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentLikeServiceImp implements CommentLikeService {
    CommentLikeRepository commentLikeRepository;
    CommentRepository commentRepository;
    UserRepository userRepository;
    @Override
    public CommentLike createCommentLike(CommentLike commentLike) {
        return commentLikeRepository.save(commentLike);
    }

    @Override
    public CommentLike updateCommentLike(CommentLike commentLike) {
        return commentLikeRepository.save(commentLike);
    }

    @Override
    public Optional<CommentLike> getCommentLikeById(long commentLikeId) {
        return commentLikeRepository.findById(commentLikeId);
    }

    @Override
    public void deleteCommentLike(long commentLikeId) {
        commentLikeRepository.deleteById(commentLikeId);
    }




    @Override
    public CommentLike createOrUpdateCommentLikeAndSetUserAndComment( Long commentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found with ID: " + commentId));

        // Check if the user has already liked this comment
        Optional<CommentLike> existingCommentLike = commentLikeRepository.findByUserUserIdAndComment_CommentId(user.getUserId(), comment.getCommentId());
        if (existingCommentLike.isPresent()) {
            // User has already liked this comment, delete existing like
            commentLikeRepository.deleteById(existingCommentLike.get().getCommentLikeId());
        } else {
            // User has not liked this comment, set the comment and user for the provided like
            CommentLike commentLike = new CommentLike();
            commentLike.setComment(comment);
            commentLike.setUser(user);
            return commentLikeRepository.save(commentLike);
        }
        return null;
    }

    @Override
    public List<CommentLike> findByComment_CommentId(long comment_commentId) {
        return commentLikeRepository.findByComment_CommentId(comment_commentId);
    }

    @Override
    public Optional<CommentLike> findByUserUserIdAndComment_CommentId(Long user_userId, long comment_commentId) {
        return commentLikeRepository.findByUserUserIdAndComment_CommentId(user_userId,comment_commentId);
    }

}
