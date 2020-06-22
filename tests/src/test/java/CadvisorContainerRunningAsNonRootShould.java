import helpers.CadvisorImageTagResolver;
import helpers.MachineIdFile;
import io.homecentr.testcontainers.containers.GenericContainerEx;
import io.homecentr.testcontainers.containers.wait.strategy.WaitEx;
import io.homecentr.testcontainers.images.PullPolicyEx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.io.IOException;

public class CadvisorContainerRunningAsNonRootShould extends CadvisorContainerTests {
    private static final Logger _logger = LoggerFactory.getLogger(CadvisorContainerRunningAsNonRootShould.class);

    private static GenericContainerEx _container;
    private static MachineIdFile _machineIdFile;

    @BeforeClass
    public static void start() throws IOException {
        _machineIdFile = MachineIdFile.create();

        _container =  new GenericContainerEx<>(new CadvisorImageTagResolver())
                .withImagePullPolicy(PullPolicyEx.never())
                .withPrivilegedMode(true)
                .withFileSystemBind("//var/run/docker.sock", "/var/run/docker.sock")


                // .withFileSystemBind("//dev/kmsg", "/dev/kmsg")
                .withFileSystemBind(_machineIdFile.getAbsolutePath(), "/etc/machine-id")
                .waitingFor(WaitEx.forHttp("/healthz").forPort(8080));

        _container.start();
        _container.followOutput(new Slf4jLogConsumer(_logger));
    }

    @AfterClass
    public static void cleanUp() {
        _container.close();
        _machineIdFile.close();
    }

    @Override
    protected GenericContainerEx getContainer() {
        return _container;
    }
}
