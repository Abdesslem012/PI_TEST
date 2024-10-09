package com.example.demo.Service;
import com.example.demo.DTO.ClassesDTO;
import com.example.demo.DTO.PostDTO;
import com.example.demo.Entity.Classes;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Forum;
import com.example.demo.Entity.Post;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.ForumRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ForumRepository forumRepository;
    private final CommentRepository commentRepository;

    public PostService(final PostRepository postRepository, final ForumRepository forumRepository,
                       final CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.forumRepository = forumRepository;
        this.commentRepository = commentRepository;
    }

    public List<PostDTO> findAll() {
        final List<Post> posts = postRepository.findAll(Sort.by("postId"));
        return posts.stream()
                .map(post -> mapToDTO(post, new PostDTO()))
                .toList();
    }

    public PostDTO get(final Long postId) {
        return postRepository.findById(postId)
                .map(post -> mapToDTO(post, new PostDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PostDTO postDTO) {
        final Post post = new Post();
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setDatePosted(LocalDate.now());
        mapToEntity(postDTO, post);
        return postRepository.save(post).getPostId();
    }

    public void update(final Long postId, final PostDTO postDTO) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(postDTO, post);
        postRepository.save(post);
    }

    public void delete(final Long postId) {
        postRepository.deleteById(postId);
    }

    private PostDTO mapToDTO(final Post post, final PostDTO postDTO) {
        postDTO.setPostId(post.getPostId());
        postDTO.setTitle(post.getTitle());
        postDTO.setTxtPost(post.getTxtPost());
        postDTO.setDatePosted(post.getDatePosted());
        postDTO.setForumId(post.getForumId() == null ? null : post.getForumId().getForumId());
        postDTO.setPostedBy(post.getPostedBy()); // Set postedBy attribute
        postDTO.setImg(post.getImg()); // Set img attribute
        postDTO.setLikeCount(post.getLikeCount()); // Set likeCount attribute
        postDTO.setViewCount(post.getViewCount()); // Set viewCount attribute
        postDTO.setTags(post.getTags()); // Set tags attribute
        return postDTO;
    }

    private Post mapToEntity(final PostDTO postDTO, final Post post) {
        post.setTitle(postDTO.getTitle());
        post.setTxtPost(postDTO.getTxtPost());
        post.setDatePosted(postDTO.getDatePosted());
        final Forum forumId = postDTO.getForumId() == null ? null : forumRepository.findById(postDTO.getForumId())
                .orElseThrow(() -> new NotFoundException("forumId not found"));
        post.setForumId(forumId);
        post.setPostedBy(postDTO.getPostedBy()); // Set postedBy attribute
        post.setImg(postDTO.getImg()); // Set img attribute
        post.setLikeCount(postDTO.getLikeCount()); // Set likeCount attribute
        post.setViewCount(postDTO.getViewCount()); // Set viewCount attribute
        post.setTags(postDTO.getTags()); // Set tags attribute
        return post;
    }

    public ReferencedWarning getReferencedWarning(final Long postId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundException::new);
        final Comment postIdComment = commentRepository.findFirstByPostId(post);
        if (postIdComment != null) {
            referencedWarning.setKey("post.comment.postId.referenced");
            referencedWarning.addParam(postIdComment.getId());
            return referencedWarning;
        }
        return null;
    }

}
