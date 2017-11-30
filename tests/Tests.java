import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Tests{

    @BeforeEach
    public void addLineBreak() {
        print("");
    }

    @Test
    public void testDisk() {
        print("Testing Disk");

        Disk disk = new Disk(8192,1);
        print("Created new disk with size 8192");

        StringBuffer buffer = new StringBuffer("XXXXYYYY");
        print("Test buffer is: "+buffer.toString());

        print("Writing buffer to disk sector 0");
        disk.write(0,buffer);

        StringBuffer buffer1 = new StringBuffer();

        print("Attempting read from disk sector 0");
        disk.read(0,buffer1);

        print("Buffer read from disk is: "+buffer1.toString());

        print("Asserting that the disk correctly saved the buffer");
        assert buffer.toString().equals(buffer1.toString());

        print("Test complete");
    }

    @Test
    public void testPrinter() {
        print("Testing Printer");

        print("Deleting any old test output files");
        File file = new File("PRINTER1");
        file.delete();

        print("Created printer with ID = 1");
        Printer printer = new Printer(1);

        print("Creating test string buffers");
        StringBuffer test = new StringBuffer("XXXXYYYY");
        StringBuffer test1 = new StringBuffer("WWWWZZZZ");

        print("Test buffer 1 is: "+test.toString());
        print("Test buffer 2 is: "+test1.toString());

        print("Printing test buffer 1");
        printer.print(test);

        print("Asserting that a new output file was created");
        assert file.exists();

        try {
            print("Reading the created output file");
            List<String> lines = Files.readAllLines(file.toPath());

            print("Asserting the output file contains the test buffer");
            assert(lines.size() == 1);
            assert(lines.get(0).equals(test.toString()));

            print("Printing test buffer 2");
            printer.print(test1);

            print("Reading the output file again");
            lines = Files.readAllLines(file.toPath());

            print("Asserting the output file contains both buffers");
            assert(lines.size() == 2);
            assert(lines.get(0).equals(test.toString()));
            assert(lines.get(1).equals(test1.toString()));

            print("Test complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUser() {

        print("Testing User");

        print("Deleting any old test output files");
        File file = new File("PRINTER2");
        file.delete();

        UserThread user = new UserThread(2);

        print("Reading the test user file");
        print("Testing the commands save, end, and print.");
        user.readFile();

        print("Asserting that the output file exists");
        assert file.exists();

        try {
            print("Reading the created output file");
            List<String> lines = Files.readAllLines(file.toPath());

            print("Correct output file contains: XXXXZZZZ");

            print("Asserting the output file contains the correct output");
            assert(lines.size() == 1);
            assert("XXXXZZZZ".equals(lines.get(0)));

            print("Test complete");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void print(String message) {
        System.out.println(message);
    }
}