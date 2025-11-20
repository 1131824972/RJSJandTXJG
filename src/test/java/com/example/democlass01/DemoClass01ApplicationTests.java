package com.example.democlass01;

import com.example.democlass01.repository.StudentDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoClass01ApplicationTests {
    @Autowired
    private StudentDao studentDao;

    @Test
    void testLogicDelete() {
        studentDao.deleteById(1L);
    }

}
