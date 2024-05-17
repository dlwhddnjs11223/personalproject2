package org.sparta.personalproject.Repository;

import org.sparta.personalproject.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Schedule, Long> {

}
