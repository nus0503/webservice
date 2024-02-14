package com.jojoldu.book.webservice.service.posts;

import com.jojoldu.book.webservice.common.PageableRequest;
import com.jojoldu.book.webservice.common.dateTime.DateTimeUtil;
import com.jojoldu.book.webservice.common.redis.RedisUtil;
import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.webservice.controller.post.dto.*;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import com.jojoldu.book.webservice.domain.oAuthUser.UserRepository;
import com.jojoldu.book.webservice.domain.posts.Posts;
import com.jojoldu.book.webservice.domain.posts.PostsRepository;
import com.jojoldu.book.webservice.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;

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
    public int updateViewCount(Long id, SessionUser user) {

        String viewCount = redisUtil.getData(String.valueOf(user.getId()));

        if (viewCount == null) {
            redisUtil.setData(String.valueOf(user.getId()), id + "_", DateTimeUtil.calculateTimeUntilMidnight());
            return postsRepository.updateViewCount(id);
        } else {
            String[] viewCountArr = viewCount.split("_");
            List<String> redisViewCountList = Arrays.asList(viewCountArr);

            boolean isView = false;

            if (!redisViewCountList.isEmpty()) {
                for (String viewedCount : redisViewCountList) {
                    if (String.valueOf(id).equals(viewedCount)) {
                        isView = true;
                        break;
                    }
                }
                if (!isView) {
                    viewCount += id + "_";

                    redisUtil.setData(String.valueOf(user.getId()), viewCount, DateTimeUtil.calculateTimeUntilMidnight());
                    postsRepository.updateViewCount(id);
                }
            }
        }
//        return postsRepository.updateViewCount(id);
        return 0;
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
