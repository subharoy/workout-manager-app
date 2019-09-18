package org.fse.workoutmanager.workout.dataobject;

import java.util.Date;
import java.util.List;

import org.fse.workoutmanager.workout.bean.Workout;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutDao extends JpaRepository<Workout, Integer> {

	@Query("select workout from #{#entityName} workout where workout.status = 0")
	List<Workout> findAllActiveWorkouts();

	@Query("select workout from #{#entityName} workout where workout.startdate >= ?1 and workout.enddate <= ?2")
	List<Workout> findAllActivitiesBetween(final Date start, final Date end, Sort sortingCriteria);
}
