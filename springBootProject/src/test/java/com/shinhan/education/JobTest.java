package com.shinhan.education;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.JobsRepository;
import com.shinhan.education.vo.JobVO;

@SpringBootTest
public class JobTest {
	@Autowired
	JobsRepository repo;
	Logger logger = LoggerFactory.getLogger(JobTest.class);

	@Test
	// Create
	public void test1() {
		logger.info("1. Create");
		String[] arr = { "0또개발자", "1마케팅", "2SI개발자", "3SM개발자", "4웹개발자", "5앱개발자", "6무슨개발자", "7자바개발자", "8JS개발자",
				"9마지막개발자" };
		IntStream.range(0, arr.length).forEach(idx -> {
			JobVO job = JobVO.builder().jobId("직책코드JOB-" + idx).jobTitle(arr[idx]).minSalary(1000 + (idx * 10))
					.maxSalary(10000 + (idx * 20)).build();
			
			// 한 번에 돌려보기 위해 모든 값을 save함 -> 이미 존재하던 데이터는 create가 아니기 때문에 regdate가 null로 update 되어버림!
			// 따라서 존재하지 않는 데이터만 insert되게 하기 위해 조건 사용
			JobVO tmp = repo.findById("직책코드JOB-" + idx).orElse(null);
			if (tmp == null) {
				repo.save(job);
			}
		});
	}

	@Test
	// Read
	public void test2() {
		logger.info("2. Read");
		repo.findAll().forEach(job -> logger.info(job.toString()));

		Iterable<JobVO> datas = repo.findAll();
		List<JobVO> joblist = (List<JobVO>) datas; // 형 변환도 할 수 있음!
	}

	@Test
	// Read - 이 방식처럼 id를 검색한 후 있으면 get해야 함!
	public void test3() {
		String title = "직책코드JOB-00";
		Optional<JobVO> jobOptional = repo.findById(title);
		if (jobOptional.isPresent()) {
			JobVO job = jobOptional.get();
			logger.info(job.toString());
		} else {
			logger.info(title + " 직책이 존재하지 않음");
		}
	}

	@Test
	// Update
	public void test4() {
		logger.info("3. Update");
		// 위의 확인 후 찍는 방법을 이렇게 한 줄로 바꿀 수 있음! 있을 때만 실행
		// 단, 있을 때를 확인해야 한다면 orElse로 받거나 위처럼 Optional로 받은 후 확인해야 함!
		repo.findById("직책코드JOB-1").ifPresent(j -> logger.info("변경전: " + j.toString()));
		repo.findById("직책코드JOB-1").ifPresent(job -> {
			job.setJobTitle("1수정!! 마케팅 아니야");
			job.setMaxSalary(99999999);
			repo.save(job);
		});

		repo.findById("직책코드JOB-1").ifPresent(j -> logger.info("변경후: " + j.toString()));
	}

	@Test
	// Delete
	public void test5() {
		logger.info("4. Delete");
		JobVO job = repo.findById("직책코드JOB-0").orElse(null);
		if (job != null) {
			repo.delete(job);
		}

//		repo.deleteById("직책코드JOB-0"); // 없는 애를 지우려고 하면 에러
		repo.deleteById("직책코드JOB-9");
		repo.deleteById("직책코드JOB-5");
		repo.deleteById("직책코드JOB-3");

		repo.findAll().forEach(j -> logger.info(j.toString()));
//		repo.deleteAll(); //전체 삭제

	}
}
