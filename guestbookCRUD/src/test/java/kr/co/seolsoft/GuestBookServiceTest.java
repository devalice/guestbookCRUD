package kr.co.seolsoft;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.seolsoft.dto.GuestBookDTO;
import kr.co.seolsoft.dto.PageRequestDTO;
import kr.co.seolsoft.dto.PageResponseDTO;
import kr.co.seolsoft.entity.GuestBook;
import kr.co.seolsoft.service.GuestBookService;

@SpringBootTest
public class GuestBookServiceTest {
	
	@Autowired
    private GuestBookService service;

    //@Test
    public void registerTest(){
        GuestBookDTO dto = GuestBookDTO.builder()
                .title("제목")
                .content("내용")
                .writer("설희")
                .build();
        Long gno = service.register(dto);
        System.out.println(gno);
    }
    
    //@Test
    public void listTest(){
    		//1페이지 10개 목록
            PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                            							  .page(11)
                            							  .size(10)
                            							  .build();
            
            //List<DTO>
            PageResponseDTO<GuestBookDTO, GuestBook> pageResponseDTO = service.getList(pageRequestDTO);
            
            for(GuestBookDTO dto : pageResponseDTO.getDtoList()){
                System.out.println(dto);
            }
            
            //이전과 다음 링크 여부와 전체 페이지 개수 확인
            System.out.println("===================================");
            System.out.println("이전:" + pageResponseDTO.isPrev());
            System.out.println("다음:" + pageResponseDTO.isNext());
            System.out.println("전체:" + pageResponseDTO.getTotalPage());
            //페이지 번호 목록 출력
            System.out.println("===================================");
            for(Integer i : pageResponseDTO.getPageList()){
                System.out.print(i + "\t");
            }
    }
    
    @Test
    public void listTest2(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                        							  .page(1)
                        							  .size(10)
                        							  .type("t")
                        							  .keyword("연습")
                        							  .build();
        
        PageResponseDTO<GuestBookDTO, GuestBook> pageResponseDTO = service.getList(pageRequestDTO);
        
        for(GuestBookDTO dto : pageResponseDTO.getDtoList()){
            System.out.println(dto);
        }

        //이전 과 다음 링크 여부 와 전체 페이지 개수 확인
        System.out.println("===================================");
        System.out.println("이전:" + pageResponseDTO.isPrev());
        System.out.println("다음:" + pageResponseDTO.isNext());
        System.out.println("전체:" + pageResponseDTO.getTotalPage());
        //페이지 번호 목록 출력
        System.out.println("===================================");
        for(Integer i : pageResponseDTO.getPageList()){
            System.out.print(i + "\t");
        }
        System.out.println();
    }

}
