package com.bookshop01.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookshop01.common.util.PathUtils;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class FileDownloadController {
	private static final Path IMAGE_REPO_PATH = PathUtils.imageRepoPath();
	
	@RequestMapping("/download")
	protected void download(@RequestParam("fileName") String fileName,
		                 	@RequestParam("goods_id") String goods_id,
			                 HttpServletResponse response) throws Exception {
		Path filePath = IMAGE_REPO_PATH.resolve(goods_id).resolve(fileName);
		File image = filePath.toFile();
		if (!image.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		response.setHeader("Cache-Control","no-cache");
		response.addHeader("Content-disposition", "attachment; fileName="+fileName);
		try (OutputStream out = response.getOutputStream();
			 FileInputStream in = new FileInputStream(image)) {
			byte[] buffer=new byte[1024*8];
			while(true){
				int count=in.read(buffer);
				if(count==-1)
					break;
				out.write(buffer,0,count);
			}
		}
	}
	
	
	@RequestMapping("/thumbnails.do")
	protected void thumbnails(@RequestParam("fileName") String fileName,
                            	@RequestParam("goods_id") String goods_id,
			                 HttpServletResponse response) throws Exception {
		Path filePath = IMAGE_REPO_PATH.resolve(goods_id).resolve(fileName);
		File image = filePath.toFile();
		
		if (!image.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		try (OutputStream out = response.getOutputStream()) {
			Thumbnails.of(image).size(121,154).outputFormat("png").toOutputStream(out);
			out.flush();
		}
	}
}
