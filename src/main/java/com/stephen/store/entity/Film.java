package com.stephen.store.entity;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "film")
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long filmId;

	private String title;

	private String description;

	private Integer releaseYear;

	private Integer rentalDuration;

	private Double rentalRate;

	private Integer length;

	private Double replacementCost;

	private String rating;

	private String specialFeatures;

	@Column(name = "last_update")
	@UpdateTimestamp
	private Timestamp lastUpdate;

	public Set<String> getSpecialFeatures() {
		if (specialFeatures == null)
			return Collections.emptySet();
		else
			return Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(specialFeatures.split(","))));
	}

	public void setSpecialFeatures(Set<String> specialFeature) {
		if (specialFeature == null)
			specialFeatures = null;
		else
			specialFeatures = String.join(",", specialFeature);
	}

}
