package org.fse.workoutmanager.workout.service;

import org.fse.workoutmanager.workout.bean.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractService<Entity extends BaseEntity, Identifier extends Number> {

	public Entity create(final Entity entity) {
		return save(entity);
	}

	protected abstract JpaRepository<Entity, Identifier> dao();

	public void delete(final Identifier workoutId) {
		dao().deleteById(workoutId);
	}

	public Entity get(final Identifier id) {
		return dao().findById(id).get();
	}

	private Entity save(final Entity entity) {
		return dao().saveAndFlush(entity);
	}

	public Entity update(final Entity entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Input details cannot be empty");
		}
		if (entity.getId() == null || entity.getId() <= 0) {
			throw new IllegalArgumentException("Invalid " + entity.getClass().getSimpleName() + " Identifier");
		}
		return save(entity);
	}

}
