
import model.Repository;


import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class Response {

  //  private static final List<Repository> repositories = loadRepositories();
    static String jsonData;

  /*  static {
        Gson gson = new Gson();
        jsonData = gson.toJson(repositories);
    }

    private static List<Repository> loadRepositories() {
        Gson gson = new Gson();
        Resource resource = new ClassPathResource("response.json");
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
            Type listType = new TypeToken<List<Repository>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
}
