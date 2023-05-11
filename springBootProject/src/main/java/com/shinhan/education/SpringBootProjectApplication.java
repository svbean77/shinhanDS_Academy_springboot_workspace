package com.shinhan.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// project 생성 시 패키지의 하위에 있는 패키지는 자동으로 스캔됨 -> 스캔을 따로 하지 않기 위해서는 패키지를 com.shinhan.education.패키지로 이름을 지어야 함! (자동 스캔)
//@ComponentScan(basePackages = {"com.shinhan"})
//@EntityScan("com.shinhan") 
//@EnableJpaRepositories("com.shinhan") // 다른 패키지의 repository를 읽기 위해 추가 (Repository 활성화)
public class SpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}

}
