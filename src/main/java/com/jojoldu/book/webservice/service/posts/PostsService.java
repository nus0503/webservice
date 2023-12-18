package com.jojoldu.book.webservice.service.posts;

import com.jojoldu.book.webservice.common.PageableRequest;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import com.jojoldu.book.webservice.domain.oAuthUser.UserRepository;
import com.jojoldu.book.webservice.domain.posts.Posts;
import com.jojoldu.book.webservice.domain.posts.PostsRepository;
import com.jojoldu.book.webservice.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    public Long save(PostsSaveRequestDto requestDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("회원이 없습니다.")
        );
        requestDto.setUser(user);
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public int updateViewCount(Long id) {
            return postsRepository.updateViewCount(id);
    }

    public Page<PostsSearchListResponseDto> search(String keyword, PageableRequest request) {
        return postsRepository.findByTitleContaining(keyword, request.toPageable())
                .map(PostsSearchListResponseDto::new);
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        entity.getUser().getId();
        return new PostsResponseDto(entity);
    }

    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    public Page<NoticeResponseDto> findAll(PageableRequest request) {
        Page<Posts> page = postsRepository.findAll(request.toPageable());
        return page.map(NoticeResponseDto::new);
    }
}
