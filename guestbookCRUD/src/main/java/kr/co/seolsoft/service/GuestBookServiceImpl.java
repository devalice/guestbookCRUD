package kr.co.seolsoft.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;

import kr.co.seolsoft.dto.GuestBookDTO;
import kr.co.seolsoft.dto.PageRequestDTO;
import kr.co.seolsoft.dto.PageResponseDTO;
import kr.co.seolsoft.entity.GuestBook;
import kr.co.seolsoft.entity.QGuestBook;
import kr.co.seolsoft.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService{
	
    //자동 주입받기 위해서 final로 선언해야 함
    private final GuestBookRepository repository;

	@Override
	public Long register(GuestBookDTO dto) {
		log.info(dto);
        
		//DTO 를 Entity로 변환
        GuestBook entity = dtoToEntity(dto);
        log.info(entity);
        
        //데이터 삽입
        repository.save(entity);
        
        //삽입한 데이터의 gno 리턴
        return entity.getGno();
	}

	//목록 가져오기
	@Override
	public PageResponseDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
		//Pageable 객체 생성
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        
        //결과를 가져오기
        //Page<GuestBook> result = repository.findAll(pageable);
        
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<GuestBook> result = repository.findAll(booleanBuilder, pageable);
        
        //Function(함수형인터페이스) 생성
        //R apply(T t)
        //람다 표현식 T -> R
        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDTO(entity));
        
        return new PageResponseDTO<>(result, fn);
	}

	@Override
	public GuestBookDTO read(Long gno) {
		//Optional 클래스
		//T타입의 객체를 포장해주는 Wrapper class
		//nullpointerexception을 제공되는 메소드로 간단히 회피 가능
		Optional<GuestBook> guestBook = repository.findById(gno);
		
        return guestBook.isPresent()?entityToDTO(guestBook.get()):null;
	}

	@Override
	public void modify(GuestBookDTO dto) {
        //수정할 데이터를 찾아오기
        Optional<GuestBook> result = repository.findById(dto.getGno());
        if(result.isPresent()){
            GuestBook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
		
        }
	}

	@Override
	public void remove(Long gno) {
        //삭제할 데이터를 찾아오기
        Optional<GuestBook> result = repository.findById(gno);
        if(result.isPresent()){
            repository.deleteById(gno);
        }
	}
	
	//검색 조건 만들기
    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();
        
        
        //querydsl - 동적 쿼리 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;
        
        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        //t c w 는 검색 화면에서 select 의 option 들의 value 가 되어야 합니다.
        if(type.contains("t")){
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }
        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

}
