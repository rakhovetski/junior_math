package ru.rakhovetski.juniormath.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "task")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "class")
    private Short classNumber;

    @Column(name = "topic")
    private String topic;

    @Column(name = "condition")
    private String condition;

    @Column(name = "answer")
    private String answer;

    @Column(name = "solve")
    private String solve;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @Builder.Default
    @OneToMany(mappedBy = "task")
    private List<TaskTest> taskTests = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;
}
