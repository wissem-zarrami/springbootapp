package com.inpocket.vitaverse.forum.service;

import com.inpocket.vitaverse.forum.entity.Comment;
import com.inpocket.vitaverse.forum.entity.CommentLike;
import com.inpocket.vitaverse.forum.entity.Post;
import com.inpocket.vitaverse.forum.entity.Vote;
import com.inpocket.vitaverse.forum.repository.CommentLikeRepository;
import com.inpocket.vitaverse.forum.repository.CommentRepository;
import com.inpocket.vitaverse.forum.repository.PostRepository;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class CommentServiceImp implements CommentService {
    CommentRepository commentRepository;
    PostRepository postRepository;
    UserRepository userRepository;
    CommentLikeRepository commentLikeRepository;

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(long commentId) {
        return commentRepository.findById(commentId);
    }


   /* @Override
    public void deleteComment(long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();

            // Initialize the replies set
            Set<Comment> replies = comment.getReplies();
            if (replies != null) {
                // Delete all replies associated with the comment
                for (Comment reply : replies) {
                    // Delete any associated likes first
                    List<CommentLike> replyLikes = commentLikeRepository.findByComment_CommentId(reply.getCommentId());
                    commentLikeRepository.deleteAll(replyLikes);
                    // Remove the reply from the parent comment's replies set
                    comment.getReplies().remove(reply);
                    commentRepository.save(comment);
                }
            }

            // Delete all comment likes associated with the comment
            List<CommentLike> commentLikes = commentLikeRepository.findByComment_CommentId(commentId);
            commentLikeRepository.deleteAll(commentLikes);

            // Finally, delete the comment itself
            commentRepository.delete(comment);
        } else {
            // Handle the case where the comment with the given ID does not exist
        }
    }*/


    @Override
    public void deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            // Handle case when comment doesn't exist
            return;
        }
        List<CommentLike> commentLikes = commentLikeRepository.findByComment_CommentId(commentId);
        commentLikeRepository.deleteAll(commentLikes);
        // Delete replies recursively
        deleteReplies(comment);

        // Delete the comment itself
        commentRepository.delete(comment);
    }

    private void deleteReplies(Comment comment) {
        if (comment.getReplies() != null) {
            for (Comment reply : comment.getReplies()) {
                List<CommentLike> commentLikes = commentLikeRepository.findByComment_CommentId(reply.getCommentId());
               // commentLikeRepository.deleteAll(commentLikes);
            //    deleteReplies(reply);
                comment.setReplies(null);
                commentRepository.save(comment);
                //commentRepository.delete(reply);
            }
        }
    }






    @Override
    public Comment createCommentAndSetCreatorAndPost(Comment comment, Long userId, Long postId) {

        Post post = postRepository.findById(postId).get();
        User user = userRepository.findById(userId).get();

        comment.setUser(user);
        comment.setPost(post);
        comment.setReply(false);
        comment.setCreatedAt(new Date());

        return commentRepository.save(comment);

    }

    @Override
    public List<Comment> findByPostPostId(long post_postId) {
        return commentRepository.findByPostPostId(post_postId);
    }


    @Override
    public Comment createReplyAndSetCreator(Long commentId, Comment reply, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Set the user of the reply
        reply.setUser(user);
        reply.setReply(true);

        // Set the parent comment for the reply
        reply.setPost(comment.getPost());

        reply.setCreatedAt(new Date());


        // Add the reply to the parent comment's list of replies
        commentRepository.save(reply);
        comment.getReplies().add(reply);




        // Save the parent comment to ensure the reply gets persisted along with it
        commentRepository.save(comment);

        // Return the reply
        return reply;
    }



}
