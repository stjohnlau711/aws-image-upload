package com.stjohnlau.awsimageupload.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 s3() { //initalize s3 bucket
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                "AKIAJY4PRHET6JPCK74A",
                "DrRjKOFVokfzxf6BaXyJbJQuL8bBa7XrduBAszv4"
        );

        AmazonS3 s3 = AmazonS3Client
                .builder()
                .withRegion("us-east-2")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        /*return AmazonS3ClientBuilder <- This way doesn't work for some reason
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();*/
        return s3;
    }
}
