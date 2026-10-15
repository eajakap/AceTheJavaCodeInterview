package org.codesignal.debugging.problems;

import java.util.*;

public class SocialMediaInteractionSystem {
    private final Map<String, Post> posts;
    private final Map<String, List<String>> groupPosts;
    private final Map<String, Group> groups;

    public SocialMediaInteractionSystem() {
        posts = new HashMap<>();
        groupPosts = new HashMap<>();
        groups = new HashMap<>();
    }

    public boolean createPost(String postId, String content) {
        if (posts.containsKey(postId)) return false;
        posts.put(postId, new Post(content));
        return true;
    }

    public boolean deletePost(String postId) {
        if (!posts.containsKey(postId)) return false;
        posts.remove(postId);
        return true;
    }

    public boolean reactToPost(String userId, String postId, String reactionType) {
        Post post = posts.get(postId);
        if (post == null) return false;
        return post.addReaction(userId, reactionType);
    }

    public Map<String, Integer> getReactionSummary(String postId) {
        Post post = posts.get(postId);
        return post == null ? null : post.getReactionSummary();
    }

    public boolean createGroup(String groupId, String operatorId) {
        if (groupPosts.containsKey(groupId)) return false;
        groupPosts.computeIfAbsent(groupId, k->new ArrayList<>());
        groups.put(groupId, new Group(operatorId));
        return true;
    }

    public boolean addMember(String groupId, String userId, String operatorId) {
        Group group = groups.get(groupId);
        if ( group == null ) return false;
        if (!operatorId.equals(group.getOperatorId())) {
            return false;
        }
        if (group.getMembers().contains(userId)) {
            return false;
        }
        group.addMember(userId);
        return true;
    }

    public boolean sharePostToGroup(String postId, String groupId, String userId) {
        Post post = posts.get(postId);
        if (post == null) return false;
        Group group = groups.get(groupId);
        if ( group == null ) return false;
        List<String> members = group.getMembers();
        if (!members.contains(userId) || !groupPosts.containsKey(groupId)) {
            return false;
        }
        List<String> feed = groupPosts.get(groupId);
        feed.add(postId);
        return true;
    }

    public List<String> getGroupPosts(String groupId) {
        return groupPosts.get(groupId);
    }

    private static class Group {
        private String operatorId;
        private List<String> members;

        public Group(String operatorId) {
            this.operatorId = operatorId;
            members = new ArrayList<>();
            members.add(operatorId);
        }

        public String getOperatorId() {
            return operatorId;
        }

        public List<String> getMembers() {
            return members;
        }

        public void addMember(String memberId) {
            members.add(memberId);
        }
        public void removeMember(String memberId) {
            members.remove(memberId);
        }

    }



    private static class Post {
        private final String content;
        private final Map<String, String> reactions;

        public Post(String content) {
            this.content = content;
            this.reactions = new HashMap<>();
        }

        public boolean addReaction(String userId, String reactionType) {
            if (reactions.containsKey(userId)) return false;
            reactions.put(userId, reactionType);
            return true;
        }

        public Map<String, Integer> getReactionSummary() {
            Map<String, Integer> summary = new HashMap<>();
            for (String reaction : reactions.values()) {
                summary.put(reaction, summary.getOrDefault(reaction, 0) + 1);
            }
            return summary;
        }
    }

    public static void main(String[] args) {
        SocialMediaInteractionSystem system = new SocialMediaInteractionSystem();
        system.createPost("post1", "Hello World!");
        system.reactToPost("user1", "post1", "like");
        system.reactToPost("user2", "post1", "love");
        System.out.println(system.getReactionSummary("post1")); // {like=1, love=1}
        system.createGroup("group1", "user1");
        system.addMember("group1", "user2", "user1");
        system.sharePostToGroup("post1", "group1", "user2");
        System.out.println(system.getGroupPosts("group1")); // [post1]
    }
}