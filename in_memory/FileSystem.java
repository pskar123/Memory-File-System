import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class File {
    private String content;

    public File(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class Directory {
    private String name;
    private Map<String, File> files;
    private Map<String, Directory> directories;

    public Directory(String name) {
        this.name = name;
        this.files = new HashMap<>();
        this.directories = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public Map<String, Directory> getDirectories() {
        return directories;
    }
}

public class FileSystem {
    private Directory root;
    private Directory currentDir;

    public FileSystem() {
        root = new Directory("/");
        currentDir = root;
    }

    // mkdir: Create a new directory
    public void mkdir(String dirName) {
        currentDir.getDirectories().put(dirName, new Directory(dirName));
    }

    // cd: Change directory
    public void cd(String dirName) {
        if (dirName.equals("/")) {
            currentDir = root;
            return;
        }
        if (dirName.equals("..")) {
            if (currentDir != root) {
                currentDir = getParentDirectory(root, currentDir.getName());
            }
            return;
        }
        Directory newDir = currentDir.getDirectories().get(dirName);
        if (newDir != null) {
            currentDir = newDir;
        } else {
            System.out.println("Directory not found");
        }
    }
    public void cp(String source, String destination) {
        Directory sourceDir = currentDir.getDirectories().get(source);
        if (sourceDir != null) {
            Directory newDir = new Directory(destination);
            for (Map.Entry<String, Directory> entry : sourceDir.getDirectories().entrySet()) {
                newDir.getDirectories().put(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, File> entry : sourceDir.getFiles().entrySet()) {
                newDir.getFiles().put(entry.getKey(), entry.getValue());
            }
            currentDir.getDirectories().put(destination, newDir);
        } else {
            File sourceFile = currentDir.getFiles().get(source);
            if (sourceFile != null) {
                File newFile = new File(sourceFile.getContent());
                currentDir.getFiles().put(destination, newFile);
            } else {
                System.out.println("Source not found");
            }
        }
    }

    // rm: Remove a file or directory
    public void rm(String name) {
        Directory targetDir = currentDir.getDirectories().get(name);
        if (targetDir != null) {
            currentDir.getDirectories().remove(name);
        } else {
            File targetFile = currentDir.getFiles().get(name);
            if (targetFile != null) {
                currentDir.getFiles().remove(name);
            } else {
                System.out.println("File or directory not found");
            }
        }
    }

    // grep: Search for a specified pattern in a file
    public void grep(String pattern, String fileName) {
        File file = currentDir.getFiles().get(fileName);
        if (file != null) {
            String content = file.getContent();
            if (content.contains(pattern)) {
                System.out.println("Pattern found in " + fileName);
            } else {
                System.out.println("Pattern not found in " + fileName);
            }
        } else {
            System.out.println("File not found");
        }
    }

    // ls: List contents of current or specified directory
    public void ls(String dirName) {
        Directory targetDir = currentDir;
        if (!dirName.equals(".")) {
            targetDir = currentDir.getDirectories().get(dirName);
        }
        if (targetDir != null) {
            System.out.println("Files:");
            for (String fileName : targetDir.getFiles().keySet()) {
                System.out.println(fileName);
            }
            System.out.println("Directories:");
            for (String dir : targetDir.getDirectories().keySet()) {
                System.out.println(dir);
            }
        } else {
            System.out.println("Directory not found");
        }
    }

    // touch: Create a new empty file
    public void touch(String fileName) {
        currentDir.getFiles().put(fileName, new File(""));
    }

    // cat: Display contents of a file
    public void cat(String fileName) {
        File file = currentDir.getFiles().get(fileName);
        if (file != null) {
            System.out.println(file.getContent());
        } else {
            System.out.println("File not found");
        }
    }

    // echo: Write text to a file
    public void echo(String fileName, String content) {
        File file = currentDir.getFiles().get(fileName);
        if (file != null) {
            file.setContent(content);
        } else {
            currentDir.getFiles().put(fileName, new File(content));
        }
    }

    // mv: Move a file or directory
    public void mv(String source, String destination) {
        Directory sourceDir = currentDir.getDirectories().get(source);
        if (sourceDir != null) {
            Directory destDir = currentDir.getDirectories().get(destination);
            if (destDir != null) {
                destDir.getDirectories().put(source, sourceDir);
                currentDir.getDirectories().remove(source);
            } else {
                System.out.println("Destination directory not found");
            }
        } else {
            File sourceFile = currentDir.getFiles().get(source);
            if (sourceFile != null) {
                File destFile = currentDir.getFiles().get(destination);
                if (destFile != null) {
                    destFile.setContent(sourceFile.getContent());
                    currentDir.getFiles().remove(source);
                } else {
                    currentDir.getFiles().put(destination, sourceFile);
                    currentDir.getFiles().remove(source);
                }
            } else {
                System.out.println("Source not found");
            }
        }
    }





    // Helper function to get parent directory
    private Directory getParentDirectory(Directory current, String targetName) {
        for (Directory dir : current.getDirectories().values()) {
            if (dir.getDirectories().containsKey(targetName)) {
                return dir;
            }
        }
        return root;
    }

    public static void main(String[] args) {


        FileSystem fs = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("Enter a command (or 'exit' to quit): ");
            String command = scanner.nextLine();

            // Check the user input
            switch (command) {
                case "exit":
                    running = false;
                    break;
                case "ls":
                    fs.ls(".");
                    break;
                case "mkdir":
                    System.out.print("Enter directory name: ");
                    String dirName = scanner.nextLine();
                    fs.mkdir(dirName);
                    break;
                case "cd":
                    fs.cd("..");
                    break;
                case "touch":
                    System.out.print("Enter file name: ");
                    String fileName = scanner.nextLine();
                    fs.touch(fileName);
                    break;
                case "cat":
                    System.out.print("Enter file name: ");
                    String file = scanner.nextLine();
                    fs.cat(file);
                    break;
                case "echo":
                    System.out.print("Enter file name: ");
                    String echoFileName = scanner.nextLine();
                    System.out.print("Enter content: ");
                    String content = scanner.nextLine();
                    fs.echo(echoFileName, content);
                    break;
                case "mv":
                    System.out.print("Enter source: ");
                    String source = scanner.nextLine();
                    System.out.print("Enter destination: ");
                    String destination = scanner.nextLine();
                    fs.mv(source, destination);
                    break;
                case "cp":
                    System.out.print("Enter source: ");
                    String src = scanner.nextLine();
                    System.out.print("Enter destination: ");
                    String dest = scanner.nextLine();
                    fs.cp(src, dest);
                    break;
                case "rm":
                    System.out.print("Enter file/directory name: ");
                    String fileOrDir = scanner.nextLine();
                    fs.rm(fileOrDir);
                    break;
                case "grep":
                    System.out.print("Enter pattern: ");
                    String pattern = scanner.nextLine();
                    System.out.print("Enter file name: ");
                    String grepFile = scanner.nextLine();
                    fs.grep(pattern, grepFile);
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }

        scanner.close();
        System.out.println("Exiting...");

    }
}
