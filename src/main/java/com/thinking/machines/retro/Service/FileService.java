package com.thinking.machines.retro.Service;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
public interface FileService {
    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
