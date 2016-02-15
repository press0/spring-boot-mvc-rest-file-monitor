package bmw.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmw.model.Claass;
import bmw.model.Registration;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    FileReaderService fileReaderService;

    @Autowired
    public RegistrationServiceImpl(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    @Override
    public int studentsRegisteredForCourse(Claass aclass) {
        List<Registration> list = fileReaderService.fetchAll();

        int i = 0;
        for (Registration registration : list) {
            if (registration.getClaass().equals(aclass)) {
                i++;
            }
        }
        return i;
    }

    @Override
    public int studentsTakingMoreThanOneCourse() {
        List<Registration> list = fileReaderService.fetchAll();

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Registration registration : list) {
            if (frequencyMap.get(registration.getStudentId()) == null) {
                frequencyMap.put(registration.getStudentId(), 1);
            } else {
                frequencyMap.put(registration.getStudentId(), frequencyMap.get(registration.getStudentId()) + 1);
            }
        }

        int i = 0;
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > 1) {
                i++;
            }
        }
        return i;
    }

    @Override
    public List<Registration> fetchAll() {
        return fileReaderService.fetchAll();
    }

    @Override
    public void reload() {
        fileReaderService.reload();
    }
}
