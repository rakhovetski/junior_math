package ru.rakhovetski.juniormath.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rakhovetski.juniormath.entity.Task;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT task.* FROM task " +
            "JOIN subject ON task.subject_id = subject.id " +
            "WHERE subject.id IN (:subjectIds) " +
            "AND task.class IN (:classNumbers);",
            countQuery = "SELECT count(task.*) FROM task " +
                    "JOIN subject ON task.subject_id = subject.id " +
                    "WHERE subject.id IN (:subjectIds) " +
                    "AND task.class IN (:classNumbers);",
            nativeQuery = true)
    Page<Task> findAllByFilters(List<Integer> subjectIds, List<Short> classNumbers, Pageable pageable);

    Page<Task> findAllByTeacherId(Integer id, Pageable pageable);
}
