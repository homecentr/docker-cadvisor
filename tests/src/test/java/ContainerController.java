import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.HashMap;

public class ContainerController {
    private static final Logger logger = LoggerFactory.getLogger(ContainerController.class);

    private GenericContainer _container;

    protected void startContainer(HashMap<String, String> envVars) {
        String dockerImageTag = System.getProperty("image_tag");

        logger.info("Tested Docker image tag: {}", dockerImageTag);

        _container = new GenericContainer<>(dockerImageTag)
                .withEnv(envVars)
                .withPrivilegedMode(true)
                .waitingFor(Wait.forHealthcheck());

        _container.start();
        _container.followOutput(new Slf4jLogConsumer(logger));
    }

    public void stopContainerIfExists() {
        if(_container != null) {
            _container.stop();
            _container.close();
        }
    }

    protected GenericContainer getContainer() {
        return _container;
    }

    protected boolean hasErrorOutput() {
        String errOut = getContainer().getLogs(OutputFrame.OutputType.STDERR);
        String[] lines = errOut.split("\\r?\\n");

        for (String line : lines) {
            // These are warnings cAdvisor puts into output which are caused by the test environment
            // i.e. these warnings are expected
            if(line.startsWith("W0427") || line.startsWith("E0427")) {
                continue;
            }

            if(line.isEmpty()) {
                continue;
            }

            System.err.println(errOut);

            return true;
        }

        return false;
    }
}