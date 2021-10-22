package com.example.codefellowship.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String body;
    private LocalDateTime createAt= LocalDateTime.now();

    @ManyToOne
    public ApplicationUser user;


    public Post() {
    }

    public Post(String body, ApplicationUser user) {
        this.body = body;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public ApplicationUser getApplicationUser() {
        return user;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.user = applicationUser;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", createAt=" + createAt +
                ", applicationUser=" + user +
                '}';
    }
}
