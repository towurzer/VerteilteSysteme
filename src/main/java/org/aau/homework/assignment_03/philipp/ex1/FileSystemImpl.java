package org.aau.homework.assignment_03.philipp.ex1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class FileSystemImpl implements FileSystem {

    private final Path DIRECTORY_PATH;

    public FileSystemImpl(String path) {
        try {
            //Relative path
            DIRECTORY_PATH = Files.createDirectories(Paths.get(path));
            System.out.println("Created Folder: " + DIRECTORY_PATH.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> listFiles() throws RemoteException {
        try (var directory = Files.list(DIRECTORY_PATH)) {
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
    public byte[] loadFile(String filename) throws RemoteException {
        try (var directory = Files.list(DIRECTORY_PATH)) {
            Path filePath = directory
                    .filter(Files::isRegularFile)
                    .filter(Objects::nonNull)
                    .filter(path -> filename.equals(path.getFileName().toString()))
                    .findFirst().orElse(null);

            if (filePath == null) {
                throw new FileNotFoundException(filename);
            }
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
