package com.example.load_balancer.repository;

import com.example.load_balancer.entity.Task;
import com.example.load_balancer.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

   List<Task> findByStatus(TaskStatus status);
   Page<Task> findByStatus(TaskStatus status, Pageable pageable);
   List<Task> findByTaskType(String taskType);
   List<Task> findByAssignedWorker(String assignedWorker);
   List<Task> findAllByOrderByCreatedAtDesc();
   List<Task> findByStatusOrderByCreatedAtAsc(TaskStatus status);
   List<Task> findByCreatedAtAfter(LocalDateTime createdAt);

   @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedWorker = :workerName AND t.status = 'IN_PROGRESS'")
    Long countActiveTasksByWorker(@Param("workerName") String workerName);

   @Query("SELECT DISTINCT t.assignedWorker FROM Task t WHERE t.status = 'IN_PROGRESS' AND t.assignedWorker IS NOT NULL")
   List<String> findActiveWorkers();

   Long countByStatus(TaskStatus status);

   @Query("SELECT COUNT(t) FROM Task t WHERE t.status = 'COMPLETED' AND t.completedAt BETWEEN :start AND :end")
   Long countCompletedTasksBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

   @Query("SELECT t.taskType, COUNT(t) FROM Task t GROUP BY t.taskType ORDER BY COUNT(t) DESC")
   List<Object[]> findTaskTypeStatistics();

   void deleteByStatusAndCompletedAtBefore(TaskStatus status, LocalDateTime completedAt);
}
