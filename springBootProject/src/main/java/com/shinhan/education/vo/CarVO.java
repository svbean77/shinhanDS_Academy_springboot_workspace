package com.shinhan.education.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode*/

@Data // 모든 내용들을 @Data로만 사용할 수 있음
@Builder // default constructor를 제공하지 않음! NoArgs, AllArgs를 둘 다 추가해주면 사용 가능
@AllArgsConstructor
@NoArgsConstructor
public class CarVO {
	private String model;
	private int price;
}
