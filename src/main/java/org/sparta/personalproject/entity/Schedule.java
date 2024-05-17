package org.sparta.personalproject.entity;

import org.sparta.personalproject.dto.RequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "schedule") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false, length = 500)
    private String content;
    @Column(name = "person", nullable = false, length = 500)
    private String person;
    @Column(name = "date", nullable = false)
    private long date;
    @Column(name = "pw", nullable = false)
    private long pw;


    public Schedule(RequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.person = requestDto.getPerson();
        this.pw = requestDto.getPw();
        this.date = requestDto.getDate();

    }

        public void update(RequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.person = requestDto.getPerson();
        this.date = requestDto.getDate();
    }
}
