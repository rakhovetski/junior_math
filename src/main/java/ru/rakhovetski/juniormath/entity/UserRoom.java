package ru.rakhovetski.juniormath.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users_room")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoom implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userRoom")
    private List<Statistic> statistics = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
        this.user.getUserRooms().add(this);
    }

    public void setRoom(Room room) {
        this.room = room;
        this.room.getUserRooms().add(this);
    }
}
