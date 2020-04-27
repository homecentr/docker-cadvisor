import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.HashMap;

public class CadvisorContainerRunningAsNonRootShould extends CadvisorContainerTests {
    private static ContainerController _controller;

    @BeforeClass
    public static void start() {
        _controller = new ContainerController();
        _controller.startContainer(new HashMap<>());
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
