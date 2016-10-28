package up.light.utils;

import java.util.EmptyStackException;
import java.util.LinkedList;

public class Stack<T> {
	private LinkedList<T> list = new LinkedList<>();

	public T push(T item) {
		list.push(item);
		return item;
	}

	public T pop() {
		if (list.isEmpty()) {
			throw new EmptyStackException();
		}
		return list.pop();
	}

	public T peek() {
		if (list.isEmpty()) {
			throw new EmptyStackException();
		}
		return list.peek();
	}

	public boolean empty() {
		return list.isEmpty();
	}

	public int size() {
		return list.size();
	}

	@Override
	public String toString() {
		return list.toString();
	}

}
