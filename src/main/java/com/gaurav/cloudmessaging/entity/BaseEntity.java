package com.gaurav.cloudmessaging.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	public long id;
	
	@Column(name = "CREATED_AT")
	public Date createdAt = new Date();

}
