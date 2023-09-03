package br.com.compass.Challenge_3.entity;

import java.time.LocalDateTime;

import br.com.compass.Challenge_3.state.PostState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private PostState state;

    private LocalDateTime date;
    
    private PostState fromState;
    
    private PostState toState;

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public PostState getState() {
		return state;
	}

	public void setState(PostState state) {
		this.state = state;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime timestamp) {
		this.date = timestamp;
	}

	public Long getId() {
		return id;
	}
	
	public PostState getFromState() {
        return fromState;
    }

    public void setFromState(PostState fromState) {
        this.fromState = fromState;
    }

    public PostState getToState() {
        return toState;
    }

    public void setToState(PostState toState) {
        this.toState = toState;
    }

    
}
