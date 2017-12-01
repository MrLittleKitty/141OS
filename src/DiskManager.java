public class DiskManager {

    private DirectoryManager directoryManager;
    private final int[] nextFreeSectors;

    public DiskManager(int numberOfDisks) {
        directoryManager = new DirectoryManager();
        nextFreeSectors = new int[numberOfDisks];
    }

    public synchronized FileInfo getFileInfo(StringBuffer buffer) {
        return directoryManager.lookup(buffer);
    }

    public synchronized void createFile(StringBuffer fileName, FileInfo info) {
        directoryManager.enter(fileName, info);
    }

    public int getAndIncrementNextFreeSector(int index) {
        int next = nextFreeSectors[index];
        nextFreeSectors[index] = nextFreeSectors[index]+1;
        return next;
    }
}
