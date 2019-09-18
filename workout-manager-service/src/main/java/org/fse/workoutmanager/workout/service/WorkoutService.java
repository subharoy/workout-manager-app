package org.fse.workoutmanager.workout.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.fse.workoutmanager.workout.bean.Workout;
import org.fse.workoutmanager.workout.dataobject.WorkoutDao;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {

	private @Resource WorkoutDao workoutDao;

	/*public List<Workout> getAllCreatedBetween(final User user, long start, long end) {
		return workoutDao.findAllByUseridBetween(user.getId(), new Date(start), new Date(end),
				workoutDao.sortAsc("start"));
	}

	public List<Workout> getAllCreatedBy(final User user) {
		return workoutDao.findAllByUserid(user.getId());
	}*/
	
	public Workout get(final Integer id) {
		return workoutDao.findById(id).get();
	}
	
	public List<Workout> getAllWorkouts() {
		return workoutDao.findAll();
	}
	
	public List<Workout> getAllActiveWorkouts() {
		return workoutDao.findAllActiveWorkouts();
	}
	
	public List<Workout> getActivitiesCreatedBetween(Date start, Date end) {
		return workoutDao.findAllActivitiesBetween(start, end, JpaSort.by(Direction.ASC, "startdate"));
	}

	public Workout create(final Workout workout) {
		return workoutDao.saveAndFlush(workout);
	}
	
	public void delete(final Integer workoutId) {
		workoutDao.deleteById(workoutId);
	}
	
	public Workout update(final Workout workout) {
		if (workout == null) {
			throw new IllegalArgumentException("Input details cannot be empty");
		}
		if (workout.getId() == null || workout.getId() <= 0) {
			throw new IllegalArgumentException("Invalid " + workout.getClass().getSimpleName() + " Identifier");
		}
		return workoutDao.saveAndFlush(workout);
	}
}
