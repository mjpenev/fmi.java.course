package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.*;

public class SocialNetworkImpl implements SocialNetwork {
    private Set<UserProfile> allUsers = new HashSet<>();
    private Collection<Post> allPosts = new HashSet<>();

    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile is null.");
        }
        if (this.allUsers.contains(userProfile)) {
            throw new UserRegistrationException("User is already registered.");
        }
        this.allUsers.add(userProfile);
    }

    @Override
    public Set<UserProfile> getAllUsers() {
        return Collections.unmodifiableSet(this.allUsers);
    }

    @Override
    public Post post(UserProfile userProfile, String content) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User is not recognized.");
        }
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Content is empty.");
        }
        if (!this.allUsers.contains(userProfile)) {
            throw new UserRegistrationException("User is not registered.");
        }
        SocialFeedPost newPost = new SocialFeedPost(userProfile, content);
        this.allPosts.add(newPost);
        return newPost;
    }

    @Override
    public Collection<Post> getPosts() {
        return Collections.unmodifiableCollection(this.allPosts);
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post is null.");
        }
        Set<UserProfile> result = new HashSet<>();
        Set<UserProfile> networkOfFriends = new HashSet<>();
        Set<UserProfile> visited = new HashSet<>();

        dfs(post.getAuthor(), visited, networkOfFriends);

        networkOfFriends.remove(post.getAuthor());

        for (UserProfile entry : networkOfFriends) {
            for (Interest interest : entry.getInterests()) {
                if (post.getAuthor().getInterests().contains(interest)) {
                    result.add(entry);
                    break;
                }
            }
        }

        return result;
    }

    private void dfs(UserProfile current, Set<UserProfile> visited, Set<UserProfile> network) {
        if (!visited.add(current)) {
            return;
        }
        network.add(current);

        for (UserProfile friend : current.getFriends()) {
            dfs(friend, visited, network);
        }
    }

    @Override
    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2) throws UserRegistrationException {
        if (userProfile1 == null || userProfile2 == null) {
            throw new IllegalArgumentException("At least one of the users is null.");
        }
        if (!(this.allUsers.contains(userProfile1)) || !(this.allUsers.contains(userProfile2))) {
            throw new UserRegistrationException("At least one of the users is not registered.");
        }

        Set<UserProfile> mutualFriends = new HashSet<>();
        for (UserProfile friend : userProfile1.getFriends()) {
            if (userProfile2.getFriends().contains(friend)) {
                mutualFriends.add(friend);
            }
        }

        return mutualFriends;
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        SortedSet<UserProfile> sortedUsers = new TreeSet<>(Comparator.comparingInt(u -> u.getFriends().size()));
        sortedUsers.addAll(this.allUsers);
        return sortedUsers;
    }
}
