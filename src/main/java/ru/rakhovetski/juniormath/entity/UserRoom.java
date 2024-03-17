package ru.rakhovetski.juniormath.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users_room")
@ToString(exclude = {"user", "room"})
@EqualsAndHashCode(exclude = {"user", "room"})
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

    public void setUser(User user) {
        this.user = user;
        this.user.getUserRooms().add(this);
    }

    public void setRoom(Room room) {
        this.room = room;
        this.room.getUserRooms().add(this);
    }
}
