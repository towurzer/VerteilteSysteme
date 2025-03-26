package homework.assignment_03.ex1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FileSystemImpl implements FileSystem {

    private final Path ROOT_PATH;

    public FileSystemImpl(String path) {
        //Relative path
        ROOT_PATH = Paths.get(path);
        try {
            Files.createDirectories(ROOT_PATH);
            System.out.println("Created Folder: " + ROOT_PATH.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> listFiles() throws RemoteException {
        try (var directory = Files.list(ROOT_PATH)) {
            return directory
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .filter(Objects::nonNull)
                    .map(Path::toString)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String loadFile(String fileName) throws RemoteException {
        try (var directory = Files.list(ROOT_PATH)) {
            Path filePath = directory
                    .filter(Files::isRegularFile)
                    .filter(Objects::nonNull)
                    .filter(path -> fileName.equals(path.getFileName().toString()))
                    .findFirst().orElse(null);

            if (filePath == null) {
                throw new FileNotFoundException(fileName);
            }

            StringBuilder builder = new StringBuilder();
            for (String line : Files.readAllLines(filePath)) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFolder() throws IOException {
        try (var fileStream = Files.walk(ROOT_PATH)) {
            fileStream.sorted(Comparator.reverseOrder()) //First files then directories
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("Error when deleting file: " + path.toAbsolutePath());
                        }
                    });
        }
    }
}
