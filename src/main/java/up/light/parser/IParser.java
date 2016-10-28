package up.light.parser;

import up.light.io.IResource;

/**
 * @version 1.0
 */
public interface IParser<T> {
	T parse(IResource res);
}
