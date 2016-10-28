package up.light.parser;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import up.light.io.IResource;
import up.light.repository.ByStrategy;
import up.light.repository.LocatorBean;

/**
 * @version 1.0
 */
public class RepoJsonParser implements IParser<Map<String, LocatorBean>> {

	@Override
	public Map<String, LocatorBean> parse(IResource res) {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<List<By>>() {
		}.getType(), new MapDeserializer());
		Gson gson = builder.create();

		Map<String, LocatorBean> m = null;

		try (FileReader reader = new FileReader(res.getFile())) {
			m = gson.fromJson(reader, new TypeToken<Map<String, LocatorBean>>() {
			}.getType());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return m;
	}

}

class MapDeserializer implements JsonDeserializer<List<By>> {

	@Override
	public List<By> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		List<By> ls = new ArrayList<>();

		JsonArray arr = json.getAsJsonArray();
		String value = null;

		for (JsonElement e : arr) {
			if (e.isJsonNull())
				continue;

			value = e.getAsString();
			int i = value.indexOf("://");
			String byName = value.substring(0, i);
			String byValue = value.substring(i + 3);

			By by = ByStrategy.getBy(byName, byValue);
			ls.add(by);
		}

		return ls;
	}

}