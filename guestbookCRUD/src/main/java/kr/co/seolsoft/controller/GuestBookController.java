package kr.co.seolsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.seolsoft.dto.GuestBookDTO;
import kr.co.seolsoft.dto.PageRequestDTO;
import kr.co.seolsoft.dto.PageResponseDTO;
import kr.co.seolsoft.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

//로그 출력을 위한 어노테이션
@Log4j2
//PageController를 만들기 위한 어노테이션
@Controller
@RequiredArgsConstructor
public class GuestBookController {
	
  //Service 주입
  private final GuestBookService service;
    
  @GetMapping("/")
  public String main(){
      log.info("시작 요청");
      //templates 에 있는 guestbook 디렉토리의 list.html을 출력
      return "/guestbook/list";
  }
  
  @GetMapping("/guestbook/list")
  public void list(PageRequestDTO pageRequestDTO, Model model){
      log.info("목록 보기 .....");
      PageResponseDTO result = service.getList(pageRequestDTO);
      model.addAttribute("result", result);
  }
  
  @GetMapping("/guestbook/register")
  public void register(){
      log.info("삽입 요청 페이지로 이동");
  }

  @PostMapping("/guestbook/register")
  public String register(GuestBookDTO dto, RedirectAttributes redirectAttributes){
      log.info("삽입 처리");
      //삽입 처리
      Long gno = service.register(dto);
      //리다이렉트 할 때 한 번만 사용하는 데이터 생성
      redirectAttributes.addFlashAttribute("msg", gno + " 삽입");
      //작업 후 목록보기로 리다이렉트
      return "redirect:/guestbook/list";
  }
  
  
  @GetMapping({"/guestbook/read", "/guestbook/modify"})
  //파라미터 중에서 gno 는 gno 에 대입되고
  //나머지는 requestDTO 에 대입됩니다. 다음 결과 페이지에 전송됩니다.
  public void read(long gno,
                   @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                   Model model){
      GuestBookDTO dto = service.read(gno);
      model.addAttribute("dto", dto);
  }
  
  @PostMapping("/guestbook/modify")
  public String modify(GuestBookDTO dto,
          			   @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
          			   RedirectAttributes redirectAttributes){
      //수정 메서드 수행
      service.modify(dto);
      
      //전달할 데이터 생성 - url파라미터에 보임
      redirectAttributes.addAttribute("page", requestDTO.getPage());
      redirectAttributes.addAttribute("gno", dto.getGno());
      redirectAttributes.addAttribute("type", requestDTO.getType());
      redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
      
      //상세보기로 리다이렉트
      return "redirect:/guestbook/read";
  }
  
  @PostMapping("/guestbook/remove")
  public String remove(Long gno,
                       RedirectAttributes redirectAttributes){
      //수정 메서드 수행
      service.remove(gno);
      //전달할 데이터 생성
      redirectAttributes.addFlashAttribute("msg", gno + " 삭제");
      
      //상세보기로 리다이렉트
      return "redirect:/guestbook/list";
  }
  
}



