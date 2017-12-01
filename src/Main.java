import javax.annotation.Resource;
import javax.naming.spi.DirectoryManager;
import java.io.File;
import java.util.Scanner;

public class Main {

    private static final int NUMBER_OF_USERS = 4;
    private static final int NUMBER_OF_DISKS = 2;
    private static final int NUMBER_OF_PRINTERS = 3;

    public static final Disk[] DISKS = new Disk[NUMBER_OF_DISKS];
    public static final Printer[] PRINTERS = new Printer[NUMBER_OF_PRINTERS];
    public static final UserThread USERS[] = new UserThread[NUMBER_OF_USERS];

    public static final DiskManager DISK_MANAGER = new DiskManager(NUMBER_OF_DISKS);
    public static final ResourceManager DISK_ALLOCATOR = new ResourceManager(NUMBER_OF_DISKS);
    public static final ResourceManager PRINTER_ALLOCATOR = new ResourceManager(NUMBER_OF_PRINTERS);

    static {
        for(int i = 1; i <= DISKS.length; i++) {
            DISKS[i-1] = new Disk(8192,i);
        }
        for(int i = 1; i <= PRINTERS.length; i++) {
            PRINTERS[i-1] = new Printer(i);
        }
        for(int i = 1; i <= USERS.length; i++) {
            USERS[i-1] = new UserThread(i);
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < USERS.length; i++) {
            USERS[i].start();
        }
    }

    public static void print(String message) {
        System.out.println(message);
    }
}
