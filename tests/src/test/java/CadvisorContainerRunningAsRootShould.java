import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.HashMap;

public class CadvisorContainerRunningAsRootShould extends CadvisorContainerTests {

    private static ContainerController _controller;

    @BeforeClass
    public static void start() {
        HashMap<String, String> envVars = new HashMap<>();
        envVars.put("PUID", "0");
        envVars.put("PGID", "0");

        _controller = new ContainerController();
        _controller.startContainer(envVars);
    }

    @AfterClass
    public static void cleanUp() {
        _controller.stopContainerIfExists();
    }

    @Override
    protected ContainerController getController() {
        return _controller;
    }
}
