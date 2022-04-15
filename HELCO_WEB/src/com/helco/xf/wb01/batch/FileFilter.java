package com.helco.xf.wb01.batch;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class FileFilter implements FilenameFilter {
	private String keyWord;
	
	public FileFilter (String keyWord){
		this.keyWord = keyWord;
	}

	public boolean accept(File dir, String fileName) {
		String filter = this.keyWord
        .replaceAll("\\.", "\\\\.")
        .replaceAll("\\$", "\\\\$")
        .replaceAll("\\+", "\\\\+")
        .replaceAll("\\(", "\\\\(")
        .replaceAll("\\)", "\\\\)")
        .replaceAll("\\{", "\\\\{")
        .replaceAll("\\}", "\\\\}")
        .replaceAll("\\[", "\\\\[")
        .replaceAll("\\]", "\\\\]")
        .replaceAll("\\^", "\\\\^")
        .replaceAll("\\*", ".*")
        .replaceAll("\\?", ".");

		return Pattern.matches(filter, fileName);
	}

}
