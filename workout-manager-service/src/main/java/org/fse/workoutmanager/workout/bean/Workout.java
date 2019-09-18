package org.fse.workoutmanager.workout.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(schema = "fse", name = "workout")
@SecondaryTable(schema = "fse", name = "workoutactivity")
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties({ "categoryid" })
@JsonPropertyOrder({ "id", "title", "notes", "burnrate", "startdate", "starttime", "enddate",
	 "endtime", "comment", "status", "category" })
public class Workout implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -7032639587972495494L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "idGenrator")
	@GenericGenerator(name = "idGenrator", strategy = "native")
	private Integer id;
	
	@Column
	@NotEmpty
	private String title;
	
	@Column
	private String notes;
	
	@Column
	@DecimalMin(value = "0.0", inclusive = true)
	private Double burnrate;

	@Column(table="workoutactivity")
	@Basic
	@Temporal(TemporalType.DATE)
	private Date startdate;
	
	@Column(table="workoutactivity")
	@Basic
	@Temporal(TemporalType.TIME)
	private Date starttime;
	
	@Column(table="workoutactivity")
	@Basic
	@Temporal(TemporalType.DATE)
	private Date enddate;
	
	@Column(table="workoutactivity")
	@Basic
	@Temporal(TemporalType.TIME)
	private Date endtime;
	
	@Column(table="workoutactivity")
	@Size(max = 50)
	private String comment;
	
	@Column(table="workoutactivity")
	private Boolean status;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "categoryid", insertable = false, updatable = false)
	private Category category;

	@Column
	@NotNull
	private Integer categoryid;

	
	public Integer getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public Double getBurnrate() {
		return burnrate;
	}
	
	public Date getStartdate() {
		return startdate;
	}
	
	public Date getStarttime() {
		return starttime;
	}
	
	public Date getEnddate() {
		return enddate;
	}
	
	public Date getEndtime() {
		return endtime;
	}
	
	public String getComment() {
		return comment;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public Category getCategory() {
		return category;
	}

	public Integer getCategoryid() {
		return categoryid;
	}

	@JsonIgnore
	public Workout putCategoryid(Integer categoryid) {
		setCategoryid(categoryid);
		return this;
	}
	
	@JsonIgnore
	public Workout putTitle(String title) {
		setTitle(title);
		return this;
	}

	@JsonIgnore
	public Workout putNotes(String notes) {
		setNotes(notes);
		return this;
	}

	@JsonIgnore
	public Workout putBurnrate(Double burnrate) {
		setBurnrate(burnrate);
		return this;
	}
	
	@JsonIgnore
	public Workout putStartdate(Date startdate) {
		setStartdate(startdate);
		return this;
	}
	
	@JsonIgnore
	public Workout putStarttime(Date starttime) {
		setStarttime(starttime);
		return this;
	}
	
	@JsonIgnore
	public Workout putEnddate(Date enddate) {
		setEnddate(enddate);
		return this;
	}
	
	@JsonIgnore
	public Workout putEndtime(Date endtime) {
		setEndtime(endtime);
		return this;
	}
	
	@JsonIgnore
	public Workout putComment(String comment) {
		setComment(comment);
		return this;
	}
	
	@JsonIgnore
	public Workout putStatus(Boolean status) {
		setStatus(status);
		return this;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setBurnrate(Double burnrate) {
		this.burnrate = burnrate;
	}
	
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName()).append("{");
		builder.append("id : ").append(id);
		builder.append(", title : ").append(title);
		builder.append(", notes : ").append(notes);
		builder.append(", burnrate : ").append(burnrate);
		builder.append(", startdate : ").append(startdate);
		builder.append(", starttime : ").append(starttime);
		builder.append(", enddate : ").append(enddate);
		builder.append(", endtime : ").append(endtime);
		builder.append(", comment : ").append(comment);
		builder.append(", status : ").append(status);
		
		builder.append(", categoryid : ").append(categoryid);
		if (category != null) {
			builder.append(", category : ").append(category);
		}
		return builder.append("}").toString();
	}

}
