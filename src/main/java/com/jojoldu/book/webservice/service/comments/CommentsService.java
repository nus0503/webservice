package com.jojoldu.book.webservice.service.comments;

import com.jojoldu.book.webservice.domain.comments.Comments;
import com.jojoldu.book.webservice.domain.comments.CommentsRepository;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import com.jojoldu.book.webservice.domain.oAuthUser.UserRepository;
import com.jojoldu.book.webservice.domain.posts.Posts;
import com.jojoldu.book.webservice.domain.posts.PostsRepository;
import com.jojoldu.book.webservice.controller.comments.dto.CommentsReqeustDto;
import com.jojoldu.book.webservice.controller.comments.dto.CommentsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public Long commentSave(String email, Long id, CommentsReqeustDto dto) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("회원이 없습니다.")
        );

        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글 쓰기 실패 : 해당 게시글이 존재하지 않습니다.")
        );

        dto.setUser(user);
        dto.setPosts(posts);

        return commentsRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public void update(Long postsId, Long id, CommentsUpdateDto dto) {
        Comments comments = commentsRepository.findByPostsIdAndId(postsId, id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id = " + id)
        );

        comments.update(dto.getComment());
    }

    @Transactional
    public void delete(Long postsId, Long id) {
        Comments comments = commentsRepository.findByPostsIdAndId(postsId, id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id = " + id)
        );
        commentsRepository.delete(comments);
    }
}
