package bmw.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import bmw.model.Claass;
import bmw.model.Registration;

@Repository
public class FileReaderServiceImpl implements FileReaderService {

    final private Logger logger = LoggerFactory.getLogger(getClass());

    private CopyOnWriteArrayList<Registration> list = new CopyOnWriteArrayList<>();

    final private String file;

    @Autowired
    public FileReaderServiceImpl(@Value("${file}")String file) {
        this.file = file;
        init();
    }

    @Override
    public List<Registration> fetchAll() {
        return list;
    }

    @Override
    public void reload() {
        init();
    }

    private void init() {
        list = new CopyOnWriteArrayList<>();
        readFile();
    }

    private void readFile() {

        Registration registration;
        String line;
        StringTokenizer st;
        String s;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            is = this.getClass().getClassLoader().getResourceAsStream(file);
            if (is == null) {
                logger.info(file + ": not found");
                return;
            }
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                st = new StringTokenizer(line, ",");
                if (!st.hasMoreTokens()) {
                    continue;
                }
                registration = new Registration();
                s = st.nextToken().trim().toUpperCase();
                registration.setClaass(Claass.valueOf(s));
                registration.setProfessor(st.nextToken().trim());
                registration.setStudentId(Integer.parseInt(st.nextToken().trim()));
                list.add(registration);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
