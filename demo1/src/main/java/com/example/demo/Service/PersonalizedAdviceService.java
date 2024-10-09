package com.example.demo.Service;



import java.util.List;

import com.example.demo.DTO.PersonalizedAdviceDTO;
import com.example.demo.Entity.MoitoringAcadimicObjectives;
import com.example.demo.Entity.PersonalizedAdvice;
import com.example.demo.Repository.MoitoringAcadimicObjectivesRepository;
import com.example.demo.Repository.PersonalizedAdviceRepository;
import com.example.demo.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;




@Service
public class PersonalizedAdviceService {

    private final PersonalizedAdviceRepository personalizedAdviceRepository;
    private final MoitoringAcadimicObjectivesRepository moitoringAcadimicObjectivesRepository;

    public PersonalizedAdviceService(
            final PersonalizedAdviceRepository personalizedAdviceRepository,
            final MoitoringAcadimicObjectivesRepository moitoringAcadimicObjectivesRepository) {
        this.personalizedAdviceRepository = personalizedAdviceRepository;
        this.moitoringAcadimicObjectivesRepository = moitoringAcadimicObjectivesRepository;
    }

    public List<PersonalizedAdviceDTO> findAll() {
        final List<PersonalizedAdvice> personalizedAdvices = personalizedAdviceRepository.findAll(Sort.by("idPersonalized"));
        return personalizedAdvices.stream()
                .map(personalizedAdvice -> mapToDTO(personalizedAdvice, new PersonalizedAdviceDTO()))
                .toList();
    }

    public PersonalizedAdviceDTO get(final Long idPersonalized) {
        return personalizedAdviceRepository.findById(idPersonalized)
                .map(personalizedAdvice -> mapToDTO(personalizedAdvice, new PersonalizedAdviceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PersonalizedAdviceDTO personalizedAdviceDTO) {
        final PersonalizedAdvice personalizedAdvice = new PersonalizedAdvice();
        mapToEntity(personalizedAdviceDTO, personalizedAdvice);
        return personalizedAdviceRepository.save(personalizedAdvice).getIdPersonalized();
    }

    public void update(final Long idPersonalized,
            final PersonalizedAdviceDTO personalizedAdviceDTO) {
        final PersonalizedAdvice personalizedAdvice = personalizedAdviceRepository.findById(idPersonalized)
                .orElseThrow(NotFoundException::new);
        mapToEntity(personalizedAdviceDTO, personalizedAdvice);
        personalizedAdviceRepository.save(personalizedAdvice);
    }

    public void delete(final Long idPersonalized) {
        personalizedAdviceRepository.deleteById(idPersonalized);
    }

    private PersonalizedAdviceDTO mapToDTO(final PersonalizedAdvice personalizedAdvice,
            final PersonalizedAdviceDTO personalizedAdviceDTO) {
        personalizedAdviceDTO.setIdPersonalized(personalizedAdvice.getIdPersonalized());
        personalizedAdviceDTO.setAdviceText(personalizedAdvice.getAdviceText());
        personalizedAdviceDTO.setMoitoringId(String.valueOf(personalizedAdvice.getMoitoringId() == null ? null : personalizedAdvice.getMoitoringId().getMaoId()));
        return personalizedAdviceDTO;
    }

    private PersonalizedAdvice mapToEntity(final PersonalizedAdviceDTO personalizedAdviceDTO,
            final PersonalizedAdvice personalizedAdvice) {
        personalizedAdvice.setAdviceText(personalizedAdviceDTO.getAdviceText());
        final MoitoringAcadimicObjectives moitoringId = personalizedAdviceDTO.getMoitoringId() == null ? null : moitoringAcadimicObjectivesRepository.findById(Long.valueOf(String.valueOf(Long.valueOf(personalizedAdviceDTO.getMoitoringId()))))
                .orElseThrow(() -> new NotFoundException("moitoringId not found"));
        personalizedAdvice.setMoitoringId(moitoringId);
        return personalizedAdvice;
    }



}
