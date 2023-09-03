package br.com.compass.Challenge_3.controller;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.compass.Challenge_3.entity.Post;
import br.com.compass.Challenge_3.service.PostService;
import br.com.compass.Challenge_3.service.StateService;
import br.com.compass.Challenge_3.state.PostState;
import br.com.compass.Challenge_3.dto.CommentDto;
import br.com.compass.Challenge_3.dto.HistoryDto;
import br.com.compass.Challenge_3.dto.PostDto;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private StateService stateService;

    @PostMapping("/{postId}/process")
    public ResponseEntity<String> processPost(@PathVariable Long postId) {
        postService.processPost(postId);
        return ResponseEntity.ok("Post processing initiated.");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> disablePost(@PathVariable Long postId) {
        stateService.transitionToState(postId, PostState.DISABLED);
        return ResponseEntity.ok("Post disabled.");
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> reprocessPost(@PathVariable Long postId) {
        stateService.transitionToState(postId, PostState.UPDATING);
        postService.processPost(postId);
        return ResponseEntity.ok("Post reprocessed and enabled.");
    }
    
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {       
        Post newPost = new Post();
        newPost.setTitle(postDto.getTitle());
        newPost.setBody(postDto.getBody());        
        newPost.setState(PostState.CREATED);
       
        Post savedPost = postService.savePost(newPost);        
        PostDto savedPostDto = convertToDto(savedPost);      

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPostDto);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> queryPosts() {
        List<Post> posts = postService.queryPosts();
        List<PostDto> postDtos = posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDtos);
    }

    private PostDto convertToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setBody(post.getBody());
        postDto.setState(post.getState());

        List<CommentDto> commentDtos = post.getComments().stream()
                .map(comment -> {
                    CommentDto commentDto = new CommentDto();
                    commentDto.setId(comment.getId());
                    commentDto.setBody(comment.getBody());
                    return commentDto;
                })
                .collect(Collectors.toList());

        List<HistoryDto> historyDtos = post.getHistory().stream()
                .map(historyEntry -> {
                    HistoryDto historyEntryDto = new HistoryDto();
                    historyEntryDto.setId(historyEntry.getId());
                    historyEntryDto.setDate(Date.from(historyEntry.getDate().atZone(ZoneId.systemDefault()).toInstant()));
                    historyEntryDto.setState(historyEntry.getState().toString());
                    return historyEntryDto;
                })
                .collect(Collectors.toList());

        postDto.setComments(commentDtos);
        postDto.setHistory(historyDtos);

        return postDto;
    }
}