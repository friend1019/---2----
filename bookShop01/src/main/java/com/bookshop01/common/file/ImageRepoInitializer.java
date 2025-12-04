package com.bookshop01.common.file;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.bookshop01.common.util.PathUtils;

@Component
public class ImageRepoInitializer implements InitializingBean {
	private static final Map<Integer, String> SAMPLE_IMAGES = new LinkedHashMap<Integer, String>();

	static {
		SAMPLE_IMAGES.put(1001, "main_banner01.jpg");
		SAMPLE_IMAGES.put(1002, "main_banner02.jpg");
		SAMPLE_IMAGES.put(1003, "main_banner03.jpg");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Path root = PathUtils.imageRepoPath();
		Files.createDirectories(root.resolve("temp"));
		for (Map.Entry<Integer, String> entry : SAMPLE_IMAGES.entrySet()) {
			Path goodsDir = root.resolve(String.valueOf(entry.getKey()));
			Files.createDirectories(goodsDir);
			ClassPathResource resource = new ClassPathResource("sample-images/" + entry.getValue());
			if (!resource.exists()) {
				continue;
			}
			try (InputStream in = resource.getInputStream()) {
				Files.copy(in, goodsDir.resolve(entry.getValue()), StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}
}
