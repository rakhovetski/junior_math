package ru.rakhovetski.juniormath.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "subject")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "subject")
    private List<Task> task = new ArrayList<>();
}
