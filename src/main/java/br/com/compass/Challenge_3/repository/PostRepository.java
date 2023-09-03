package br.com.compass.Challenge_3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.compass.Challenge_3.entity.Post;
import br.com.compass.Challenge_3.state.PostState;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	 List<Post> findByState(PostState state);
}
