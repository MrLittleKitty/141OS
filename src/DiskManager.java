public class DiskManager {

    private DirectoryManager manager;
    private final Disk[] disks;
    private final int[] nextFreeSectors;

    public DiskManager(int numberOfDisks) {
        manager = new DirectoryManager();
        disks = new Disk[numberOfDisks];
        for(int i = 0; i < disks.length; i++) {
            disks[i] = new Disk(8192, i);
            //TODO---Remove all of this
        }
        nextFreeSectors = new int[numberOfDisks];
    }

    public void writeLine(StringBuffer fileName, StringBuffer line) {
        FileInfo info = manager.lookup(fileName);
        if(info == null) {
            info = new FileInfo();
            info.diskNumber = 0;
            info.fileLength = 0;
            info.startingSector = nextFreeSector(0);
            manager.enter(fileName,info);
        }
        int sectorOffset = info.fileLength / disks[info.diskNumber].getSectorSize();
        info.fileLength += line.length();

        disks[info.diskNumber].write(info.startingSector+sectorOffset,line);
    }

    public void printFile(StringBuffer fileName, Printer printer) {

        FileInfo file = manager.lookup(fileName);

        int sectorSize = disks[file.diskNumber].getSectorSize();
        int upper = file.fileLength/sectorSize;

        for(int i = 0; i < upper; i++) {
            StringBuffer buffer = new StringBuffer();
            disks[file.diskNumber].read(file.startingSector+i,buffer);
            printer.print(buffer);
        }

    }

    public int nextFreeSector(int disk){
        return nextFreeSectors[disk];
    }
}
