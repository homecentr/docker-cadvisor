package helpers;

import io.homecentr.testcontainers.images.EnvironmentImageTagResolver;

public class CadvisorImageTagResolver extends EnvironmentImageTagResolver {
    public CadvisorImageTagResolver() {
        super("homecentr/cadvisor:local");
    }
}
