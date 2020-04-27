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
@PrepareForTest(ComplaintService.class)
class ComplaintServiceTest {

    @Test
    void checkComplaintFlatComment() {
    }

    @Test
    void checkComplaintUserComment() {
    }

    @Test
    void saveComplaint() {
    }

    @Test
    void getAllComplaint() {
    }
}