package ru.rakhovetski.juniormath.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "student")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student implements BaseEntity<Integer>{

    @Id
    private Integer id;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "level")
    private Integer level;

    @Column(name = "completed_tasks")
    private Integer completedTasks;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Character character;
}
