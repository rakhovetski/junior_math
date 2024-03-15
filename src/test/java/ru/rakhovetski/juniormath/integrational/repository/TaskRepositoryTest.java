package ru.rakhovetski.juniormath.integrational.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ru.rakhovetski.juniormath.entity.Task;
import ru.rakhovetski.juniormath.integrational.IntegrationBaseTest;
import ru.rakhovetski.juniormath.repository.TaskRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Sql({
        "/db/data.sql"
})
@RequiredArgsConstructor
public class TaskRepositoryTest extends IntegrationBaseTest {

    @Autowired
    private TaskRepository taskRepository;
    private static final Integer PAGE_SIZE = 10;
    private static final Integer PAGE_NUMBER = 0;

    @Test
    public void testFindFindAllBySubjectNameIdAndClassNumberCorrect() {
        Long totalPageCount = 1L;
        Integer subject_id = 1;
        Short class_number = 9;
        Short classNumber = 9;
        String topicAl = "topicAl";

        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        Page<Task> result = taskRepository.findAllByFilters(List.of(subject_id), List.of(class_number), pageable);

        assertEquals(totalPageCount, result.getTotalElements());

        Task firstResult = result.stream().toList().getFirst();

        assertEquals(classNumber, firstResult.getClassNumber());
        assertEquals(topicAl, firstResult.getTopic());
    }

    @Test
    public void testFindFindAllBySubjectNameIdAndClassNumberCorrectTwoElements() {
        Long totalPageCount = 2L;
        Integer subjectId = 2;
        Short classNumber = 10;
        String topicIT1 = "topicIT1";
        String topicIT2 = "topicIT2";

        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        Page<Task> result = taskRepository.findAllByFilters(List.of(subjectId), List.of(classNumber), pageable);

        assertEquals(totalPageCount, result.getTotalElements());

        Task firstTask = result.stream().toList().getFirst();

        assertEquals(classNumber, firstTask.getClassNumber());
        assertEquals(topicIT1, firstTask.getTopic());

        Task lastTask = result.stream().toList().getLast();

        assertEquals(classNumber, lastTask.getClassNumber());
        assertEquals(topicIT2, lastTask.getTopic());
    }

    @Test
    public void testFindFindAllBySubjectNameIdAndClassNumberIncorrectClassNumber() {
        Integer subject_id = 1;
        Short incorrectClassNumber = 12;
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        Page<Task> result = taskRepository.findAllByFilters(List.of(subject_id), List.of(incorrectClassNumber), pageable);

        assertEquals(0, result.getTotalElements());
    }

}
