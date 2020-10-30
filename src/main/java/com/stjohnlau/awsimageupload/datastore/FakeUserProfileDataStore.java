package com.stjohnlau.awsimageupload.datastore;

import com.stjohnlau.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    public static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("b0634e9c-4b43-4216-a2ca-ebeb1b52db69"),
                "fakeuser1", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("2029cebd-fed0-414a-b75a-5e9cb7cadfee"),
                "fakeuser2", null));
    }

    public List<UserProfile> getUserProfiles(){
        return USER_PROFILES;
    }
}
