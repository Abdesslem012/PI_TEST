package com.example.demo.Service;


import java.util.List;

import com.example.demo.DTO.FeedBackDTO;
import com.example.demo.Entity.FeedBack;
import com.example.demo.Repository.FeedBackRepository;
import com.example.demo.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FeedBackService {

    private final FeedBackRepository feedBackRepository;

    public FeedBackService(final FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    public List<FeedBackDTO> findAll() {
        final List<FeedBack> feedBacks = feedBackRepository.findAll(Sort.by("feedbackId"));
        return feedBacks.stream()
                .map(feedBack -> mapToDTO(feedBack, new FeedBackDTO()))
                .toList();
    }

    public FeedBackDTO get(final Long feedbackId) {
        return feedBackRepository.findById(feedbackId)
                .map(feedBack -> mapToDTO(feedBack, new FeedBackDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FeedBackDTO feedBackDTO) {
        final FeedBack feedBack = new FeedBack();
        mapToEntity(feedBackDTO, feedBack);
        return feedBackRepository.save(feedBack).getFeedbackId();
    }

    public void update(final Long feedbackId, final FeedBackDTO feedBackDTO) {
        final FeedBack feedBack = feedBackRepository.findById(feedbackId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(feedBackDTO, feedBack);
        feedBackRepository.save(feedBack);
    }

    public void delete(final Long feedbackId) {
        feedBackRepository.deleteById(feedbackId);
    }

    private FeedBackDTO mapToDTO(final FeedBack feedBack, final FeedBackDTO feedBackDTO) {
        feedBackDTO.setFeedbackId(feedBack.getFeedbackId());
        feedBackDTO.setCommentaire(feedBack.getCommentaire());
        feedBackDTO.setAuteur(feedBack.getAuteur());
        feedBackDTO.setDestinataire(feedBack.getDestinataire());
        return feedBackDTO;
    }

    private FeedBack mapToEntity(final FeedBackDTO feedBackDTO, final FeedBack feedBack) {
        feedBack.setCommentaire(feedBackDTO.getCommentaire());
        feedBack.setAuteur(feedBackDTO.getAuteur());
        feedBack.setDestinataire(feedBackDTO.getDestinataire());
        return feedBack;
    }

}
