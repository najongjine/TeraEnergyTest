package com.biz.tour.domain;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MusicChVO {
	private int id;
	private String title;
	private String author;
	private String uploaded_pic;
	private String original_pic;
	private String uploaded_filename;
	private String original_filename;
	private int visitcnt;
	private Date created_at;
	private int itemsPerpage;
	private int pageIndex;

}
