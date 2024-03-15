package ru.rakhovetski.juniormath.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "room")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "class")
    private Short classNumber;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "room")
    private List<UserRoom> userRooms = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "room")
    private List<Test> tests = new ArrayList<>();
}
