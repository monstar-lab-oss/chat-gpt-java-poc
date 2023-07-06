package com.chat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Data
@Builder
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "user_weight")
@NoArgsConstructor
@AllArgsConstructor
public class WeightChangeLogItem extends AuditMetadata {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double weightInPounds;
  private boolean processed;

  @ManyToOne
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private User user;
}
