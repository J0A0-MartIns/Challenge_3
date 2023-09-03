package br.com.compass.Challenge_3.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.compass.Challenge_3.entity.Comment;
import br.com.compass.Challenge_3.entity.Post;
import br.com.compass.Challenge_3.repository.CommentRepository;
import br.com.compass.Challenge_3.state.PostState;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Comment> fetchCommentsFromExternalApi(Long postId) {
        String apiUrl = "https://jsonplaceholder.typicode.com/posts/" + postId + "/comments";
        ResponseEntity<Comment[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, Comment[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(response.getBody());
        } else {
            throw new RuntimeException("Failed to fetch comments from external API");
        }
    }
    public void fetchAndEnrichComments(Post post) {
        List<Comment> comments = fetchCommentsFromExternalApi(post.getId());
        comments.forEach(comment -> {
            comment.setPost(post);
            commentRepository.save(comment);
        });
        post.setState(PostState.COMMENTS_OK);
    }
}
    

    

