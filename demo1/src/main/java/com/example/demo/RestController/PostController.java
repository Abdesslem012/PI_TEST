package com.example.demo.RestController;

import com.example.demo.DTO.ForumDTO;
import com.example.demo.DTO.PostDTO;
import com.example.demo.Service.PostService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    private final PostService postService;

    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable(name = "postId") final Long postId) {
        return ResponseEntity.ok(postService.get(postId));
    }

    @PostMapping("/add-post")
    public ResponseEntity<Long> createPost(@RequestBody final PostDTO postDTO) {
        final Long createdPostId = postService.create(postDTO);
        return new ResponseEntity<>(createdPostId, HttpStatus.CREATED);
    }


    private String saveImageToServer(MultipartFile imageFile) throws IOException {
        // Chemin de destination pour enregistrer l'image
        String uploadDir = "src\\main\\resources\\static\\images";

        // Créer le dossier s'il n'existe pas
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Chemin complet du fichier d'image sur le serveur
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName); // Utiliser Paths.get() pour créer un objet Path

        // Copiez le fichier d'image vers le dossier de destination
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Renvoyer l'URL de l'image
        return "/images/" + fileName;
    }
    @PutMapping("update-post/{postId}")
    public ResponseEntity<Long> updatePost(@PathVariable(name = "postId") final Long postId,
                                           @RequestBody  final PostDTO postDTO) {
        postService.update(postId, postDTO);
        return ResponseEntity.ok(postId);
    }

    @DeleteMapping("delete-post/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "postId") final Long postId) {
        final ReferencedWarning referencedWarning = postService.getReferencedWarning(postId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        postService.delete(postId);
        return ResponseEntity.noContent().build();
    }

}

