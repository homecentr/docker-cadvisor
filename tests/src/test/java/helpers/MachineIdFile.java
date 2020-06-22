package helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class MachineIdFile {
    private final File _file;

    public static MachineIdFile create() throws IOException {
        File tempFile = File.createTempFile("machine-id", "txt");

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(UUID.randomUUID().toString());
        }

        return new MachineIdFile(tempFile);
    }

    private MachineIdFile(File file) {
        _file = file;
    }

    public String getAbsolutePath() {
        return _file.getAbsolutePath();
    }

    public void close() {
        _file.delete();
    }
}
