
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Repository;


import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class Response {

   private static final List<Repository> repositories = loadRepositories();
    public static final String jsonData;


    static {
        Gson gson = new Gson();
        jsonData = gson.toJson(repositories);
    }

    static List<Repository> loadRepositories() {
        Gson gson = new Gson();
        try (InputStreamReader reader = new InputStreamReader(
                Response.class.getClassLoader().getResourceAsStream("response.json"))) {


            Type listType = new TypeToken<List<Repository>>() {
            }.getType();
            return gson.fromJson(reader, listType);

        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
    }
}





