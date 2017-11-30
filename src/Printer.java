import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {

    private final File file;

    public Printer(int id) {

        file = new File("PRINTER"+id);
    }

    public void print(StringBuffer line) {

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            try (FileWriter fileWriter = new FileWriter(file, true)) {
                try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
                    writer.write(line.toString());
                    writer.newLine();
                    writer.flush();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
