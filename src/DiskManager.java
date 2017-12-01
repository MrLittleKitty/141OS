public class DiskManager {

    private DirectoryManager directoryManager;
    private final int[] nextFreeSectors;

    public DiskManager(int numberOfDisks) {
        directoryManager = new DirectoryManager();
        nextFreeSectors = new int[numberOfDisks];
    }

    public FileInfo getFileInfo(StringBuffer buffer) {
        return directoryManager.lookup(buffer);
    }

    public int getAndIncrementNextFreeSector(int index) {
        int next = nextFreeSectors[index];
        nextFreeSectors[index] = nextFreeSectors[index]+1;
        return next;
    }

    public void writeLine(StringBuffer fileName, StringBuffer line) {
        FileInfo info = directoryManager.lookup(fileName);
        if(info == null) {
            info = new FileInfo();
            info.diskNumber = 0;
            info.fileLength = 0;
            info.startingSector = nextFreeSector(0);
            directoryManager.enter(fileName,info);
        }
        int sectorOffset = info.fileLength / disks[info.diskNumber].getSectorSize();
        info.fileLength += line.length();

        disks[info.diskNumber].write(info.startingSector+sectorOffset,line);
    }
}
