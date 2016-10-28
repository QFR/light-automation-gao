package up.light.utils;

import java.util.Iterator;

import up.light.io.FileSystemResource;
import up.light.reader.IReader;
import up.light.reader.ReaderFactory;

public class DataIterator implements Iterator<Object[]> {
	private IReader reader;
	private int currentIndex;
	
	public DataIterator(FileSystemResource res, String name) {
		reader = ReaderFactory.getReader(res);
		setGroup(name);
	}
	
	public void setGroup(String name) {
		reader.changeGroup(name);
	}

	@Override
	public boolean hasNext() {
		return reader.getNextIndex(currentIndex) > 0;
	}

	@Override
	public Object[] next() {
		currentIndex = reader.getNextIndex(currentIndex);
		return new Object[]{
				reader.readLineWithTitle(currentIndex)
		};
	}

}
