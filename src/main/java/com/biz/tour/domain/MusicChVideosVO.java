package com.biz.tour.domain;

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
public class MusicChVideosVO {
	private int id;
	private int musicch_id;
	private int  iorder;
	private String uploaded_filename;
	private String original_filename;

}
