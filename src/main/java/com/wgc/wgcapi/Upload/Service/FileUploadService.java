package com.wgc.wgcapi.Upload.Service;
/*
Created on 2023/08/21 9:26 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService {

    private AmazonS3 client;

    private String bucket;

    public FileUploadService(@Value("${cloud.aws.s3.bucket}") String bucket,
                             @Value("${cloud.aws.credentials.accessKey}") String accessKey,
                             @Value("${cloud.aws.credentials.secretKey}") String secretKey,
                             @Value("${cloud.aws.credentials.region}") String region) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        this.client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();

        this.bucket = bucket;
    }

    public ResponseDto upload(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
        try {
            String url = this.upload(file);
            return new ResponseDto(url);
        } catch (IOException e) {
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public String upload(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = this.filePath() + fileName;
        PutObjectRequest requestObject = getRequestObject(filePath, file);
        client.putObject(requestObject);
        return client.getUrl(bucket, filePath).toString();
    }

    private PutObjectRequest getRequestObject(String path, MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = setMetadata(file);

        return new PutObjectRequest(this.bucket, path, inputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
    }

    private ObjectMetadata setMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        return metadata;
    }

    private String filePath() {
        String dateFormat = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return dateFormat + UUID.randomUUID().toString();
    }

}
