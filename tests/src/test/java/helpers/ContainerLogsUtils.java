package helpers;

import io.homecentr.testcontainers.containers.GenericContainerEx;
import org.testcontainers.containers.output.OutputFrame;

public class ContainerLogsUtils {
    public static boolean hasErrorOutputExceptKnownErrors(GenericContainerEx container) {
        String errOut = container.getLogs(OutputFrame.OutputType.STDERR);
        String[] lines = errOut.split("\\r?\\n");

        for (String line : lines) {
            // These are warnings cAdvisor puts into output which are caused by the test environment
            // i.e. these warnings are expected
            // W0622 means the user does not have enough permissions to access a system resource like /dev/kmsg
            if(line.startsWith("W0427") || line.startsWith("E0427") || line.startsWith("W0622")) {
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
