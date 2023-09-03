package br.com.compass.Challenge_3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.compass.Challenge_3.entity.Post;
import br.com.compass.Challenge_3.repository.PostRepository;
import br.com.compass.Challenge_3.state.PostState;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private RestTemplate restTemplate;     
    

    public void processPost(Long postId) {
        Post post = fetchPostFromExternalApi(postId);
        post.setState(PostState.POST_OK);
        postRepository.save(post);

        commentService.fetchAndEnrichComments(post);
    }
    
    public void disablePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        
        if (post.getState() == PostState.ENABLED) {
            post.setState(PostState.DISABLED);
            postRepository.save(post);
        } else {
            throw new RuntimeException("Post cannot be disabled");
        }
    }

    public void reprocessPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        
        if (post.getState() == PostState.ENABLED || post.getState() == PostState.DISABLED) {
            post.setState(PostState.UPDATING);
            postRepository.save(post);

            commentService.fetchAndEnrichComments(post);

            post.setState(PostState.ENABLED);
            postRepository.save(post);
        } else {
            throw new RuntimeException("Post cannot be reprocessed");
        }
    }

    public List<Post> queryPosts() {
        return postRepository.findAll();
    }  
    
    public Post savePost(Post post) {
        return postRepository.save(post);
    }
    
    private Post fetchPostFromExternalApi(Long postId) {
    	 String apiUrl = "https://jsonplaceholder.typicode.com/posts/" + postId;
         ResponseEntity<Post> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, Post.class);

         if (response.getStatusCode() == HttpStatus.OK) {
             return response.getBody();
         } else {
             throw new RuntimeException("Failed to fetch post from external API");
         }
    }   	
}

    

