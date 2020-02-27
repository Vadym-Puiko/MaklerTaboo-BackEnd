package com.softserve.maklertaboo;

import com.softserve.maklertaboo.repository.FlatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	FlatRepository flatRepository;

    @Test
    void loadTestData() {
    }
}
