package com.AS.Student_Attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.AS.Student_Attendance.entity.Attendance;
import java.util.List;
import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
	List<Attendance> findByUser(com.AS.Student_Attendance.entity.User user);

	List<Attendance> findByUserAndCourseCourseId(com.AS.Student_Attendance.entity.User user, Long courseId);

	boolean existsByUserUserIdAndCourseCourseIdAndAttendanceDate(Long userId, Long courseId, java.time.LocalDate date);

    List<Attendance> findByUser_UserId(Integer userId);

    List<Attendance> findByAttendanceDateBetween(LocalDate startDate, LocalDate endDate);
}
