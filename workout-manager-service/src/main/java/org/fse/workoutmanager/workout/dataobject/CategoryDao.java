package org.fse.workoutmanager.workout.dataobject;

import java.util.List;

import org.fse.workoutmanager.workout.bean.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {

	List<Category> findAll();

}
