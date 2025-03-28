package homework.assignment_03.tobi.fileService.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FileServiceImpl implements FileService {
    private final Path FOLDER_PATH;

    public FileServiceImpl(String path){
        try{
            FOLDER_PATH = Files.createDirectories(Paths.get(path));
            System.out.println("Created Folder: " + FOLDER_PATH.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<String> listFiles() throws RemoteException {
        try (Stream<Path> fileList = Files.list(FOLDER_PATH)){
            return fileList.filter(Files::isRegularFile).map(Path::getFileName).filter(Objects::nonNull).map(Path::toString).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] loadFile(String fileName) throws RemoteException, FileNotFoundException {
        try (Stream<Path> fileList = Files.list(FOLDER_PATH)) {
            Path filePath = fileList.filter(Files::isRegularFile).filter(Objects::nonNull).filter(path -> fileName.equals(path.getFileName().toString())).findFirst().orElse(null);

            if (filePath == null){
                throw new FileNotFoundException(fileName);
            }

            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
