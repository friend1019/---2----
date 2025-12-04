package com.bookshop01.common.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathUtils {
	private static final Path IMAGE_REPO_PATH = Paths.get(System.getProperty("user.dir"), "file_repo");

	private PathUtils() {
	}

	public static Path imageRepoPath() {
		return IMAGE_REPO_PATH;
	}
}
