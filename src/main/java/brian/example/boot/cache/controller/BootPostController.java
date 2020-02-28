package brian.example.boot.cache.controller;

import brian.example.boot.cache.model.Post;
import brian.example.boot.cache.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BootPostController {

    private PostService service;

    @Autowired
    public BootPostController(PostService service){
        this.service = service;
    }

    @GetMapping("/post/{id}")
    public Post getPost(@PathVariable("id") int id){

        return service.getPost(id);
    }

    public Post addPost(Post post){


        return post;
    }
}
