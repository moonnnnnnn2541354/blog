package com.sparta.blog.domain.comment.entity;

import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.user.entity.User;
import com.sparta.blog.global.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Boolean thumbsUp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog", nullable = false)
    private Blog blog;

    @Builder
    public Comment(String text, Boolean thumbsUp, User user, Blog blog) {
        this.text = text;
        this.thumbsUp = thumbsUp;
        this.user = user;
        this.blog = blog;
    }


}
