package com.inpocket.vitaverse.forum.service;

import com.inpocket.vitaverse.forum.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public interface PostService {
    Post createPost(Post post);
    Post updatePost(Post post);
    Optional<Post> getPostById(long postId);
    void deletePost(long postId);
    List<Post> getAllPosts();
    Post createPostAndSetCreatorAndCommunity(Post post, Long userId, Long communityId, MultipartFile attachment) ;
    List<Post> findByCommunityCommunityId(long community_communityId);



    public  List<Post> findByUserJoinedCommunities(long userId);
    public Page<Post> findByUserJoinedCommunitiesPaginated(long userId, int page, int size);
    public List<Post> findByTextContentContainingOrCommunityCommunityNameContainingOrUserFirstNameContaining(String textContent);
    Page<Post> findTop50ByOrderByVotesDesc( int page, int size);
    Set<String> getSearchSuggestions (String text);


}
