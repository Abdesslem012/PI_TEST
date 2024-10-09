package com.example.demo.Service;


import com.example.demo.DTO.CommentDTO;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Post;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment addCommentToPost(Long postId, Comment comment) {
        // Fetch the post by ID
        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
            throw new NotFoundException("Post not found with ID: " + postId);
        }

        Post post = postOptional.get();

        // Set the post for the comment
        comment.setPostId(post);


        // Save the comment to the database
        return commentRepository.save(comment);
    }



    public CommentService(final CommentRepository commentRepository,
                          final PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    public List<CommentDTO> findCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    }

    public List<CommentDTO> findAll() {
        final List<Comment> comments = commentRepository.findAll(Sort.by("id"));
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    }

    public CommentDTO get(final Long id) {
        return commentRepository.findById(id)
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CommentDTO commentDTO) {
        final Comment comment = new Comment();
        mapToEntity(commentDTO, comment);
        return commentRepository.save(comment).getId();
    }

    public void update(final Long id, final CommentDTO commentDTO) {
        final Comment comment = commentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(commentDTO, comment);
        commentRepository.save(comment);
    }

    public void delete(final Long id) {
        commentRepository.deleteById(id);
    }

    private CommentDTO mapToDTO(final Comment comment, final CommentDTO commentDTO) {
        commentDTO.setId(comment.getId());
        commentDTO.setTxtComment(comment.getTxtComment());
        commentDTO.setDatePosted(comment.getDatePosted());
        commentDTO.setPostId(comment.getPostId() == null ? null : comment.getPostId().getPostId());
        return commentDTO;
    }

    private Comment mapToEntity(final CommentDTO commentDTO, final Comment comment) {
        comment.setTxtComment(commentDTO.getTxtComment());
        comment.setDatePosted(commentDTO.getDatePosted());
        final Post postId = commentDTO.getPostId() == null ? null : postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new NotFoundException("postId not found"));
        comment.setPostId(postId);
        return comment;
    }

}
