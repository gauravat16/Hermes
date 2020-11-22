package com.hermes.cloudmessaging.database.entity.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
public class BaseEntity {

    @Id
    public String id;

    public Date createdAt = new Date();

}
