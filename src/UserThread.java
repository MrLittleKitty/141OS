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
                    }
                    else if(currentLine.toString().startsWith(".print")) {
                        new PrintJobThread(new StringBuffer(currentLine.substring(7))).start();
                    }
                    else if(currentLine.toString().startsWith(".end")){
                        currentFile = null;
                        Main.DISK_ALLOCATOR.release(currentDisk);
                    }
                    else  if(currentFile != null) {
                        manager.writeLine(currentFile,currentLine);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
