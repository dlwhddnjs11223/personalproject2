package org.sparta.personalproject.Repository;

import org.sparta.personalproject.controller.Pw;
import org.sparta.personalproject.controller.entity.Schedule;
import org.sparta.personalproject.dto.RequestDto;
import org.sparta.personalproject.dto.ResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Repository {

    private final JdbcTemplate jdbcTemplate;

    public Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ResponseDto addSchedule(Schedule schedule) {

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

    public ResponseDto updateSchedule( long id,  RequestDto requestDto) {
        // 해당 schedule 가져오기


        String sql = "UPDATE schedule SET title = ?, content = ?, person = ?, date = ?, pw = ? where id = ?";
        jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getContent(), requestDto.getPerson(), requestDto.getDate(), requestDto.getPw(), id);
        return new ResponseDto(new Schedule(requestDto));


    }


    public ResponseDto deleteSchedule(long id) {
        // 해당 schedule 가져오기
        String sql = "DELETE from schedule where id = ?";
        jdbcTemplate.update(sql, id);
        return new ResponseDto(findSchedule(id));

    }

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
