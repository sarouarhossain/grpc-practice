package com.demo.grpcservice.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Data
public class Blog {
  @Id private String id;
  private String authorId;
  private String title;
  private String content;
  @CreationTimestamp private ZonedDateTime createdAt;
}
