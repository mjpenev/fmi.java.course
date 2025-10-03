package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.*;

public class DefaultUserProfile implements UserProfile {
    private final String username;
    private Set<Interest> interests = new HashSet<>();
    private Set<UserProfile> friends = new HashSet<>();
    public DefaultUserProfile(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Collection<Interest> getInterests() {
        return Collections.unmodifiableSet(this.interests);
    }

    @Override
    public boolean addInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("Interest is null.");
        }
        return this.interests.add(interest);
    }

    @Override
    public boolean removeInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("Interest is null.");
        }
        return this.interests.remove(interest);
    }

    @Override
    public Collection<UserProfile> getFriends() {
        return this.friends;
    }

    @Override
    public boolean addFriend(UserProfile userProfile) {
        if (this == userProfile || userProfile == null) {
            throw new IllegalArgumentException("Illegal operation.");
        }
        if (this.friends.add(userProfile)) {
            userProfile.getFriends().add(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        if (this == userProfile || userProfile == null) {
            throw new IllegalArgumentException("Illegal operation.");
        }
        if (this.friends.remove(userProfile)) {
            userProfile.getFriends().remove(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        return this.friends.contains(userProfile);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultUserProfile other)) {
            return false;
        }
        return this.username.equals(other.username);
    }
}
