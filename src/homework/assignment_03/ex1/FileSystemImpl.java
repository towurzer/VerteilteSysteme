package homework.assignment_03.ex1;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class FileSystemImpl implements FileSystem {

    private final List<FileDTO> files = new ArrayList<>();

    @Override
    public List<String> listFiles() throws RemoteException {
        return files.stream().map(FileDTO::getFilename).sorted().toList();
    }

    @Override
    public String loadFile(String fileName) throws RemoteException {
        FileDTO file = files.stream().filter(f -> f.getFilename().equals(fileName)).findFirst().orElse(null);
        if(file == null) {
            throw new RemoteException("File not found");
        }
        return file.getContent();
    }

    public void addFile(FileDTO file) {
        files.add(file);
    }
}
