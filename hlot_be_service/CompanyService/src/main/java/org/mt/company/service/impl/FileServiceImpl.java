package org.mt.company.service.impl;

import org.mt.company.service.FileService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public byte[] download() throws IOException {
        byte[] data = null;
        try{
            Path p = Paths.get("/var/temp/test.pdf");
            data = Files.readAllBytes(p);
        } catch(IOException e){
            throw new IOException("IOE Error message >> " + e.getMessage());
        }
        return data;
    }

}
