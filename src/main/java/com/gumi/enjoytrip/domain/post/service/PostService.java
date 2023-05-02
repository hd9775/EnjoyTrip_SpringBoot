package com.gumi.enjoytrip.domain.post.service;

import com.gumi.enjoytrip.domain.post.dto.PostCreateDto;
import com.gumi.enjoytrip.domain.post.dto.PostDto;
import com.gumi.enjoytrip.domain.post.dto.PostListDto;
import com.gumi.enjoytrip.domain.post.dto.PostUpdateDto;
import com.gumi.enjoytrip.domain.post.entity.Post;
import com.gumi.enjoytrip.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    public List<Post> listPost() {
        return postRepository.listPost();
    }

    public void createPost(PostCreateDto post) {
        postRepository.createPost();
    }

    public Post detailPost(long id) {
        return postRepository.detailPost(id);
    }

    public boolean getLikeStatus(long postId, long userId) {
        return postRepository.getLikeStatud(postId, userId);
    }

    public int getLikeCount(long id) {
        return postRepository.getLikeCount(id);
    }

    public void increaseViewCount(long id) {
        postRepository.increaseViewCount(id);
    }

    public Post getPost(long id) {
        return postRepository.getPost(id);
    }

    public void updatePost(long postId, PostUpdateDto postUpdateDto) {
        postRepository.updatePost(postId, postUpdateDto);
    }

    public void deletePost(long postId) {
        postRepository.deletePost(postId);

    }

    public void likePost(long id, long userId) {
        postRepository.likePost(id, userId);
    }

    public void unlikePost(long id, long userId) {
        postRepository.unlikePost(id, userId);
    }

    public void setNotice(long id, boolean isNotice) {
        postRepository.setNotice(id, isNotice);
    }
}
