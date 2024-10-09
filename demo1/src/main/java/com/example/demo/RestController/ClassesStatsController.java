/*package com.example.demo.RestController;


import com.example.demo.Entity.ClassesStats;
import com.example.demo.Service.ClassesStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/classes")
public class ClassesStatsController {

    @Autowired
    private ClassesStatsService statsService;

    @GetMapping("/{classId}/stats")
    public ClassesStats getClassStats(@PathVariable Long classId) {
        return statsService.getClassStats(classId);
    }
}*/

