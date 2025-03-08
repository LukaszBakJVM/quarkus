import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Collections;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockGithubApi implements QuarkusTestResourceLifecycleManager {
    private WireMockServer wireMockServer;
    @Override
    public Map<String, String> start() {

        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();
        int port = wireMockServer.port();
        configureFor(port);





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
