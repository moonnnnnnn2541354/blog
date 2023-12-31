package com.sparta.blog.domain.blog.entity;

import com.sparta.blog.domain.blog.dto.request.BlogRequestDto;
import com.sparta.blog.domain.comment.entity.Comment;
import com.sparta.blog.domain.user.entity.User;
import com.sparta.blog.global.entity.BaseTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "blogs")
public class Blog extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Long thumbsUpPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE)
    List<Comment> commentList = new ArrayList<>();

    @Builder
    public Blog(String title, String text, User user, Long thumbsUpPoint) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.thumbsUpPoint = thumbsUpPoint;
    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.text = requestDto.getText();
    }

    public void isThumbsUp(Boolean toggle) {
        if (toggle) {
            thumbsUpPoint++;
            return;
        }
        thumbsUpPoint--;
    }
}
