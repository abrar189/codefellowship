package com.example.codefellowship.reposetory;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findAllByUser(ApplicationUser applicationUser);
}
