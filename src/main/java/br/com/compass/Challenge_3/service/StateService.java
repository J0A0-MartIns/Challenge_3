package br.com.compass.Challenge_3.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compass.Challenge_3.entity.History;
import br.com.compass.Challenge_3.entity.Post;
import br.com.compass.Challenge_3.repository.PostRepository;
import br.com.compass.Challenge_3.state.PostState;

@Service
public class StateService {
    @Autowired
    private PostRepository postRepository;

    public void transitionToState(Long postId, PostState newState) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        PostState currentState = post.getState();
        if (isValidTransition(currentState, newState)) {
            addHistoryEntry(post, currentState, newState);
            post.setState(newState);
            postRepository.save(post);
        } else {
            throw new RuntimeException("Invalid state transition");
        }
    }

    private boolean isValidTransition(PostState currentState, PostState newState) {
        switch (currentState) {
            case CREATED:
                return newState == PostState.POST_FIND;
            case POST_FIND:
                return newState == PostState.POST_OK;
            case POST_OK:
                return newState == PostState.COMMENTS_FIND;
            case COMMENTS_FIND:
                return newState == PostState.COMMENTS_OK;
            case COMMENTS_OK:
                return newState == PostState.ENABLED;
            case ENABLED:
                return newState == PostState.DISABLED || newState == PostState.UPDATING;
            case DISABLED:
                return newState == PostState.ENABLED || newState == PostState.UPDATING;
            case UPDATING:
                return newState == PostState.ENABLED || newState == PostState.DISABLED;
            default:
                return false;
        }
    }

    private void addHistoryEntry(Post post, PostState fromState, PostState toState) {
        History historyEntry = new History();
        historyEntry.setPost(post);
        historyEntry.setFromState(fromState);
        historyEntry.setToState(toState);     
        historyEntry.setDate(LocalDateTime.now());

        post.getHistory().add(historyEntry);
    }
}

