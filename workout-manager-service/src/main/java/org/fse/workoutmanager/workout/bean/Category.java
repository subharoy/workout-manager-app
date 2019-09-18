package org.fse.workoutmanager.workout.bean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(schema = "fse", name = "category")
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties({ "workouts" })
@JsonPropertyOrder({ "id", "category", "workouts" })
public class Category implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = 936378602471695215L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "idGenrator")
	@GenericGenerator(name = "idGenrator", strategy = "native")
	private Integer id;
	
	@Column
	@Size(max = 50)
	private String category;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category", orphanRemoval = true, cascade = { CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.REMOVE })
	private Set<Workout> workouts;

	public Integer getId() {
		return id;
	}
	
	public String getCategory() {
		return category;
	}

	public Set<Workout> getWorkouts() {
		return workouts;
	}

	@JsonIgnore
	public Category putCategory(String category) {
		setCategory(category);
		return this;
	}

	@JsonIgnore
	public Category putId(Integer id) {
		setId(id);
		return this;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setWorkouts(Set<Workout> workouts) {
		this.workouts = workouts;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName()).append("{");
		builder.append("id : ").append(id);
		builder.append(", category : ").append(category);
		return builder.append("}").toString();
	}

}
