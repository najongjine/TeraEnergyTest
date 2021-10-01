package com.biz.tour.domain.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PageDTO {
	private long totalCount;
	private long listPerPage; //한페이지에 보여질 데이터 개수
	private long pageCount; //하단에 보여질 페이지번호 리스트 개수
	private long firstPageNo;
	private long finalPageNo; //마지막 페이지(계산결과)
	private long prePageNo;
	private long startPageNo; // 보여지는 리스트의 시작페이지 번호
	private long nextPageNo;
	private long endPageNo; // 보여지는 리스트의 끝번호
	private long currentPageNo;
}
