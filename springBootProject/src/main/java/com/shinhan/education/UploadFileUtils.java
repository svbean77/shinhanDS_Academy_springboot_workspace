package com.shinhan.education;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;
import org.springframework.util.FileCopyUtils;
import net.coobird.thumbnailator.Thumbnails;
public class UploadFileUtils {
	// 썸네일 이미지 크기
	static final int THUMB_WIDTH = 300;
	static final int THUMB_HEIGHT = 300;

	// 파일 업로드
	public static String fileUpload(String uploadPath, String fileName, byte[] fileData, String ymdPath)
			throws Exception {
		UUID uid = UUID.randomUUID(); // 같은 이름의 파일이 없도록 하기 위해 유일한 ID 생성해 파일명에 붙임
		String newFileName = uid + "_" + fileName;
		String imgPath = uploadPath + ymdPath;
		File target = new File(imgPath, newFileName);
		FileCopyUtils.copy(fileData, target);
		String thumbFileName = "s_" + newFileName;
		File image = new File(imgPath + File.separator + newFileName);
		
		// 여기는 썸네일을 만드는 코드 -> 따로 함수를 빼서 첫 사진에 대해서만 실행해주면 될 것 같다!
		File thumbnail = new File(imgPath + File.separator + "s" + File.separator + thumbFileName);
		if (image.exists()) {
			thumbnail.getParentFile().mkdirs();
			Thumbnails.of(image).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(thumbnail);
		}
		return newFileName;
	}

	// 폴더이름 및 폴더 생성
	public static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		int pos = uploadPath.lastIndexOf(File.separator); // "\\"로 찾아도 됨
		String path = uploadPath.substring(0, pos);
		String folder = uploadPath.substring(pos); // "\"부터 들어가야 하기 때문에 pos부터 시작임
		makeDir(path, folder);
		makeDir(uploadPath, yearPath, monthPath, datePath);
		makeDir(uploadPath, yearPath, monthPath, datePath + "\\s");
		return datePath;
	}

	// 폴더 생성
	private static void makeDir(String uploadPath, String... paths) {
		if (new File(paths[paths.length - 1]).exists())
			return;
		for (String path : paths) {
			File dirPath = new File(uploadPath + path);
			if (!dirPath.exists())
				dirPath.mkdir();
		}
	}
}
