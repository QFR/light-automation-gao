package up.light.reader;

import java.io.IOException;

import up.light.io.FileSystemResource;
import up.light.reader.impl.ExcelReader;

public class ReaderFactory {
	public static IReader getReader(FileSystemResource res) {
		String type = null;
		try {
			type = res.getFile().getName();
		} catch (IOException e) {
			e.printStackTrace();
		}
		type = type.substring(type.lastIndexOf(".") + 1);
		IReader ret = null;
		
		switch (type) {
		case "xls":
		case "xlsx":
		case "xlsm":
			ret = new ExcelReader(res);
			break;
		default:
			break;
		}
		
		return ret;
	}
}
