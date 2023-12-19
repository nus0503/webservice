package com.jojoldu.book.webservice.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findByPostsIdAndId(Long postsId, Long id);
}
