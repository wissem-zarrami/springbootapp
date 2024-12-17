package com.inpocket.vitaverse.forum.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.inpocket.vitaverse.forum.entity.Comment;
import com.inpocket.vitaverse.forum.entity.Community;
import com.inpocket.vitaverse.forum.entity.Post;
import com.inpocket.vitaverse.forum.entity.Vote;
import com.inpocket.vitaverse.forum.repository.CommentRepository;
import com.inpocket.vitaverse.forum.repository.CommunityRepository;
import com.inpocket.vitaverse.forum.repository.PostRepository;
import com.inpocket.vitaverse.forum.repository.VoteRepository;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class PostServiceImp implements PostService{
    PostRepository postRepository;
    UserRepository userRepository;
     Cloudinary cloudinary;
     CommunityRepository communityRepository;
     VoteRepository voteRepository;
    CommentRepository commentRepository;

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }
    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }
    @Override
    public Optional<Post> getPostById(long postId) {
        return postRepository.findById(postId);
    }
    @Override
    public void deletePost(long postId) {

        List<Vote> associatedVotes = voteRepository.findByPostPostId(postId);

        voteRepository.deleteAll(associatedVotes);

        List<Comment> comments = commentRepository.findByPostPostId(postId);

        commentRepository.deleteAll(comments);



        postRepository.deleteById(postId);
    }
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    @Override
    public Post createPostAndSetCreatorAndCommunity(Post post, Long userId, Long communityId, MultipartFile attachment) {
        try {
            // Find user and community
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Community community = communityRepository.findById(communityId)
                    .orElseThrow(() -> new RuntimeException("Community not found"));

            // Set user and community for the post
            post.setUser(user);
            post.setCommunity(community);

            // Set createdAt to the current date and time
            post.setCreatedAt(new Date());

            // Upload attachment to Cloudinary
            if (attachment != null && !attachment.isEmpty()) {
                Map<?, ?> uploadResult = cloudinary.uploader().upload(attachment.getBytes(), ObjectUtils.emptyMap());
                String attachmentUrl = (String) uploadResult.get("url");
                post.setAttachment(attachmentUrl);
            }

            // Save the post
            return postRepository.save(post);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading attachment", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Post and setting user, community", e);
        }
    }


    @Override
    @Transactional
    public List<Post> findByCommunityCommunityId(long communityId) {
        List<Post> posts = postRepository.findByCommunityCommunityIdOrderByCreatedAtDesc(communityId);
        for (Post post : posts) {
            // Initialize lazy associations
            Hibernate.initialize(post.getUser());
            Hibernate.initialize(post.getCommunity());

        }
        return posts;
    }

    @Override

    @Transactional
    public List<Post> findByUserJoinedCommunities(long userId) {
        List<Community> communitiesJoined = communityRepository.findByMembersUserId(userId);
        List<Post> joinedCommunitiesPosts = new ArrayList<>();
        for (Community c : communitiesJoined) {

            joinedCommunitiesPosts.addAll(findByCommunityCommunityId(c.getCommunityId()));
        }


        return joinedCommunitiesPosts;
    }


@Override
    @Transactional
    public Page<Post> findByUserJoinedCommunitiesPaginated(long userId, int page, int size) {
        List<Community> communitiesJoined = communityRepository.findByMembersUserId(userId);
        List<Post> joinedCommunitiesPosts = new ArrayList<>();
        for (Community c : communitiesJoined) {
            joinedCommunitiesPosts.addAll(findByCommunityCommunityId(c.getCommunityId()));
        }

        // Sort posts by creation time in descending order
        Collections.sort(joinedCommunitiesPosts, Comparator.comparing(Post::getCreatedAt).reversed());

        // Convert the list to a Page object
        int start = Math.min((page - 1) * size, joinedCommunitiesPosts.size());
        int end = Math.min(page * size, joinedCommunitiesPosts.size());
        List<Post> paginatedPosts = joinedCommunitiesPosts.subList(start, end);
        return new PageImpl<>(paginatedPosts, PageRequest.of(page, size), joinedCommunitiesPosts.size());
    }

    @Override
    public List<Post> findByTextContentContainingOrCommunityCommunityNameContainingOrUserFirstNameContaining(String textContent){
        return postRepository.findByTextContentContainingOrCommunityCommunityNameContainingOrUserFirstNameContaining(textContent,textContent,textContent);
    }

    @Override
    public Page<Post> findTop50ByOrderByVotesDesc(int page, int size) {
        List<Post> joinedCommunitiesPosts = postRepository.findTop50ByOrderByVotesDesc();


        // Sort posts by creation time in descending order
        Collections.sort(joinedCommunitiesPosts, Comparator.comparing(Post::getCreatedAt).reversed());

        // Convert the list to a Page object
        int start = Math.min((page - 1) * size, joinedCommunitiesPosts.size());
        int end = Math.min(page * size, joinedCommunitiesPosts.size());
        List<Post> paginatedPosts = joinedCommunitiesPosts.subList(start, end);
        return new PageImpl<>(paginatedPosts, PageRequest.of(page, size), joinedCommunitiesPosts.size());

    }

    @Override
    public Set<String> getSearchSuggestions(String text) {
        Set<String> suggestions = new HashSet<>(); // Use a Set instead of a List

        // Fetch posts based on text content
        List<Post> textContentPosts = postRepository.findByTextContentContaining(text);
        for (Post post : textContentPosts) {
            suggestions.add(post.getTextContent());
        }

        // Fetch posts based on community name
        List<Post> communityNamePosts = postRepository.findByCommunityCommunityNameContaining(text);
        for (Post post : communityNamePosts) {
            suggestions.add(post.getCommunity().getCommunityName());
        }

        // Fetch posts based on user first name
        List<Post> firstNamePosts = postRepository.findByUserFirstNameContaining(text);
        for (Post post : firstNamePosts) {
            suggestions.add(post.getUser().getFirstName());
        }

        return suggestions;
    }

}



