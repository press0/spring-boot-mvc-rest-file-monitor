package bmw.service;

import java.util.List;

import bmw.model.Registration;

public interface FileReaderService {

    void reload();
    List<Registration> fetchAll();

}
