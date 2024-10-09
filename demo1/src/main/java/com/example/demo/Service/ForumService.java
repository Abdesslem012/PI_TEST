package com.example.demo.Service;
import com.example.demo.DTO.ForumDTO;
import com.example.demo.Entity.Forum;
import com.example.demo.Entity.Post;
import com.example.demo.Repository.ForumRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    private final ForumRepository forumRepository;
    private final PostRepository postRepository;

    public ForumService(final ForumRepository forumRepository,
                        final PostRepository postRepository) {
        this.forumRepository = forumRepository;
        this.postRepository = postRepository;
    }

    public List<ForumDTO> findAll() {
        final List<Forum> forums = forumRepository.findAll(Sort.by("forumId"));
        return forums.stream()
                .map(forum -> mapToDTO(forum, new ForumDTO()))
                .toList();
    }

    public ForumDTO get(final Long forumId) {
        return forumRepository.findById(forumId)
                .map(forum -> mapToDTO(forum, new ForumDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ForumDTO forumDTO) {
        final Forum forum = new Forum();
        mapToEntity(forumDTO, forum);
        return forumRepository.save(forum).getForumId();
    }

    public void update(final Long forumId, final ForumDTO forumDTO) {
        final Forum forum = forumRepository.findById(forumId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(forumDTO, forum);
        forumRepository.save(forum);
    }

    public void delete(final Long forumId) {
        forumRepository.deleteById(forumId);
    }

    private ForumDTO mapToDTO(final Forum forum, final ForumDTO forumDTO) {
        forumDTO.setForumId(forum.getForumId());
        return forumDTO;
    }

    private Forum mapToEntity(final ForumDTO forumDTO, final Forum forum) {
        return forum;
    }

    public ReferencedWarning getReferencedWarning(final Long forumId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Forum forum = forumRepository.findById(forumId)
                .orElseThrow(NotFoundException::new);
        final Post forumIdPost = postRepository.findFirstByForumId(forum);
        if (forumIdPost != null) {
            referencedWarning.setKey("forum.post.forumId.referenced");
            referencedWarning.addParam(forumIdPost.getPostId());
            return referencedWarning;
        }
        return null;
    }

}
