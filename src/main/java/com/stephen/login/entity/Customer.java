package com.stephen.login.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
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
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	private Boolean active;
	
	private String firstName;

	private String lastName;

	@Column(unique = true)
	@ValidEmail
	@NotBlank
	private String email;

	@Column(name = "create_date", nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp createDate;

	@Column(name = "last_update")
	@UpdateTimestamp
	private Timestamp lastUpdate;

	@JsonIgnore
	private String password;

	private Boolean mfaEnabled;
	
	private String totp;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    private Address address;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", referencedColumnName = "storeId")
    private Store store;
	
	@Transient
	private String accessToken;

}
