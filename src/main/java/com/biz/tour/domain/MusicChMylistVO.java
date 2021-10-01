package com.biz.tour.domain;

import java.util.Date;

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
public class MusicChMylistVO {
	private int id;
	private int member_id;
	private int music_ch_id;
	private int iorder;
	private String title;
	private Date created_at;
	private String  author;
	private String uploaded_pic;
	private int pageIndex;
	private int itemsPerpage;
}
