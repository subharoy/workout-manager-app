package org.fse.workoutmanager.workout.service;

import java.util.List;

import javax.annotation.Resource;

import org.fse.workoutmanager.workout.bean.Category;
import org.fse.workoutmanager.workout.dataobject.CategoryDao;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

	private @Resource CategoryDao categoryDao;
	
	public Category get(final Integer id) {
		return categoryDao.findById(id).get();
	}

	public List<Category> getAllCategories() {
		return categoryDao.findAll();
	}

	public Category create(final Category category) {
		return categoryDao.saveAndFlush(category);
	}

	public Category update(final Category category) {
		if (category == null) {
			throw new IllegalArgumentException("Input details cannot be empty");
		}
		if (category.getId() == null || category.getId() <= 0) {
			throw new IllegalArgumentException("Invalid " + category.getClass().getSimpleName() + " Identifier");
		}
		return categoryDao.saveAndFlush(category);
	}
	
	public void delete(final Integer id) {
		categoryDao.deleteById(id);
	}
}
