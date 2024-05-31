package github.Zcy19980412.web;



import github.Zcy19980412.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HabitController {

    @Autowired
    private HabitService habitService;



}
