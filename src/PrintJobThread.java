public class PrintJobThread extends Thread {

    private final StringBuffer fileName;
    public PrintJobThread(StringBuffer fileName) {
        this.fileName = new StringBuffer(fileName);
    }

    @Override
    public void run() {

        FileInfo info = Main.DISK_MANAGER.getFileInfo(fileName);

        Disk disk = Main.DISKS[info.diskNumber];

        int printerIndex = Main.PRINTER_ALLOCATOR.request();
        Printer printer = Main.PRINTERS[printerIndex];

        StringBuffer sector = new StringBuffer();
        for(int i = 0; i < info.fileLength; i++) {
            sector.setLength(0);

            disk.read(info.startingSector+i,sector);
            try {
                //Because a read from disk takes 200 miliseconds
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            printer.print(sector);

            try {
                //Because the printer takes 2750 miliseconds to print a line
                Thread.sleep(2750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Main.PRINTER_ALLOCATOR.release(printerIndex);
    }
}
