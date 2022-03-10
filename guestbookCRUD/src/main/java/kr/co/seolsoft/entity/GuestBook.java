package kr.co.seolsoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuestBook extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;
    
    @Column(length=100, nullable=false)
    private String title;

    @Column(length=1000, nullable=false)
    private String content;

    @Column(length=100, nullable=false)
    private String writer;
    
    //title 수정 메서드 - setter
    public void changeTitle(String title){
        this.title = title;
    }
    
    //content 수정 메서드
    public void changeContent(String content){
        this.content = content;
    }
    
}
