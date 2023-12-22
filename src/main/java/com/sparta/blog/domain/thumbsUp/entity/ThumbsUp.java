package com.sparta.blog.domain.thumbsUp.entity;

import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.user.entity.User;
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
@Table(name = "thumbs_up")
@NoArgsConstructor
public class ThumbsUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean thumbsUp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    @Builder
    public ThumbsUp(Boolean thumbsUp, User user, Blog blog) {
        this.thumbsUp = thumbsUp;
        this.user = user;
        this.blog = blog;
    }

    public Boolean isThumbsUp() {
        this.thumbsUp = !thumbsUp;
        return this.thumbsUp;
    }

}
