package homework.assignment_03.ex1;

import homework.assignment_03.ex1.dto.File;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class FileSystemImpl implements FileSystem {

    private final List<File> files = new ArrayList<>();

    @Override
    public List<String> listFiles() throws RemoteException {
        return files.stream().map(File::getFilename).sorted().toList();
    }

    @Override
    public String loadFile(String fileName) throws RemoteException {
        File file = files.stream().filter(f -> f.getFilename().equals(fileName)).findFirst().orElse(null);
        if(file == null) {
            throw new RemoteException("File not found");
        }
        return file.getContent();
    }

    public void addFile(File file) {
        files.add(file);
    }
}
