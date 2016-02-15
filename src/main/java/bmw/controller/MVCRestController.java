package bmw.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bmw.service.RegistrationService;
import bmw.model.Claass;
import bmw.model.Registration;
import bmw.util.CaseConverter;

@Controller
@RequestMapping(value = "/register")
public class MVCRestController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private AtomicInteger i = new AtomicInteger(0);

    RegistrationService registrationService;

    @Autowired
    public MVCRestController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping("/studentsRegisteredForCourse/{claass}")
    @ResponseBody
    public int studentsRegisteredForCourse(@PathVariable Claass claass) {
        return registrationService.studentsRegisteredForCourse(claass);
    }

    @RequestMapping("/studentsTakingMoreThanOneCourse")
    @ResponseBody
    public int studentsTakingMoreThanOneCourse() {
        return registrationService.studentsTakingMoreThanOneCourse();
    }

    @RequestMapping("/fetchAll")
    @ResponseBody
    public List<Registration> fetchAll() {
        logger.info(""+i.incrementAndGet());
        return registrationService.fetchAll();
    }

    @RequestMapping("/reload")
    public String reload() {
        registrationService.reload();
        return "reloading csv file";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Claass.class, new CaseConverter<>(Claass.class));
    }

}
