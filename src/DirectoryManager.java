import java.util.HashMap;

public class DirectoryManager {

    private final HashMap<String,FileInfo> files = new HashMap<>();

    public void enter(StringBuffer buffer, FileInfo info) {
        files.put(buffer.toString(),info);
    }

    public FileInfo lookup(StringBuffer buffer) {
        return files.get(buffer.toString());
    }

}
