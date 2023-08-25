import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class WordCount {
    
    // Calculate the number of words in the files stored under the directory name
    // available at argv[1].
    //
    // Assume a depth 3 hierarchy:
    //   - Level 1: root
    //   - Level 2: subdirectories
    //   - Level 3: files
    //
    // root
    // ├── subdir 1
    // │     ├── file
    // │     ├── ...
    // │     └── file
    // ├── subdir 2
    // │     ├── file
    // │     ├── ...
    // │     └── file
    // ├── ...
    // └── subdir N
    // │     ├── file
    // │     ├── ...
    // │     └── file
    public static void main(String[] args) {
        //if (args.length != 1) {
        //    System.err.println("Usage: java WordCount <root_directory>");
        //    System.exit(1);
        //}

        //String rootPath = args[0];
        String rootPath = "<root dir>";
        File rootDir = new File(rootPath);
        File[] subdirs = rootDir.listFiles();
        int count = 0;
        Semaphore mutex = new Semaphore(1);
        Thread[] processos = new Thread[subdirs.length];

        if (subdirs != null) {
            for (int i=0; i<subdirs.length; i++) {
                processos[i] = new Thread();
                processos[i].start();
                if (subdirs[i].isDirectory()) {
                    String dirPath = rootPath + "/" + subdirs[i].getName();
                    try {
                        mutex.acquireUninterruptibly();
                        count += wcDir(dirPath);
                    } finally{
                        mutex.release();
                    }
                }
            }
        }

        System.out.println(count);
    }

    public static int wc(String fileContent) {
        String[] words = fileContent.split("\\s+");
        return words.length;
    }

    public static int wcFile(String filePath) {
        System.out.println("Lendo arquivo: " + filePath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder fileContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }

            reader.close();
            return wc(fileContent.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int wcDir(String dirPath) {
        System.out.println("Lendo diretório: " + dirPath);
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        int count = 0;

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    count += wcFile(file.getAbsolutePath());
                }
            }
            return count;
        }
        System.out.println("Terminei de ler diretório: " + dirPath);
        return count;
    }
}
