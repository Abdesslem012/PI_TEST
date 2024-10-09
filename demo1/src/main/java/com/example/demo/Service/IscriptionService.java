package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.DTO.IscriptionDTO;
import com.example.demo.Entity.Iscription;
import com.example.demo.Repository.EventRepository;
import com.example.demo.Repository.IscriptionRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


@Service
public class IscriptionService {

    private final IscriptionRepository iscriptionRepository;
    private final StudentRepository studentRepository;

    private  final EventRepository eventRepository;

    @Autowired
    private MailSender mailSender;

    public IscriptionService(final IscriptionRepository iscriptionRepository, StudentRepository studentRepository, EventRepository eventRepository) {
        this.iscriptionRepository = iscriptionRepository;
        this.studentRepository = studentRepository;
        this.eventRepository = eventRepository;
    }

    public List<IscriptionDTO> findAll() {
        final List<Iscription> iscriptions = iscriptionRepository.findAll(Sort.by("idInscription"));
        return iscriptions.stream()
                .map(iscription -> mapToDTO(iscription, new IscriptionDTO()))
                .toList();
    }

    public IscriptionDTO get(final Long idInscription) {
        return iscriptionRepository.findById(idInscription)
                .map(iscription -> mapToDTO(iscription, new IscriptionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final IscriptionDTO iscriptionDTO) {
        final Iscription iscription = new Iscription();
        mapToEntity(iscriptionDTO, iscription);
        Long idInscription = iscriptionRepository.save(iscription).getIdInscription();
        return iscriptionRepository.save(iscription).getIdInscription();
    }

    public void update(final Long idInscription, final IscriptionDTO iscriptionDTO) {
        final Iscription iscription = iscriptionRepository.findById(idInscription)
                .orElseThrow(NotFoundException::new);
        mapToEntity(iscriptionDTO, iscription);
        iscriptionRepository.save(iscription);
    }

    public void delete(final Long idInscription) {
        iscriptionRepository.deleteById(idInscription);
    }

    private IscriptionDTO mapToDTO(final Iscription iscription, final IscriptionDTO iscriptionDTO) {
        iscriptionDTO.setIdInscription(iscription.getIdInscription());
        iscriptionDTO.setFirstName(iscription.getFirstName());
        iscriptionDTO.setLastName(iscription.getLastName());
        iscriptionDTO.setEmail(iscription.getEmail());
        iscriptionDTO.setPhone(iscription.getPhone());
        iscriptionDTO.setBirthdate(iscription.getBirthdate());
        iscriptionDTO.setStatus(iscription.getStatus());
        return iscriptionDTO;
    }

    private Iscription mapToEntity(final IscriptionDTO iscriptionDTO, final Iscription iscription) {
        iscription.setIdInscription(iscriptionDTO.getIdInscription());
        iscription.setFirstName(iscriptionDTO.getFirstName());
        iscription.setLastName(iscriptionDTO.getLastName());
        iscription.setEmail(iscriptionDTO.getEmail());
        iscription.setPhone(iscriptionDTO.getPhone());
        iscription.setBirthdate(iscriptionDTO.getBirthdate());
        iscription.setStatus(iscriptionDTO.getStatus());
        return iscription;
    }

    public boolean emailExists(final String email) {
        return iscriptionRepository.existsByEmailIgnoreCase(email);
    }

    public boolean phoneExists(final String phone) {
        return iscriptionRepository.existsByPhoneIgnoreCase(phone);
    }


    public void saveInscription(Iscription inscription) {
        iscriptionRepository.save(inscription);
    }







    public IscriptionDTO getInscriptionDetails(Long idInscription) {
        Iscription inscription = iscriptionRepository.findById(idInscription)
                .orElseThrow(() -> new NotFoundException("Inscription not found with ID: " + idInscription));

        // Convertir l'objet Inscription en InscriptionDTO si nécessaire
        IscriptionDTO inscriptionDTO = convertToDTO(inscription);

        return inscriptionDTO;
    }

    private IscriptionDTO convertToDTO(Iscription inscription) {
        IscriptionDTO inscriptionDTO = new IscriptionDTO();
        inscriptionDTO.setIdInscription(inscription.getIdInscription());
        // Autres attributs à mapper si nécessaire
        return inscriptionDTO;
    }

    public boolean accepterInscription(Long idInscription) {
        Optional<Iscription> optionalInscription = iscriptionRepository.findById(idInscription);
        if (optionalInscription.isPresent()) {
            Iscription inscription = optionalInscription.get();
            inscription.setStatus("accepted");
            iscriptionRepository.save(inscription);

            sendAcceptanceNotificationToStudent(inscription);
            // Envoyer une notification à l'étudiant
            return true; // L'acceptation est réussie
        } else {
            return false; // L'inscription n'a pas été trouvée
        }
    }

    private void sendAcceptanceNotificationToStudent(Iscription inscription) {
        // Récupérer les informations nécessaires pour envoyer la notification à l'étudiant
        String studentEmail = inscription.getEmail();
        String studentName = inscription.getFirstName() + " " + inscription.getLastName();

        // Construire le message de notification
        String subject = "Confirmation d'inscription acceptée";
        String message = "Cher " + studentName + ",\n\nVotre inscription a été acceptée. Félicitations !";

        // Envoyer l'e-mail de notification à l'étudiant
        sendConfirmationEmail(studentEmail, subject, message); // Utilisez votre service de notification
    }

    public void sendConfirmationEmail(String recipient, String subject, String message) {
        // Logique pour envoyer l'e-mail de confirmation à l'étudiant
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage); // Envoyer l'e-mail de confirmation
    }


    public Long getTotalInscriptions() {
        return iscriptionRepository.count();
    }
}