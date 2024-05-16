package org.sparta.personalproject.controller;

import org.sparta.personalproject.controller.entity.Schedule;
import org.sparta.personalproject.dto.RequestDto;
import org.sparta.personalproject.dto.ResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


@RestController
@RequestMapping("/api")
public class Controller {

    private final JdbcTemplate jdbcTemplate;


    public Controller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    1단계
     */
    @PostMapping("/schedules")
    public ResponseDto createSchedule(@RequestBody RequestDto requestDto) {

        // requestDto를 entity로 변환
        Schedule schedule = new Schedule(requestDto);
//        schedule.setId(idNum);
//        // entity를 데이터베이스에 저장
//        scheduleList.put(idNum, schedule);
//        idNum++;
//        // entity를 responseDto로 변환
//        return new ResponseDto(schedule);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO schedule (title, content, person, date, pw) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);


            preparedStatement.setString(1, schedule.getTitle());
            preparedStatement.setString(2, schedule.getContent());
            preparedStatement.setString(3, schedule.getPerson());
            preparedStatement.setLong(4, schedule.getDate());
            preparedStatement.setLong(5, schedule.getPw());
            return preparedStatement;
        }, keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        // Entity -> ResponseDto
        ResponseDto responseDto = new ResponseDto(schedule);

        return responseDto;
    }

    /*
    2단계
     */
    @GetMapping("/schedules/{id}")
    public ResponseDto getSchedule(@PathVariable long id) {
        Schedule schedule = findSchedule(id);
        ResponseDto responseDto = new ResponseDto(schedule);
        return responseDto;
    }

    /*
    3단계X
     */
    @GetMapping("/schedules")
    public List<ResponseDto> getSchedulelist() {

        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ResponseDto>() {
            @Override
            public ResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String person = rs.getString("person");
                Long date = rs.getLong("date");
                Long pw = rs.getLong("pw");
                return new ResponseDto(id, title, content, person, date);
            }
        });

    }

    //
//    /*
//    4단계
//     */
    @PutMapping("/schedules/{id}")
    public ResponseDto updateSchedule(@PathVariable long id, @RequestBody RequestDto requestDto) {
        // 해당 schedule 가져오기
        Schedule schedule = findSchedule(id);
        if (schedule != null && schedule.getPw() == requestDto.getPw()) {
            String sql = "UPDATE schedule SET title = ?, content = ?, person = ?, date = ?, pw = ? where id = ?";
            jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getContent(), requestDto.getPerson(), requestDto.getDate(), requestDto.getPw(), id);
            return new ResponseDto(schedule);

        } else {

            throw new IllegalArgumentException("해당 ID가 존재하지 않습니다.");

        }
    }
//
//    /*
//    5단계
//     */
    @DeleteMapping("/schedules/{id}")
    public ResponseDto deleteSchedule(@PathVariable long id, @RequestBody Pw pw) {
        // 해당 schedule 가져오기
        Schedule schedule = findSchedule(id);
        if (schedule != null && schedule.getPw()==pw.getPw()) {
            String sql = "DELETE from schedule where id = ?";
            jdbcTemplate.update(sql, id);
            return new ResponseDto(schedule);

        } else {

            throw new IllegalArgumentException("해당 ID가 존재하지 않거나 비밀번호가 일치하지 않습니다.");

        }
    }

//    /*
//    ID값으로 entity 찾아오는 메서드
//     */

    public Schedule findSchedule(long id) {

        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getLong("id"));
                schedule.setTitle(resultSet.getString("title"));
                schedule.setContent(resultSet.getString("content"));
                schedule.setPerson(resultSet.getString("person"));
                schedule.setDate(resultSet.getLong("date"));
                schedule.setPw(resultSet.getLong("pw"));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }
}
