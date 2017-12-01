import java.io.*;

public class UserThread extends Thread {

    private final int id;
    private final File file;

    private int currentDisk = 0;
    private StringBuffer currentLine;
    private StringBuffer currentFile = null;

    private DiskManager manager = new DiskManager(4);

    public UserThread(int id) {
        this.id = id;
        file = new File("USER"+id);
        currentLine = new StringBuffer();
    }

    @Override
    public void run() {

        try(FileReader fileReader = new FileReader(file)){
            try(BufferedReader reader = new BufferedReader(fileReader)){
                String line = null;
                while((line = reader.readLine()) != null) {
                    currentLine.setLength(0);
                    currentLine.append(line);

                    if(currentLine.toString().startsWith(".save")) {
                        currentFile = new StringBuffer(currentLine.substring(6));
                        currentDisk = Main.DISK_ALLOCATOR.request();
                        Main.print("Saving file "+currentFile.toString()+" to disk "+currentDisk);
                    }
                    else if(currentLine.toString().startsWith(".print")) {
                        new PrintJobThread(new StringBuffer(currentLine.substring(7))).start();
                    }
                    else if(currentLine.toString().startsWith(".end")){
                        Main.print("Saved file "+currentFile.toString()+" to disk "+currentDisk);
                        currentFile = null;
                        Main.DISK_ALLOCATOR.release(currentDisk);
                    }
                    else  if(currentFile != null) {
                        Disk disk = Main.DISKS[currentDisk];
                        FileInfo info = Main.DISK_MANAGER.getFileInfo(currentFile);
                        if(info == null) {
                            info = new FileInfo();
                            info.diskNumber = currentDisk;
                            info.fileLength = 1;
                            info.startingSector = Main.DISK_MANAGER.getAndIncrementNextFreeSector(currentDisk);
                            Main.DISK_MANAGER.createFile(currentFile, info);
                            disk.write(info.startingSector,currentLine);

                            //Because a file write takes 200 milliseconds
                            Thread.sleep(200);
                        }
                        else {
                            int nextFreeSector = Main.DISK_MANAGER.getAndIncrementNextFreeSector(currentDisk);
                            info.fileLength = info.fileLength+1;
                            disk.write(nextFreeSector,currentLine);

                            //Because a file write takes 200 milliseconds
                            Thread.sleep(200);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
