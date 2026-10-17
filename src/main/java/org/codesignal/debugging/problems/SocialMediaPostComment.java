package org.codesignal.debugging.problems;

import java.util.*;

public class SocialMediaPostComment {
    private Map<String, Post> posts;
    private Map<String, Comment> comments;
    private Map<String, Deque<String>> postContentHistory;
    private Map<String, Deque<String>> commentContentHistory;

    public SocialMediaPostComment() {
        this.posts = new HashMap<>();
        this.comments = new HashMap<>();
        this.postContentHistory = new HashMap<>();
        this.commentContentHistory = new HashMap<>();
    }

    public boolean createPost(String postId, String content, long timestamp) {
        if (posts.containsKey(postId)) {
            return false;
        }
        posts.put(postId, new Post(content, timestamp));
        return true;
    }

    public boolean editPost(String postId, String newContent) {
        if (!posts.containsKey(postId)) {
            return false;
        }
        // archive the current content
        Post post = posts.get(postId);
        postContentHistory.computeIfAbsent(postId, dq->new ArrayDeque<>()).push(post.getContent());
        // update with latest content now
        posts.get(postId).setContent(newContent);
        return true;
    }

    public boolean deletePost(String postId) {
        if (!posts.containsKey(postId)) {
            return false;
        }
        if (!postContentHistory.containsKey(postId)) {
            return false;
        }
        // clear history
        Deque<String> dq = postContentHistory.get(postId);
        dq.clear();
        // remove post
        posts.remove(postId);
        return true;
    }

    public boolean likePost(String postId) {
        if (!posts.containsKey(postId)) {
            return false;
        }
        posts.get(postId).incrementLikes();
        return true;
    }

    public List<String> topNLikedPosts(int n) {
        return posts.entrySet().stream()
                .sorted((e1, e2) -> {
                    int cmp = Integer.compare(e2.getValue().getLikes(), e1.getValue().getLikes());
                    return cmp != 0 ? cmp : Long.compare(e1.getValue().getTimestamp(), e2.getValue().getTimestamp());
                })
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }

    // Getters for testing purposes
    public Map<String, Post> getPosts() {
        return posts;
    }

    public Map<String, Comment> getComments() {
        return comments;
    }

    public boolean commentOnPost(String postId, String commentId, String content, long timestamp) {
        if (!posts.containsKey(postId) || comments.containsKey(commentId)) {
            return false;
        }
        comments.put(commentId, new Comment(content, timestamp));
        posts.get(postId).addComment(commentId);
        return true;
    }

    public List<String> topNCommentedPosts(int n) {
        return posts.entrySet().stream()
                .sorted((e1, e2) -> {
                    int cmp = Integer.compare(e2.getValue().getComments().size(), e1.getValue().getComments().size());
                    return cmp != 0 ? cmp : Long.compare(e1.getValue().getTimestamp(), e2.getValue().getTimestamp());
                })
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }

    public boolean editComment(String postId, String commentId, String newComment) {
        if (!posts.containsKey(postId)) {
            return false;
        }
        if (!comments.containsKey(commentId)) {
            return false;
        }
        // archive the current content
        Comment comment = comments.get(commentId);
        commentContentHistory.computeIfAbsent(commentId, dq->new ArrayDeque<>()).push(comment.getContent());

        // update the comments for the POST at both the places.
        comment.setContent(newComment);
        return true;
    }

    public boolean rollbackPostEdit(String postId) {
        if (!posts.containsKey(postId)) {
            return false;
        }
        if (!postContentHistory.containsKey(postId) || postContentHistory.get(postId).isEmpty()) {
            return false;
        }

        // rollback to the previous comment
        posts.get(postId).setContent(postContentHistory.get(postId).pop());
        return true;
    }

    public boolean rollbackCommentEdit(String postId, String commentId) {
        if (!posts.containsKey(postId)) {
            return false;
        }
        if (!comments.containsKey(commentId)) {
            return false;
        }
        if (!commentContentHistory.containsKey(commentId)) {
            return false;
        }
        if (commentContentHistory.get(commentId).isEmpty()) {
            return false;
        }
        // rollback to the previous comment
        comments.get(commentId).setContent(commentContentHistory.get(commentId).pop());
        return true;
    }

    public static class Post {
        private String content;
        private long timestamp;
        private int likes;
        private List<String> comments;

        public Post(String content, long timestamp) {
            this.content = content;
            this.timestamp = timestamp;
            this.likes = 0;
            this.comments = new ArrayList<>();
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getLikes() {
            return likes;
        }

        public void incrementLikes() {
            this.likes++;
        }

        public List<String> getComments() {
            return comments;
        }

        public void addComment(String commentId) {
            this.comments.add(commentId);
        }
    }

    public static class Comment {
        private String content;
        private long timestamp;

        public Comment(String content, long timestamp) {
            this.content = content;
            this.timestamp = timestamp;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
    public static void main(String[] args) {
        SocialMediaPostComment smpc = new SocialMediaPostComment();
        smpc.createPost("post1", "Hello World!", System.currentTimeMillis());
        smpc.likePost("post1");
        smpc.commentOnPost("post1", "comment1", "Nice post!", System.currentTimeMillis());
        System.out.println(smpc.topNLikedPosts(1)); // Should print ["post1"]
        System.out.println(smpc.topNCommentedPosts(1)); // Should print ["post1"]
    }
}