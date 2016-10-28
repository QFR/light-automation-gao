package up.light.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorCode;

import up.light.FolderType;
import up.light.Setting;

/**
 * @version 1.0
 */
public class LightFileAppender extends FileAppender {
	static final String DEFAULT_FILENAME_PATTERN = "'log.'yyyy.MM.dd-HH.mm.ss'.txt'";
	private String mNamePattern;

	public LightFileAppender() {
	}

	// use this constructor when no properties or xml file found
	public LightFileAppender(Layout layout, String namePattern) throws IOException {
		super(layout, Setting.getPath(FolderType.LOG) + DateUtil.getDateString(namePattern));
	}

	public String getNamePattern() {
		return mNamePattern;
	}

	public void setNamePattern(String namePattern) {
		mNamePattern = namePattern;
	}

	// call this method when properties or xml file exists
	@Override
	public void activateOptions() {
		if (StringUtils.isBlank(mNamePattern)) {
			mNamePattern = DEFAULT_FILENAME_PATTERN;
		}
		
		String f = Setting.getPath(FolderType.LOG) + DateUtil.getDateString(mNamePattern);

		try {
			setFile(f, fileAppend, bufferedIO, bufferSize);
		} catch (IOException e) {
			errorHandler.error("setFile(" + fileName + "," + fileAppend + ") call failed.", e,
					ErrorCode.FILE_OPEN_FAILURE);
		}
	}
}
