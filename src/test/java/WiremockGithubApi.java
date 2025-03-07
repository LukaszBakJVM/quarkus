import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Collections;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockGithubApi implements QuarkusTestResourceLifecycleManager {
    private WireMockServer wireMockServer;
    @Override
    public Map<String, String> start() {

        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor(8089);




        stubFor(
                get(urlMatching(".*"))
                        .atPriority(10)
                        .willReturn(aResponse().proxiedFrom("https://api.github.com")));
        return  Collections.singletonMap("services.GitHubClient/mp-rest/url", wireMockServer.baseUrl());
    }

    @Override
    public void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }

    }
}
