package com.edi_converter.watchservice;

import com.edi_converter.Main;
import com.edi_converter.entity.Converter;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class WatchServiceCall {

    private static final Logger log = LoggerFactory.getLogger(WatchServiceCall.class);
    
    private FileAlterationObserver fileAlterationObserver;
    private FileAlterationMonitor fileAlterationMonitor;
    private FileAlterationListener fileAlterationListener;
    
    public WatchServiceCall(Converter converter, String hotfolder) {
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            @Override
            public void onFileCreate(File file) {
                log.info("File: " + file.getName() + " created");
                String uploadedFile = hotfolder+ "/" + file.getName();
                converter.setFileName(uploadedFile);
                converter.runEngine();
            }

            @Override
            public void onFileDelete(File file) {
                log.info("File: " + file.getName() + " deleted");
            }

            @Override
            public void onFileChange(File file) {
                log.info("File: " + file.getName() + " changed");
            }
        };
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(hotfolder);
        fileAlterationObserver.addListener(listener);
        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(500);
        fileAlterationMonitor.addObserver(fileAlterationObserver);
        try {
            fileAlterationMonitor.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
