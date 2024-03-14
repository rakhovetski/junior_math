package ru.rakhovetski.juniormath.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "teacher")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher implements BaseEntity<Integer> {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private User user;
}
