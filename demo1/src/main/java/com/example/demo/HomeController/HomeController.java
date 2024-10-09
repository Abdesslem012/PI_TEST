package com.example.demo.HomeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello World!";
    }


    @GetMapping("/home")
    public String home() {
        return "index.html";
    }





    @GetMapping("/allCourse")
    public String allCourse() {
        return "all-courses.html";
    }

    @GetMapping("/addCourse")
    public String addCourse() {
        return "add-course.html";
    }

    @GetMapping("/editCourse")
    public String editCourse() {
        return "edit-course.html";
    }

    @GetMapping("/detailsCourse")
    public String detailsCourse() {
        return "details-course.html";
    }

    @GetMapping("/addStudent")
    public String addStudent() {
        return "add-student.html";
    }

    @GetMapping("/allStudent")
    public String allStudent() {
        return "all-students.html";
    }

    @GetMapping("/editStudent")
    public String editStudent() {
        return "edit-student.html"; }


    @GetMapping("/addMonitoring")
    public String addMonitoring() {
        return "add-monitoring.html";
    }

    @GetMapping("/allMonitoring")
    public String allMonitoring() {
        return "monitoring.html";
    }

    @GetMapping("/editMonitoring")
    public String editMonitoring() {
        return "edit-monitoring.html"; }

    @GetMapping("/addClasse")
    public String addClasse() {
        return "add-library-assets.html";
    }

    @GetMapping("/allClasse")
    public String allClasse() {
        return "library-assets.html";
    }

    @GetMapping("/editClasse")
    public String editClasse() {
        return "edit-library-assets.html";
    }

    @GetMapping("/addTeacher")
    public String addTeacher() {
        return "add-professor.html";
    }

    @GetMapping("/allTeacher")
    public String allTeacher() {
        return "all-professors.html";
    }

    @GetMapping("/editTeacher")
    public String editTeacher() {
        return "edit-professor.html";
    }
    @GetMapping("/addProgramme")
    public String addProgramme() {
        return "add-department.html";
    }
    @GetMapping("/allProgramme")
    public String allProgramme() {
        return "departments.html";
    }
    @GetMapping("/editProgramme")
    public String editProgramme() {
        return "edit-department.html";
    }

    @GetMapping("/addGroupeStudent")
    public String addGroupeStudent() {
        return "add-GroupeStudent.html";
    }

    @GetMapping("/allGroupeStudent")
    public String allGroupeStudent() {
        return "GroupeStudents.html";
    }

    @GetMapping("/consulterQuestion")
    public String ConsulterQuestion() {
        return "consulter-question.html";
    }

    @GetMapping("/editGroupeStudent")
    public String editGroupeStudent() {
        return "edit-GroupeStudent.html";
    }
    @GetMapping("/addSector")
    public String addSector() {
        return "add-sector.html";
    }

    @GetMapping("/allSector")
    public String allSector() {
        return "sectors.html";
    }

    @GetMapping("/editSector")
    public String editSector() {
        return "edit-sector.html";
    }

    @GetMapping("/addPresence")
    public String addPresence() {
        return "add-presence.html";
    }

    @GetMapping("/allPresence")
    public String allPresence() {
        return "presences.html";
    }

    @GetMapping("/editPresence")
    public String editPresence() {
        return "edit-presence.html";
    }

    @GetMapping("/addUnit")
    public String addUnit() {
        return "add-unit.html";
    }

    @GetMapping("/allUnit")
    public String allUnit() {
        return "units.html";
    }

    @GetMapping("/editUnit")
    public String editUnit() {
        return "edit-unit.html";
    }

    @GetMapping("/addLesson")
    public String addLesson() {
        return "add-lesson.html";
    }

    @GetMapping("/allLesson")
    public String allLesson() {
        return "lesson.html";
    }


    @GetMapping("/affecterLesson")
    public String alllLesson() {
        return "affecter-lessons.html";
    }

    @GetMapping("/editlesson")
    public String editLesson() {
        return "edit-lesson.html";
    }

    @GetMapping("/addRessource")
    public String addRessource() {
        return "add-ressource.html";
    }

    @GetMapping("/allRessource")
    public String allRessource() {
        return "ressources.html";
    }

    @GetMapping("/editRessource")
    public String editRessource() {
        return "edit-ressource.html";
    }

    @GetMapping("/addObjective")
    public String addObjective() {
        return "add-objective.html";
    }

    @GetMapping("/allObjective")
    public String allObjective() {
        return "objectives.html";
    }

    @GetMapping("/editObjective")
    public String editObjective() {
        return "edit-objective.html";
    }

    @GetMapping("/addFeedback")
    public String addFeedback() {
        return "add-feedback.html";
    }

    @GetMapping("/allFeedback")
    public String allFeedback() {
        return "feedbacks.html";
    }

    @GetMapping("/editFeedback")
    public String editFeedback() {
        return "edit-feedback.html";
    }

    @GetMapping("/addRating")
    public String addRating() {
        return "add-rating.html";
    }

    @GetMapping("/allRating")
    public String allRating() {
        return "ratings.html";
    }

    @GetMapping("/editRating")
    public String editRating() {
        return "edit-rating.html";
    }

    @GetMapping("/addQuiz")
    public String addQuiz() {
        return "add-quiz.html";
    }

    @GetMapping("/allQuiz")
    public String allQuiz() {
        return "quizs.html";
    }

    @GetMapping("/editQuiz")
    public String editQuiz() {
        return "edit-quiz.html";
    }


    @GetMapping("/addQuestion")
    public String addQuestion() {
        return "add-question.html";
    }

    @GetMapping("/allQuestion")
    public String allQuestion() {
        return "questions.html";
    }

    @GetMapping("/editQuestion")
    public String editQuestion() {
        return "edit-question.html";
    }

    @GetMapping("/addEvent")
    public String addEvent() {
        return "add-event.html";
    }

    @GetMapping("/allEvent")
    public String allEvent() {
        return "events.html";
    }

    @GetMapping("/editEvent")
    public String editEvent() {
        return "edit-event.html";
    }

    @GetMapping("/addSpecialtie")
    public String addSpecialtie() {
        return "add-specialtie.html";
    }

    @GetMapping("/allSpecialtie")
    public String allSpecialtie() {
        return "specialties.html";
    }

    @GetMapping("/editSpecialtie")
    public String editSpecialtie() {
        return "edit-specialtie.html";
    }

    @GetMapping("/addStudentToClass")
    public String addStudentToClass() {
        return "add-student-class.html";
    }

    @GetMapping("/calender")
    public String calender() {
        return "calender.html";
    }
    @GetMapping("/viewRessource")
    public String viewRessource() {
        return "view-ressource.html";
    }

    @GetMapping("/affecterQuestion")
    public String affecterQuestionQuiz() {
        return "affecter-question-quiz.html";
    }

    @GetMapping("/addStudentToGroup")
    public String addStudentToGroup() {
        return "add-student-groupe.html";
    }

}
