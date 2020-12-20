package com.example.demo.controllers;

import com.example.demo.models.Genre;
import com.example.demo.models.Post;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PostController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    PostRepository postRepository;

    @Autowired
    GenreRepository genreRepository;

//    @GetMapping("/")
//    public Iterable<Post> getAllPosts(){
//        Iterable<Post> postList = postRepository.findAll();
//        return postList;
//    }

    //валідацію зробити
    @PostMapping("/postAdd")
    public Post addPost(@RequestBody Post post,
                        @RequestParam("file") MultipartFile file) throws IOException {//в цій строчці з цим @RequestParam може бути проблема на фронті бо я просто хз як там на фронті і норм не можу тестанути , на простих html шаблонах все класно робить
        //Post postToCreate = new Post();
        if (post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        if (file == null){// якщо файл не надіслали
            // валідацію сюда в'єбіть
        }

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()){ // це типу перевірка на долбайоба , чи шлях правильний вказаний до директорії
            uploadDir.mkdir();
        }

        //прописати валідацію
        String regex = "/image.*";
        if (!file.getContentType().matches(regex)){// перевіряє чи файл це фото

        }

        file.transferTo(new File(uploadPath + "\\" + file.getOriginalFilename()));

        post.setMainPhoto(uploadPath + "\\" + file.getOriginalFilename());
//        postToCreate.setPostHeader(post.getPostHeader());
//        postToCreate.setDescription(post.getDescription());
//        postToCreate.setGenres(post.getGenres());
//        postToCreate.setPrimarySkills(post.getPrimarySkills());
//        postToCreate.setLocation(post.getLocation());
        //Прописати перевірку на існування жанру та праймері скілу
//        Set<Genre> genres = (Set<Genre>) genreRepository.findAll();
//        for (Genre g: post.getGenres()){
//            if(genres.contains(g)){
//              postToCreate.addGenre(g);
              // тупі ви уєбани блять
                // post.setGenres() для кого
                //створили новий сет і додаєте туда жанри строчкою нижче якщо вони вже є
                //genreRepository.findByGenreName(g.getGenreName());
                // потім post.setGenres() ставите новий сет а вже потім робите сейв(просто тільки тоді нова id присвоюється)
//            }
//        }

        postRepository.save(post);
        return postRepository.getTopByPostHeaderOrderByIdDesc(post.getPostHeader());
    }

    //валідацію зробити
    @GetMapping("/post/{id}")
    public Post getPost(@PathVariable(value = "id") Long id){
        Post post = postRepository.findById(id).get();
        //Validation
        if(!postRepository.existsById(id)){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Post doesn't exist");
        }//
        return post;
    }

    @PutMapping("/post/{id}")
    public Post updatePost(@PathVariable(value = "id") Long id,
                           @RequestBody Post post,
                           @RequestParam("file") MultipartFile file) throws IOException {//в цій строчці з цим @RequestParam може бути проблема на фронті бо я просто хз як там на фронті і норм не можу тестанути , на простих html шаблонах все класно робить
        Post postByID = postRepository.findById(id).get();
        postByID.setPostHeader(post.getPostHeader());
        postByID.setDescription(post.getDescription());
        postByID.setGenres(post.getGenres());
        postByID.setPrimarySkills(post.getPrimarySkills());
        postByID.setLocation(post.getLocation());

        if (file == null){// якщо файл не надіслали
            // валідацію сюда в'єбіть
        }
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()){ // це типу перевірка на долбайоба , чи шлях правильний вказаний до директорії
            uploadDir.mkdir();
        }
        //прописати валідацію
        String regex = "/image.*";
        if (!file.getContentType().matches(regex)){// перевіряє чи файл це фото

        }
        file.transferTo(new File(uploadPath + "\\" + file.getOriginalFilename()));
        post.setMainPhoto(uploadPath + "\\" + file.getOriginalFilename());
        postRepository.save(postByID);
        return postByID;
    }

    @DeleteMapping("/post/{id}")
    public Post deletePost(@PathVariable(value = "id") Long id, @RequestBody Post post){
        postRepository.deleteById(id);
        return post;
    }
}
