package com.inpocket.vitaverse.forum.controller;

import com.inpocket.vitaverse.forum.service.CloudinaryServiceForum;
import com.inpocket.vitaverse.forum.entity.Post;
import com.inpocket.vitaverse.forum.entity.PostType;
import com.inpocket.vitaverse.forum.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;

@RestController
@AllArgsConstructor
@RequestMapping("api/Posts")

public class PostController {
    PostService postService;
    CloudinaryServiceForum cloudinaryService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post post){
       return postService.createPost(post);
    }
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Post updatePost(@RequestBody Post post){
        return postService.updatePost(post);
    }
    @GetMapping("/{postid}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Post> getPost( @PathVariable("postid") long postid){
        return postService.getPostById(postid);
    }
    @DeleteMapping("/delete/{postid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("postid") long postid){
         postService.deletePost(postid);
    }
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }


    @PostMapping("/addPost")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestParam("userId") Long userId,
                           @RequestParam("communityId") Long communityId,
                           @RequestParam(value = "attachment", required = false) MultipartFile attachment,
                           @RequestParam("textContent") String textContent,
                           @RequestParam("postType") PostType postType) throws IOException {
        // Construct the post object using the parameters
        Post post = new Post();
        post.setTextContent(textContent);
        // Set other fields as needed
        post.setPostType(postType);

        // Call the service method to handle the creation
        Post createdPost = postService.createPostAndSetCreatorAndCommunity(post, userId, communityId, attachment);

        // If an attachment was provided, set the attachment URL to the post
        if (attachment != null && !attachment.isEmpty()) {
            // Get the URL of the uploaded file from Cloudinary
            String attachmentUrl = cloudinaryService.upload(attachment).get("url").toString();
            // Set the attachment URL to the post
            createdPost.setAttachment(attachmentUrl);
        }

        // Return the created post
        return createdPost;
    }
    @GetMapping("/all/{communityId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> findByCommunityCommunityId(@PathVariable long communityId){
        return postService.findByCommunityCommunityId(communityId);
    }

    @GetMapping("/JoinedCommunitiesPosts/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> findByUserJoinedCommunities(@PathVariable long userId) {
        return postService.findByUserJoinedCommunities(userId);
    }


    @GetMapping("/user/{userId}/communities")
    @ResponseStatus(HttpStatus.OK)
    public Page<Post> findPostsByUserJoinedCommunities(@PathVariable long userId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return postService.findByUserJoinedCommunitiesPaginated(userId, page, size);
    }


    @GetMapping("/search/{text}")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> findByTextContentContaining(@PathVariable String text){
        return postService.findByTextContentContainingOrCommunityCommunityNameContainingOrUserFirstNameContaining(text);
    }


    @GetMapping("/popularPosts")
    @ResponseStatus(HttpStatus.OK)
    public Page<Post> findTop50ByOrderByVotesDesc(
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return postService.findTop50ByOrderByVotesDesc( page, size);
    }


    @GetMapping("/searchSuggestions/{text}")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getSearchSuggestions(@PathVariable String text) {
        return postService.getSearchSuggestions(text);
    }





}
