package com.e_com.e_com_spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class EComSpringApplicationTests {

	@Test
	void contextLoads() {
	}

}
