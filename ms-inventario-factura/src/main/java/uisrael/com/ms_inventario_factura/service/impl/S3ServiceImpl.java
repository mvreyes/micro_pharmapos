package uisrael.com.ms_inventario_factura.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uisrael.com.ms_inventario_factura.service.S3Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3ServiceImpl implements S3Service {
    private final AmazonS3 s3Client;
    private final String bucketName;

    public S3ServiceImpl(@Value("${aws.access.key.id}") String accessKeyId,
                     @Value("${aws.secret.access.key}") String secretAccessKey,
                     @Value("${aws.s3.bucket.name}") String bucketName,
                     @Value("${aws.region}") String region) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
        this.bucketName = bucketName;
    }

    public String subirArchivo(File file) {
        String fileName = System.currentTimeMillis() + "_" + file.getName();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead)); // Hacer público el objeto
        return s3Client.getUrl(bucketName, fileName).toString(); // Retorna la URL del archivo
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, null));
        return s3Client.getUrl(bucketName, fileName).toString(); // Devuelve la URL de la imagen
    }

    @Override
    public byte[] getFile(String fileName) {
        try {
            return s3Client.getObject(bucketName, fileName).getObjectContent().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
