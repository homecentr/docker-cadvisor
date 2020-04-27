import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public abstract class CadvisorContainerTests {

  protected abstract ContainerController getController();

  @Test
  public void notProduceErrorOutput() {
    assertFalse(getController().hasErrorOutput());
  }

  @Test
  public void listenOnWebUiPort() throws IOException {
    URL root = new URL(String.format("http://%s:%d",
            getController().getContainer().getContainerIpAddress(),
            getController().getContainer().getMappedPort(8080)));

    HttpURLConnection connection = (HttpURLConnection)root.openConnection();
    connection.connect();

    assertEquals(200,  connection.getResponseCode());
  }

  @Test
  public void returnMetrics() throws IOException {
    URL root = new URL(String.format("http://%s:%d/metrics",
          getController().getContainer().getContainerIpAddress(),
          getController().getContainer().getMappedPort(8080)));

    HttpURLConnection connection = (HttpURLConnection)root.openConnection();
    connection.connect();

    assertEquals(200,  connection.getResponseCode());
  }

  @Test
  public void returnSuccessOnHealthCheckEndpoint() throws IOException {
    URL root = new URL(String.format("http://%s:%d/healthz",
            getController().getContainer().getContainerIpAddress(),
            getController().getContainer().getMappedPort(8080)));

    HttpURLConnection connection = (HttpURLConnection)root.openConnection();
    connection.connect();

    assertEquals(200,  connection.getResponseCode());
  }
}