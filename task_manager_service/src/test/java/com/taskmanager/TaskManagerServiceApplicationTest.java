package com.taskmanager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskManagerServiceApplicationTest {

	private static TaskManagerServiceApplication jerseySpringBoot;
	private static String[] args;
	private static String[] argsTwo;

	@BeforeClass
	public static void init() throws Exception {
		jerseySpringBoot = new TaskManagerServiceApplication();
		argsTwo = new String[0];
		args = new String[1];
		args[0] = "exitcode";
	}

	@AfterClass
	public static void destroy() {
		jerseySpringBoot = null;
		args = null;
		argsTwo = null;
	}

	@Test
	public void testMain() {
		try {
			TaskManagerServiceApplication.main(argsTwo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

