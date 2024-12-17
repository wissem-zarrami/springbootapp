package com.inpocket.vitaverse.forum.controller;

import com.inpocket.vitaverse.forum.entity.Vote;
import com.inpocket.vitaverse.forum.entity.VoteType;
import com.inpocket.vitaverse.forum.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/Votes")
public class VoteController {
    VoteService voteService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Vote createVote(@RequestBody Vote vote) {
        return voteService.createVote(vote);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Vote updateVote(@RequestBody Vote vote) {
        return voteService.updateVote(vote);
    }

    @GetMapping("/{voteId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Vote> getVoteById(@PathVariable("voteId") long voteId) {
        return voteService.getVoteById(voteId);
    }

    @DeleteMapping("/delete/{voteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVote(@PathVariable("voteId") long voteId) {
        voteService.deleteVote(voteId);
    }


    @PostMapping("/createOrUpdate/{postId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Vote createOrUpdateVote(@RequestBody Vote vote,
                                   @PathVariable("postId") Long postId,
                                   @PathVariable("userId") Long userId) {
        return voteService.createOrUpdateVoteAndSetUserAndPost(vote, postId, userId);
    }

    @DeleteMapping("/deleteByUserAndPost/{postId}/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVoteByUserAndPost(@PathVariable Long postId, @PathVariable Long userId) {
        voteService.deleteVoteByUserAndPost(postId, userId);
    }


    @GetMapping("/{postId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Vote findByPostPostIdAndUserUserId(@PathVariable("postId") long postId, @PathVariable("userId") long userId) {
        return voteService.findByPostPostIdAndUserUserId(postId, userId);
    }

    @GetMapping("/upvote/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> findByPostPostIdAndVoteTypeUpvote(@PathVariable("postId") long postId) {
        return voteService.findByPostPostIdAndVoteTypeUpvote(postId, VoteType.UPVOTE);
    }

    @GetMapping("/downvote/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> findByPostPostIdAndVoteTypeDownvote(@PathVariable("postId") long postId) {
        return voteService.findByPostPostIdAndVoteTypeDownvote(postId, VoteType.DOWNVOTE);
    }
}

