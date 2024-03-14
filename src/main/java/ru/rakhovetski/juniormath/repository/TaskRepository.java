package ru.rakhovetski.juniormath.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.rakhovetski.juniormath.entity.Task;


public interface TaskRepository extends JpaRepository<Task, Integer> {

    Page<Task> findAllBy(Pageable pageable);

    Page<Task> findAllBySubjectId(Integer subjectId, Pageable pageable);

    Page<Task> findAllByClassNumber(Short classNumber, Pageable pageable);

    Page<Task> findAllBySubjectIdAndClassNumber(Integer subjectId, Short classNumber, Pageable pageable);

    Page<Task> findAllByTeacherId(Integer id, Pageable pageable);
}
