package com.softserve.maklertaboo.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class AmazonStorageService {
    private AmazonS3 s3client;
    private String bucketName;
    private String accessKey;
    private String secretAccessKey;
    private String endpointUrl;

    /**
     * Constructor with parameters
     *
     * @author Vadym Puiko
     */
    @Autowired
    public AmazonStorageService(
            @Value("${BUCKET_NAME}") String bucketName,
            @Value("${ACCESS_KEY}") String accessKey,
            @Value("${SECRET_KEY}") String secretAccessKey,
            @Value("${ENDPOINT_URL}") String endpointUrl) {
        this.bucketName = bucketName;
        this.accessKey = accessKey;
        this.secretAccessKey = secretAccessKey;
        this.endpointUrl = endpointUrl;
    }

    /**
     * Method that set credentials to amazon client
     *
     * @author Vadym Puiko
     */
    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1).build();
    }

    /**
     * Method that upload file
     *
     * @param multipartFile
     * @return String
     * @author Vadym Puiko
     */
    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        if (multipartFile != null) {
            try {
                String fileName = generateFileName();
                fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
                InputStream inputStream = convertMultiPartToInputStream(multipartFile);
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(multipartFile.getContentType());
                metadata.setContentLength(multipartFile.getSize());
                uploadFileTos3bucket(fileName, inputStream, metadata);
                inputStream.close();
                log.info("Successfully upload");
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Caught an AmazonClientException: ");
                log.info("Error Message: " + e.getMessage());
            }
            return fileUrl;
        } else {
            return "File is not exists";
        }
    }

    /**
     * Method delete file
     *
     * @param fileUrl
     * @author Vadym Puiko
     */
    public void deleteFile(String fileUrl) {
        s3client.deleteObject(bucketName, fileUrl);
        log.info("Successfully deleted");
    }

    /**
     * Method that upload file to s3 bucket
     *
     * @param fileName,inputStream,metadata
     * @author Vadym Puiko
     */
    private void uploadFileTos3bucket(String fileName, InputStream inputStream, ObjectMetadata metadata) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
    }

    /**
     * Method that generate file name
     *
     * @return String
     * @author Vadym Puiko
     */
    private String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * Method that convert MultipartFile to InputStream
     *
     * @param multipartFile
     * @return InputStream
     * @author Vadym Puiko
     */
    private InputStream convertMultiPartToInputStream(MultipartFile multipartFile) {
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            log.info("Error Message: " + e.getMessage());
        }
        return inputStream;
    }
}