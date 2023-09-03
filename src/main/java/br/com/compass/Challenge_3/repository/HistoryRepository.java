package br.com.compass.Challenge_3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.compass.Challenge_3.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
	  List<History> findByPostId(Long postId);
}
