package com.wgc.wgcapi.Configuration.Stroage.S3;
/*
Created on 2023/09/10 2:17 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Configuration {

    @Value("${cloud.aws.credentials.accessKey}")
    String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    String secretKey;

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(
                        new AWSStaticCredentialsProvider(awsCredentials)
                )
                .build();
    }

}
