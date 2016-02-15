package bmw.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileMonitorService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String monitorFile;
    private String monitorDirectory;

    private static final long pollingInterval = 2 * 1000;

    FileReaderService fileReaderService;

    @Autowired
    public FileMonitorService(FileReaderService fileReaderService,
                              @Value("${file}") String file,
                              @Value("${directory}") String directory) {
        this.fileReaderService = fileReaderService;
        this.monitorFile = file;
        this.monitorDirectory = directory;
        monitor();
    }

    public void monitor() {
        String currentPath;
        try {
            currentPath = (new File(".")).getCanonicalPath();
        } catch (IOException e) {
            logger.error(e.toString());
            return;
        }
        String path = currentPath + File.separator + monitorDirectory;
        logger.info("Current path:" + currentPath);
        logger.info("monitoring: " + monitorFile + " in " + path);
        final File directory = new File(path);
        final NameFileFilter nameFileFilter = new NameFileFilter(monitorFile);
        final FileAlterationObserver fao = new FileAlterationObserver(directory, nameFileFilter);
        final FileAlterationMonitor monitor = new FileAlterationMonitor(pollingInterval);
        fao.addListener(new FileAlterationListenerAdaptor() {
                            @Override
                            public void onFileChange(final File file) {
                                logger.info(file.getAbsoluteFile() + " was modified. Reloading");
                                fileReaderService.reload();
                            }
                        }
        );
        monitor.addObserver(fao);
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
