package ru.rakhovetski.juniormath.integrational.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ru.rakhovetski.juniormath.entity.Subject;
import ru.rakhovetski.juniormath.entity.Task;
import ru.rakhovetski.juniormath.integrational.IntegrationBaseTest;
import ru.rakhovetski.juniormath.repository.SubjectRepository;
import ru.rakhovetski.juniormath.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Sql({
        "/db/data.sql"
})
public class TaskRepositoryTest extends IntegrationBaseTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void testFindWithAllFiltersAllPagination() {
        List<Integer> subjectIds = subjectRepository.findAll().stream().map(Subject::getId).toList();
        List<Short> classNumbers = IntStream.range(1, 11).boxed().map(Integer::shortValue).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(0, 10);

        Page<Task> result = taskRepository.findAllByFilters(subjectIds, classNumbers, pageable);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    public void testFindWithAllFiltersOneItemPagination() {
        List<Integer> subjectIds = subjectRepository.findAll().stream().map(Subject::getId).toList();
        List<Short> classNumbers = IntStream.range(1, 11).boxed().map(Integer::shortValue).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(0, 1);

        Page<Task> result = taskRepository.findAllByFilters(subjectIds, classNumbers, pageable);
        assertEquals(3, result.getTotalElements());
        assertEquals(3, result.getTotalPages());
    }

    @Test
    public void testFindWithOneSubjectId() {
        List<Integer> subjectIds = List.of(1);
        List<Short> classNumbers = IntStream.range(1, 11).boxed().map(Integer::shortValue).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(0, 10);

        Page<Task> result = taskRepository.findAllByFilters(subjectIds, classNumbers, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    public void testFindWithOneClassNumberId() {
        List<Integer> subjectIds = subjectRepository.findAll().stream().map(Subject::getId).toList();
        List<Short> classNumbers = List.of((short)10);
        Pageable pageable = PageRequest.of(0, 10);

        Page<Task> result = taskRepository.findAllByFilters(subjectIds, classNumbers, pageable);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    public void testFindWithOneClassNumberIdAndOneSubjectId() {
        List<Integer> subjectIds = List.of(1);
        List<Short> classNumbers = List.of((short)9);
        Pageable pageable = PageRequest.of(0, 10);

        Page<Task> result = taskRepository.findAllByFilters(subjectIds, classNumbers, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    public void testFindWithIncorrectSubjectIds() {
        List<Integer> subjectIds = List.of(100);
        List<Short> classNumbers = List.of((short)10);
        Pageable pageable = PageRequest.of(0, 10);

        Page<Task> result = taskRepository.findAllByFilters(subjectIds, classNumbers, pageable);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    public void testFindWithIncorrectClassNumbers() {
        List<Integer> subjectIds = List.of(1);
        List<Short> classNumbers = List.of((short)12);
        Pageable pageable = PageRequest.of(0, 10);

        Page<Task> result = taskRepository.findAllByFilters(subjectIds, classNumbers, pageable);
        assertEquals(0, result.getTotalElements());
    }
}
