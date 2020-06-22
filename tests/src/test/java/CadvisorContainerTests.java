import io.homecentr.testcontainers.containers.GenericContainerEx;
import io.homecentr.testcontainers.containers.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static helpers.ContainerLogsUtils.hasErrorOutputExceptKnownErrors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public abstract class CadvisorContainerTests {

  protected abstract GenericContainerEx getContainer();

  @Test
  public void notProduceErrorOutput() {
    assertFalse(hasErrorOutputExceptKnownErrors(getContainer()));
  }

  @Test
  public void listenOnWebUiPort() throws IOException {
    HttpResponse response = getContainer().makeHttpRequest(8080, "/");

    assertEquals(200,  response.getResponseCode());
  }

  @Test
  public void returnMetrics() throws IOException {
    HttpResponse response = getContainer().makeHttpRequest(8080, "/metrics");

    assertEquals(200,  response.getResponseCode());
  }
}