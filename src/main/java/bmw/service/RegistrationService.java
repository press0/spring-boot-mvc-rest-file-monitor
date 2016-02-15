package bmw.service;

import java.util.List;

import bmw.model.Claass;
import bmw.model.Registration;

public interface RegistrationService {
    int studentsRegisteredForCourse(Claass aclass);
    int studentsTakingMoreThanOneCourse();
    List<Registration> fetchAll();
    void reload();
}
