package com.inpocket.vitaverse.forum.service;



import com.inpocket.vitaverse.forum.entity.Vote;
import com.inpocket.vitaverse.forum.entity.VoteType;

import java.util.List;
import java.util.Optional;

public interface VoteService {
    Vote createVote(Vote vote);
    Vote updateVote(Vote vote);
    Optional<Vote> getVoteById(long voteId);
    void deleteVote(long voteId);
    Vote createOrUpdateVoteAndSetUserAndPost(Vote vote, Long postId, Long userId);
    void deleteVoteByUserAndPost(Long postId, Long userId) ;

    Vote findByPostPostIdAndUserUserId(long post_postId, Long user_userId);

    List<Vote> findByPostPostIdAndVoteTypeUpvote(long postId, VoteType voteType);
    List<Vote> findByPostPostIdAndVoteTypeDownvote(long postId, VoteType voteType);






}
