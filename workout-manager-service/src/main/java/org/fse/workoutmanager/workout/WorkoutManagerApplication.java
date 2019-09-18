package org.fse.workoutmanager.workout;

import java.io.IOException;

import org.fse.workoutmanager.workout.bean.Category;
import org.fse.workoutmanager.workout.bean.Workout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableAutoConfiguration
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = { "org.fse.workoutmanager.workout.*" })
public class WorkoutManagerApplication {

	//private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutManagerApplication.class);

	public static void main(String[] args) throws IOException {
		SpringApplication.run(WorkoutManagerApplication.class, args);
	}

	@Bean("category")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Category category() {
		return new Category();
	}

	@Bean("workout")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Workout workout() {
		return new Workout();
	}

}
