package com.example.demo.DTO;


import com.example.demo.Entity.Student;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;


@Getter
@Setter
public class MoitoringAcadimicObjectivesDTO {

@Id
private Long maoId;




    @NotNull
    private String subject;


    @NotNull
    private double average;

    private String description;


    private String deadline;

    private Student studentId;

    // Ajoutez les setters pour successRate et attendancePercentage
    private double successRate;
    private int attendancePercentage;

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public void setAttendancePercentage(int attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

}
