package org.sparta.personalproject.Repository;

import org.sparta.personalproject.dto.ResponseDto;
import org.sparta.personalproject.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface scheduleRepository extends JpaRepository<Schedule, Long> {

    List<ResponseDto> findAllByContentContains(String content);

}
