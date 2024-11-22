package uisrael.com.ms_inventario_factura.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface S3Service {
    public String subirArchivo(File file);
    public String uploadFile(MultipartFile file) throws IOException;
    public byte[] getFile(String fileName);
}
