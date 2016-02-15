package bmw;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import bmw.controller.MVCRestController;
import bmw.model.Claass;
import bmw.model.Registration;
import bmw.service.RegistrationService;

@RunWith(MockitoJUnitRunner.class)
public class MVCRestControllerTest {

    @InjectMocks
    MVCRestController registrationController;

    @Mock
    private RegistrationService registrationService;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void testStudentsRegisteredForCourse_case_insensitive() throws Exception {
        when(registrationService.studentsRegisteredForCourse(Claass.PHYSICS)).thenReturn(0);
        mvc.perform(MockMvcRequestBuilders.get("/register/studentsRegisteredForCourse/pHySiCs"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("0")));

        when(registrationService.studentsRegisteredForCourse(Claass.CHEMISTRY)).thenReturn(2);
        mvc.perform(MockMvcRequestBuilders.get("/register/studentsRegisteredForCourse/cHeMiStRy"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2")));

    }

    @Test
    public void testStudentsTakingMoreThanOneCourse() throws Exception {
        when(registrationService.studentsTakingMoreThanOneCourse()).thenReturn(1);
        mvc.perform(MockMvcRequestBuilders.get("/register/studentsTakingMoreThanOneCourse"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));
    }

    @Test
    public void testFetchAll() throws Exception {
        when(registrationService.fetchAll()).thenReturn(initList());
        mvc.perform(MockMvcRequestBuilders.get("/register/fetchAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[1].claass", is("CHEMISTRY")))
                .andExpect(jsonPath("$[1].professor", is("Jane")))
                .andExpect(jsonPath("$[1].studentId", is(3455)));
    }

    @Test
    public void testReload() throws Exception {

    }


    private List<Registration> initList() {
        List<Registration> list = new ArrayList<>();
        list.add(new Registration(Claass.CHEMISTRY, "Joseph", 1234));
        list.add(new Registration(Claass.CHEMISTRY, "Jane", 3455));
        list.add(new Registration(Claass.HISTORY, "Jane", 3455));
        list.add(new Registration(Claass.MATHEMATICS, "Doe", 56767));
        return list;
    }
}