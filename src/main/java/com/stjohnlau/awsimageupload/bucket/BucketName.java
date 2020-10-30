package com.stjohnlau.awsimageupload.bucket;

public enum BucketName {
    PROFILE_IMAGE("stjohnlau-aws-image-upload");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
