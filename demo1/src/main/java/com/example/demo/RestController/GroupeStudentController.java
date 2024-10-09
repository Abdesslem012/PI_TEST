/*package com.example.demo.RestController;

import com.example.demo.DTO.GroupeStudentDTO;
import com.example.demo.Entity.Student;
import com.example.demo.Service.GroupeStudentService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/groupeStudents", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupeStudentController {
    private final GroupeStudentService groupeStudentService;

    public GroupeStudentController(final GroupeStudentService groupeStudentService) {
        this.groupeStudentService = groupeStudentService;
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<GroupeStudentDTO>> getAllGroupeStudents() {
        return ResponseEntity.ok(groupeStudentService.findAll());
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @GetMapping("/{groupstudentId}")
    public ResponseEntity<GroupeStudentDTO> getGroupeStudent(
            @PathVariable(name = "groupstudentId") final Long groupstudentId) {
        return ResponseEntity.ok(groupeStudentService.get(groupstudentId));
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGroupeStudent(
            @RequestBody @Valid final GroupeStudentDTO groupeStudentDTO) {
        final Long createdGroupstudentId = groupeStudentService.create(groupeStudentDTO);
        return new ResponseEntity<>(createdGroupstudentId, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @PutMapping("/{groupstudentId}")
    public ResponseEntity<Long> updateGroupeStudent(
            @PathVariable(name = "groupstudentId") final Long groupstudentId,
            @RequestBody @Valid final GroupeStudentDTO groupeStudentDTO) {
        groupeStudentService.update(groupstudentId, groupeStudentDTO);
        return ResponseEntity.ok(groupstudentId);
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @DeleteMapping("/{groupstudentId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGroupeStudent(
            @PathVariable(name = "groupstudentId") final Long groupstudentId) {
        final ReferencedWarning referencedWarning = groupeStudentService.getReferencedWarning(groupstudentId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        groupeStudentService.delete(groupstudentId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{nomGroupe}/assignStudents")
    public ResponseEntity<Void> assignStudentsToGroup(
            @PathVariable(name = "nomGroupe") String nomGroupe,
            @RequestBody Set<Student> studentId) {
        groupeStudentService.assignStudentsToGroup(nomGroupe, studentId);
        return ResponseEntity.ok().build();
    }

}

*/