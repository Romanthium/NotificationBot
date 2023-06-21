package com.example.notificationbot.repository;

import com.example.notificationbot.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    static Specification<Task> topicContains(String keywordTopic) {
        return (task, cq, cb) -> cb.like(cb.lower(task.get("topic")), "%" + keywordTopic + "%");
    }

    static Specification<Task> textContains(String keywordText) {
        return (task, cq, cb) -> cb.like(cb.lower(task.get("text")), "%" + keywordText + "%");
    }
}
