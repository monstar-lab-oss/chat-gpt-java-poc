package com.chat.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Table(name = "users")
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class User extends AuditMetadata {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private Double weightInPounds;
	private Integer activeCaloriesGoal;
}
