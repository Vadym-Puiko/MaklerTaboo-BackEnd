package com.softserve.maklertaboo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@PrepareForTest(FlatCommentService.class)
class FlatCommentServiceTest {

    @Test
    void saveFlatComment() {
    }

    @Test
    void saveCommentAboutComment() {
    }

    @Test
    void deleteFlatComment() {
    }

    @Test
    void getAllFlatCommentsForFlat() {
    }

    @Test
    void getAllFlatCommentsByLikes() {
    }

    @Test
    void getAllCommentsForComment() {
    }

    @Test
    void getFlatCommentById() {
    }

    @Test
    void addLike() {
    }

    @Test
    void minusLike() {
    }
}