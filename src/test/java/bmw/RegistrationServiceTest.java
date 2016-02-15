package bmw;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import bmw.model.Claass;
import bmw.model.Registration;
import bmw.service.FileReaderService;
import bmw.service.RegistrationServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    @Mock
    private FileReaderService fileReaderService;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    public void testStudentsRegisteredForCourse() {

        when(fileReaderService.fetchAll()).thenReturn(initList());
        assertTrue(3 == registrationService.studentsRegisteredForCourse(Claass.PHYSICS));
        assertTrue(1 == registrationService.studentsRegisteredForCourse(Claass.MATHEMATICS));
        assertTrue(0 == registrationService.studentsRegisteredForCourse(Claass.HISTORY));

    }

    @Test
    public void testStudentsTakingMoreThanOneCourse() {

        when(fileReaderService.fetchAll()).thenReturn(initList());
        assertTrue(1 == registrationService.studentsTakingMoreThanOneCourse());
    }

    @Test
    public void testReload() throws Exception {

    }

    private List<Registration> initList() {
        List<Registration> list = new ArrayList<>();
        list.add(new Registration(Claass.PHYSICS, "Prof Doe", 1));
        list.add(new Registration(Claass.MATHEMATICS, "Prof Smith", 2));
        list.add(new Registration(Claass.CHEMISTRY, "Prof Doe", 2));
        list.add(new Registration(Claass.PHYSICS, "Prof Doe", 2));
        list.add(new Registration(Claass.PHYSICS, "Prof Doe", 3));
        return list;
    }

}