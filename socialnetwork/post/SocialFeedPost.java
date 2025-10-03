package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.*;

public class SocialFeedPost implements Post{
    private String id;
    private UserProfile author;
    private LocalDateTime publishedOnDate;
    private String content;
    private Map<ReactionType, Set<UserProfile>> reactions = new HashMap<>();

    public SocialFeedPost(UserProfile author, String content) {
        this.id = UUID.randomUUID().toString();
        this.author = author;
        this.content = content;
        this.publishedOnDate = LocalDateTime.now();
    }

    @Override
    public String getUniqueId() {
        return this.id;
    }

    @Override
    public UserProfile getAuthor() {
        return this.author;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return this.publishedOnDate;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public boolean addReaction(UserProfile userProfile, ReactionType reactionType) {
        if (userProfile == null || reactionType == null) {
            throw new IllegalArgumentException("Reaction is not recognized");
        }
        if (!this.reactions.containsKey(reactionType)) {

            this.reactions.put(reactionType, new HashSet<UserProfile>());
        }

        return this.reactions.get(reactionType).add(userProfile);
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User not recognized");
        }
        boolean result = false;
        for (Map.Entry<ReactionType, Set<UserProfile>> entry : this.reactions.entrySet()) {
            if (entry.getValue().remove(userProfile)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {
        return Collections.unmodifiableMap(this.reactions);
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        if (reactionType == null) {
            throw new IllegalArgumentException("Reaction type is null.");
        }
        return this.reactions.get(reactionType).size();
    }

    @Override
    public int totalReactionsCount() {
        int countTotal = 0;
        for (Map.Entry<ReactionType, Set<UserProfile>> entry : this.reactions.entrySet()) {
            countTotal += entry.getValue().size();
        }

        return countTotal;
    }
}
