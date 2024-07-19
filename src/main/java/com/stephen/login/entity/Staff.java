package com.stephen.login.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stephen.login.validators.ValidEmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staff")
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long staffId;

	private String firstName;

	private String lastName;
	
	@Lob
	@JsonIgnore
    private byte[] picture;

	@Column(unique = true)
	@ValidEmail
	@NotBlank
	private String email;

	private Boolean active;
	
	@Column(name = "last_update")
	@UpdateTimestamp
	private Timestamp lastUpdate;

	@JsonIgnore
	private String password;

	private Boolean mfaEnabled;
	
	private String totp;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", referencedColumnName = "storeId")
    private Store store;
	
	@Transient
	private String accessToken;

}
