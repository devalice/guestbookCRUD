package kr.co.seolsoft.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestBookDTO {
    private Long gno;
    private String title;
    private String content;
    private String writer;
    
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    
}
