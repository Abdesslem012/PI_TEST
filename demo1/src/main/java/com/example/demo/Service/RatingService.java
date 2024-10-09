package com.example.demo.Service;

import java.util.List;

import com.example.demo.DTO.RatingDTO;
import com.example.demo.Entity.Quiz;
import com.example.demo.Entity.Rating;
import com.example.demo.Repository.QuizRepository;
import com.example.demo.Repository.RatingRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final QuizRepository quizRepository;

    public RatingService(final RatingRepository ratingRepository,
            final QuizRepository quizRepository) {
        this.ratingRepository = ratingRepository;
        this.quizRepository = quizRepository;
    }

    public List<RatingDTO> findAll() {
        final List<Rating> ratings = ratingRepository.findAll(Sort.by("ratingId"));
        return ratings.stream()
                .map(rating -> mapToDTO(rating, new RatingDTO()))
                .toList();
    }

    public RatingDTO get(final Long ratingId) {
        return ratingRepository.findById(ratingId)
                .map(rating -> mapToDTO(rating, new RatingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RatingDTO ratingDTO) {
        final Rating rating = new Rating();
        mapToEntity(ratingDTO, rating);
        return ratingRepository.save(rating).getRatingId();
    }

    public void update(final Long ratingId, final RatingDTO ratingDTO) {
        final Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ratingDTO, rating);
        ratingRepository.save(rating);
    }

    public void delete(final Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    private RatingDTO mapToDTO(final Rating rating, final RatingDTO ratingDTO) {
        ratingDTO.setRatingId(rating.getRatingId());
        ratingDTO.setNom(rating.getNom());
        ratingDTO.setDescription(rating.getDescription());
        ratingDTO.setNombre(rating.getNombre());
        ratingDTO.setQuizId(rating.getQuizId() == null ? null : rating.getQuizId().getQuizId());
        return ratingDTO;
    }

    private Rating mapToEntity(final RatingDTO ratingDTO, final Rating rating) {
        rating.setNom(ratingDTO.getNom());
        rating.setDescription(ratingDTO.getDescription());
        rating.setNombre(ratingDTO.getNombre());
        final Quiz quizId = ratingDTO.getQuizId() == null ? null : quizRepository.findById(ratingDTO.getQuizId())
                .orElseThrow(() -> new NotFoundException("quizId not found"));
        rating.setQuizId(quizId);
        return rating;
    }

    public boolean quizIdExists(final Long quizId) {
        return ratingRepository.existsByQuizIdQuizId(quizId);
    }

    public ReferencedWarning getReferencedWarning(final Long ratingId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(NotFoundException::new);
        return null;
    }

}
