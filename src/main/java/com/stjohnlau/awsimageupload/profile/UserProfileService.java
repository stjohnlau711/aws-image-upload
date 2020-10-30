package com.stjohnlau.awsimageupload.profile;

import com.stjohnlau.awsimageupload.bucket.BucketName;
import com.stjohnlau.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile user = getUserProfileOrThrow(userProfileId);
        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getUserProfileId());

        return user.getUserProfileImageLink().map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }

    List<UserProfile> getUserProfiles(){

        return userProfileDataAccessService.getUserProfiles();
    }


    void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        if(file.isEmpty()){ //check if file is empty
            throw new IllegalStateException("Cannot upload empty file");
        }

        checkContentType(file); //check if file is jpeg, png, or gif (extracted method)

        UserProfile user = getUserProfileOrThrow(userProfileId); //check if user exists (extracted method)

        Map<String, String> metadata = new HashMap<>(); //grab metadata
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String filename = String.format("%s-%s", file.getOriginalFilename(),UUID.randomUUID());
        try { //save to bucket
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            user.setUserProfileImageLink(filename);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    private void checkContentType(MultipartFile file) {
        if(!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType()
                , ContentType.IMAGE_PNG.getMimeType()
                ,ContentType.IMAGE_GIF.getMimeType())
        .contains(file.getContentType())){ //check content type
            throw new IllegalStateException("File needs to be JPEG, PNG, or GIF");
        }
    }

    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        UserProfile user = userProfileDataAccessService //check if user exists
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User does not exist"));
        return user;
    }

}
