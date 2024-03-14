package ru.rakhovetski.juniormath.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "statistic")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistic implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_room_id")
    private UserRoom userRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_test_id")
    private TaskTest taskTest;

    public void setUserRoom(UserRoom userRoom) {
        this.userRoom = userRoom;
        this.userRoom.getStatistics().add(this);
    }

    public void setTaskTest(TaskTest taskTest) {
        this.taskTest = taskTest;
        this.taskTest.getStatistics().add(this);
    }
}
