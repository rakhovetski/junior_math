package ru.rakhovetski.juniormath.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "task_test")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskTest implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskTest")
    private List<Statistic> statistics = new ArrayList<>();

    public void setTask(Task task) {
        this.task = task;
        this.task.getTaskTests().add(this);
    }

    public void setTest(Test test) {
        this.test = test;
        this.test.getTaskTests().add(this);
    }
}
