package kr.co.seolsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.seolsoft.entity.GuestBook;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, 
											 QuerydslPredicateExecutor<GuestBook>{
}



