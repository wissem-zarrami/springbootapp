package com.inpocket.vitaverse.forum.service;

import com.inpocket.vitaverse.forum.entity.Post;
import com.inpocket.vitaverse.forum.entity.Vote;
import com.inpocket.vitaverse.forum.entity.VoteType;
import com.inpocket.vitaverse.forum.repository.PostRepository;
import com.inpocket.vitaverse.forum.repository.VoteRepository;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteServiceImp implements VoteService{
    VoteRepository voteRepository;
    PostRepository postRepository;
    UserRepository userRepository;

    @Override
    public Vote createVote(Vote vote) {
        return voteRepository.save(vote);
    }
    @Override
    public Vote updateVote(Vote vote) {
        return voteRepository.save(vote);
    }
    @Override
    public Optional<Vote> getVoteById(long voteId) {
        return voteRepository.findById(voteId);
    }
    @Override
    public void deleteVote(long voteId) {
        voteRepository.deleteById(voteId);
    }



    @Override
    public Vote createOrUpdateVoteAndSetUserAndPost(Vote vote, Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));

        // Check if the user has already voted on this post
        Optional<Vote> existingVote = voteRepository.findByUserUserIdAndPostPostId(user.getUserId(), post.getPostId());
        if (existingVote.isPresent()) {
            // User has already voted on this post, update the existing vote
            existingVote.get().setVoteType(vote.getVoteType()); // Update the vote type
            return voteRepository.save(existingVote.get());
        } else {
            // User has not voted on this post, set the post and user for the provided vote
            vote.setPost(post);
            vote.setUser(user);
            return voteRepository.save(vote);
        }
    }


    @Override
    public void deleteVoteByUserAndPost(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));

        // Check if the user has voted on this post
        Optional<Vote> existingVote = voteRepository.findByUserUserIdAndPostPostId(user.getUserId(), post.getPostId());
        if (existingVote.isPresent()) {
            // Delete the existing vote
            voteRepository.delete(existingVote.get());
        } else {

        }
    }

    @Override
    public Vote findByPostPostIdAndUserUserId(long post_postId, Long user_userId) {
        return voteRepository.findByPostPostIdAndUserUserId(post_postId,user_userId);
    }

    @Override
    public List<Vote> findByPostPostIdAndVoteTypeUpvote(long postId, VoteType voteType) {
        return voteRepository.findByPostPostIdAndVoteType(postId,VoteType.UPVOTE);
    }

    @Override
    public List<Vote> findByPostPostIdAndVoteTypeDownvote(long postId, VoteType voteType) {
        return voteRepository.findByPostPostIdAndVoteType(postId,VoteType.DOWNVOTE);
    }


}
