import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class CadvisorContainerShould extends ContainerTestBase {

  @Test
  public void listenOnWebUiPort() throws IOException {
    startContainer();

    checkContainerListensOnWebUiPort();
  }

  @Test
  public void listenOnWebUiPortWhenRunningAsRoot() throws IOException {
    startContainer("0", "0");

    checkContainerListensOnWebUiPort();
  }

  @Test
  public void returnMetrics() throws IOException {
    startContainer();

    URL root = new URL(String.format("http://%s:%d/metrics",
    getContainer().getContainerIpAddress(),
    getContainer().getMappedPort(8080)));

    HttpURLConnection connection = (HttpURLConnection)root.openConnection();
    connection.connect();

    assertEquals(200,  connection.getResponseCode());
  }

  @Test
  public void returnSuccessOnHealthCheckEndpoint() throws IOException {
    startContainer();

    URL root = new URL(String.format("http://%s:%d/healthz",
    getContainer().getContainerIpAddress(),
    getContainer().getMappedPort(8080)));

    HttpURLConnection connection = (HttpURLConnection)root.openConnection();
    connection.connect();

    assertEquals(200,  connection.getResponseCode());
  }

  private void checkContainerListensOnWebUiPort() throws IOException {
    URL root = new URL(String.format("http://%s:%d",
            getContainer().getContainerIpAddress(),
            getContainer().getMappedPort(8080)));

    HttpURLConnection connection = (HttpURLConnection)root.openConnection();
    connection.connect();

    assertEquals(200,  connection.getResponseCode());
  }
}