package org.codesignal.debugging.problems;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SocialMediaPostCommentTest {
    @Test
    void testPostCreationAndRollback() {
        SocialMediaPostComment socialMedia = new SocialMediaPostComment();

        assertTrue(socialMedia.createPost("post-1", "Original", 100L));
        assertEquals("Original", socialMedia.getPosts().get("post-1").getContent());
        assertFalse(socialMedia.createPost("post-1", "Duplicate", 200L));
        assertFalse(socialMedia.rollbackPostEdit("post-1"));
        assertFalse(socialMedia.rollbackPostEdit("missing"));

        assertTrue(socialMedia.editPost("post-1", "Edited"));
        assertEquals("Edited", socialMedia.getPosts().get("post-1").getContent());
        assertTrue(socialMedia.rollbackPostEdit("post-1"));
        assertEquals("Original", socialMedia.getPosts().get("post-1").getContent());
        assertFalse(socialMedia.rollbackPostEdit("post-1"));
    }

    @Test
    void testLikePostAndQueryTopLiked() {
        SocialMediaPostComment socialMedia = new SocialMediaPostComment();
        socialMedia.createPost("older", "Older", 100L);
        socialMedia.createPost("newer", "Newer", 200L);

        assertFalse(socialMedia.likePost("missing"));
        assertTrue(socialMedia.likePost("newer"));
        assertTrue(socialMedia.likePost("older"));
        assertTrue(socialMedia.likePost("older"));

        assertEquals(List.of("older", "newer"), socialMedia.topNLikedPosts(2));
        assertEquals(List.of("older"), socialMedia.topNLikedPosts(1));
        assertTrue(socialMedia.topNLikedPosts(0).isEmpty());
    }

    @Test
    void testCommentCreationEditAndRollback() {
        SocialMediaPostComment socialMedia = new SocialMediaPostComment();
        socialMedia.createPost("post-1", "Post", 100L);

        assertFalse(socialMedia.commentOnPost("missing", "comment-1", "Comment", 110L));
        assertTrue(socialMedia.commentOnPost("post-1", "comment-1", "Original comment", 110L));
        assertFalse(socialMedia.commentOnPost("post-1", "comment-1", "Duplicate ID", 120L));
        assertEquals(List.of("comment-1"), socialMedia.getPosts().get("post-1").getComments());
        assertEquals("Original comment", socialMedia.getComments().get("comment-1").getContent());

        assertFalse(socialMedia.editComment("missing", "comment-1", "Edited"));
        assertFalse(socialMedia.editComment("post-1", "missing", "Edited"));
        assertTrue(socialMedia.editComment("post-1", "comment-1", "Edited comment"));
        assertEquals("Edited comment", socialMedia.getComments().get("comment-1").getContent());

        assertFalse(socialMedia.rollbackCommentEdit("missing", "comment-1"));
        assertFalse(socialMedia.rollbackCommentEdit("post-1", "missing"));
        assertTrue(socialMedia.rollbackCommentEdit("post-1", "comment-1"));
        assertEquals("Original comment", socialMedia.getComments().get("comment-1").getContent());
        assertFalse(socialMedia.rollbackCommentEdit("post-1", "comment-1"));
    }

    @Test
    void testCommentAndQueryTopCommented() {
        SocialMediaPostComment socialMedia = new SocialMediaPostComment();
        socialMedia.createPost("older", "Older", 100L);
        socialMedia.createPost("newer", "Newer", 200L);

        assertTrue(socialMedia.commentOnPost("newer", "comment-1", "One", 210L));
        assertTrue(socialMedia.commentOnPost("older", "comment-2", "Two", 220L));
        assertTrue(socialMedia.commentOnPost("older", "comment-3", "Three", 230L));

        assertEquals(List.of("older", "newer"), socialMedia.topNCommentedPosts(2));
        assertEquals(List.of("older"), socialMedia.topNCommentedPosts(1));
        assertTrue(socialMedia.topNCommentedPosts(0).isEmpty());
    }

    @Test
    void testEditAndRollbackMultipleTimes() {
        SocialMediaPostComment socialMedia = new SocialMediaPostComment();
        socialMedia.createPost("post-1", "Initial", 100L);

        assertTrue(socialMedia.editPost("post-1", "First edit"));
        assertTrue(socialMedia.editPost("post-1", "Second edit"));
        assertTrue(socialMedia.rollbackPostEdit("post-1"));
        assertEquals("First edit", socialMedia.getPosts().get("post-1").getContent());
        assertTrue(socialMedia.rollbackPostEdit("post-1"));
        assertEquals("Initial", socialMedia.getPosts().get("post-1").getContent());
        assertFalse(socialMedia.rollbackPostEdit("post-1"));
    }

    @Test
    void testDoubleEditAndDoubleRollbackComment() {
        SocialMediaPostComment socialMedia = new SocialMediaPostComment();
        socialMedia.createPost("post-1", "Post", 100L);
        socialMedia.commentOnPost("post-1", "comment-1", "Initial", 110L);

        assertTrue(socialMedia.editComment("post-1", "comment-1", "First edit"));
        assertTrue(socialMedia.editComment("post-1", "comment-1", "Second edit"));
        assertTrue(socialMedia.rollbackCommentEdit("post-1", "comment-1"));
        assertEquals("First edit", socialMedia.getComments().get("comment-1").getContent());
        assertTrue(socialMedia.rollbackCommentEdit("post-1", "comment-1"));
        assertEquals("Initial", socialMedia.getComments().get("comment-1").getContent());
        assertFalse(socialMedia.rollbackCommentEdit("post-1", "comment-1"));
    }

    @Test
    void testDoubleEditAndDoubleRollbackPost() {
        SocialMediaPostComment socialMedia = new SocialMediaPostComment();
        socialMedia.createPost("post-1", "Initial", 100L);

        assertTrue(socialMedia.editPost("post-1", "First edit"));
        assertTrue(socialMedia.editPost("post-1", "Second edit"));
        assertTrue(socialMedia.rollbackPostEdit("post-1"));
        assertEquals("First edit", socialMedia.getPosts().get("post-1").getContent());
        assertTrue(socialMedia.rollbackPostEdit("post-1"));
        assertEquals("Initial", socialMedia.getPosts().get("post-1").getContent());
        assertFalse(socialMedia.rollbackPostEdit("post-1"));
    }

    @Test
    void testDeletePostBehavior() {
        SocialMediaPostComment socialMedia = new SocialMediaPostComment();
        socialMedia.createPost("post-1", "Initial", 100L);

        assertFalse(socialMedia.deletePost("missing"));
        assertFalse(socialMedia.deletePost("post-1"));
        socialMedia.editPost("post-1", "Edited");
        assertTrue(socialMedia.deletePost("post-1"));
        assertFalse(socialMedia.getPosts().containsKey("post-1"));
        assertFalse(socialMedia.rollbackPostEdit("post-1"));
    }
}
