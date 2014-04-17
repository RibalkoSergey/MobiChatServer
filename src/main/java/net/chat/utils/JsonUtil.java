package net.chat.utils;


import net.chat.interfaces.IJSONableEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * User: sergeyr
 * Date: 10/31/11
 */
public class JsonUtil {

    public static <T extends IJSONableEntity> JSONArray toArray(Collection<T> entities) {
        if (entities == null) return new JSONArray();

        JSONArray results = new JSONArray();

        for (T each : entities)
            results.put(each.toJson());

        return results;
    }

    public static List<Long> getLongsListFromJsonArray(JSONArray array) {
        List<Long> result = new ArrayList<Long>();
        for (int i = 0; i < array.length(); i++)
            result.add(Long.parseLong((String) array.get(i)));

        return result;
    }

    public static JSONArray preparedJSONArray(JSONObject... objects) {
        return new JSONArray(Arrays.asList(objects));
    }
}
