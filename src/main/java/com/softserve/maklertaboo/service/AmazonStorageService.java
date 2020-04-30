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
import org.springframework.transaction.annotation.Transactional;
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
     * The method that set credentials to amazon client.
     *
     * @author Vadym Puiko
     */
    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1).build();
        log.info("Initialize Amazon was successfully");
    }

    /**
     * The general method that upload file
     *
     * @param multipartFile multipartFile
     * @return {@link String}
     * @author Vadym Puiko
     */
    @Transactional
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
                log.info("File was uploaded successfully");
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Caught an AmazonClientException: ");
                log.info("Error Message: " + e.getMessage());
            }
            return fileUrl;
        } else {
            log.info("File is not exists");
            return "";
        }
    }

    /**
     * The method that delete file from s3 bucket
     *
     * @param fileUrl for deleting.
     * @author Vadym Puiko
     */
    @Transactional
    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(bucketName, fileName);
        log.info("File was deleted successfully");
    }

    /**
     * The method that upload file to s3 bucket
     *
     * @param fileName    string
     * @param inputStream inputStream
     * @param metadata    objectMetadata
     * @author Vadym Puiko
     */
    private void uploadFileTos3bucket(String fileName, InputStream inputStream, ObjectMetadata metadata) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
        log.info("File was uploaded successfully to S3 Bucket");
    }

    /**
     * The method that generate file name
     *
     * @return {@link String}
     * @author Vadym Puiko
     */
    public String generateFileName() {
        log.info("File name was generated successfully");
        return UUID.randomUUID().toString();
    }

    /**
     * The method that convert MultipartFile to InputStream.
     *
     * @param multipartFile MultipartFile
     * @return {@link InputStream}
     * @author Vadym Puiko
     */
    private InputStream convertMultiPartToInputStream(MultipartFile multipartFile) {
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            log.info("Error Message: " + e.getMessage());
        }
        log.info("MultiPartFile to InputStream was converted successfully");
        return inputStream;
    }

    public String uploadPdfFile(MultipartFile multipartFile) {
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
                log.info("File was uploaded successfully");
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Caught an AmazonClientException: ");
                log.info("Error Message: " + e.getMessage());
            }
            return fileUrl;
        } else {
            log.info("File is not exists");
            return "";
        }
    }

    public void uploadAgreementTos3bucket(String bucketName, String fileName, InputStream inputStream, ObjectMetadata metadata) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
        log.info("File was uploaded successfully to S3 Bucket");
    }
}