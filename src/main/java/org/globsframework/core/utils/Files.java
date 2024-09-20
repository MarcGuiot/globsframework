package org.globsframework.core.utils;

import org.globsframework.core.utils.exceptions.IOFailure;
import org.globsframework.core.utils.exceptions.ItemNotFound;
import org.globsframework.core.utils.exceptions.ResourceAccessFailed;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Files {
    private Files() {
    }

    public static String loadFileToString(String fileName) throws IOFailure {
        File file = new File(fileName);
        return loadFileToString(file);
    }

    public static String loadFileToString(File file) {
        StringBuilder builder = new StringBuilder();
        FileReader fileReader = null;
        BufferedReader reader = null;
        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                builder.append(line).append(Strings.LINE_SEPARATOR);
            }
            return builder.toString();
        } catch (IOException e) {
            throw new IOFailure(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                } else if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                throw new IOFailure(e);
            }
        }
    }

    public static String loadFileToString(String fileName, final String charsetName) throws IOFailure, ItemNotFound {
        try {
            return loadStreamToString(new InputStreamReader(new FileInputStream(fileName), charsetName));
        } catch (UnsupportedEncodingException e) {
            throw new IOFailure(e);
        } catch (FileNotFoundException e) {
            throw new ItemNotFound(e);
        }
    }

    public static String loadStreamToString(InputStream stream, final String charsetName) throws IOFailure {
        try {
            return loadStreamToString(new InputStreamReader(stream, charsetName));
        } catch (UnsupportedEncodingException e) {
            throw new IOFailure(e);
        }
    }

    public static String loadStreamToString(Reader input) throws IOFailure {
        try {
            Reader reader = new BufferedReader(input);
            StringBuilder result = new StringBuilder();
            char[] buffer = new char[1024];
            int count = reader.read(buffer);
            while (count != -1) {
                result.append(buffer, 0, count);
                count = reader.read(buffer);
            }
            return result.toString();
        } catch (IOException e) {
            throw new IOFailure(e);
        }
    }

    public static void dumpStringToFile(File file, String content) throws IOFailure {
        if (file == null) {
            return;
        }
        dumpStringToFile(file.getAbsolutePath(), content);
    }

    public static String dumpStringToFile(String fileName, String content) throws IOFailure {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName);
            writer.append(content);
            writer.close();
            return fileName;
        } catch (IOException e) {
            throw new IOFailure(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new IOFailure(e);
                }
            }
        }
    }

    public static boolean deleteWithSubtree(File directory) {
        return delete(directory, true);
    }

    public static boolean deleteSubtreeOnly(File directory) {
        return delete(directory, false);
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int readed;
        while ((readed = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, readed);
        }
        outputStream.close();
        inputStream.close();
    }

    public static void copyFileToOutputStream(String filePath, OutputStream outputStream) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        byte[] bytes = new byte[1024 * 10];
        int readed;
        while ((readed = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, readed);
        }
    }

    private static boolean delete(File directory, boolean andRoot) {
        File[] files = directory.listFiles();
        if (files == null) {
            return false;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteWithSubtree(file);
            } else {
                file.delete();
            }
        }
        return andRoot ? directory.delete() : true;
    }

    public static void copyStreamTofile(InputStream inputStream, String file) throws IOException {
        copyStream(inputStream, new FileOutputStream(file));
    }

    public static String copyResourceToTmpFile(Object obj, String resourceName) throws IOException {
        InputStream stream = obj.getClass().getResourceAsStream(resourceName);
        if (stream == null) {
            throw new FileNotFoundException("Failed to find " + resourceName);
        }
        int i = resourceName.indexOf(".");
        String extention = "tmp";
        if (i > -1) {
            extention = resourceName.substring(i, resourceName.length());
        }

        File tmp = File.createTempFile("tmp", extention);
        tmp.deleteOnExit();
        String path = tmp.getAbsolutePath();
        copyStreamTofile(stream, path);
        return path;
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int readed;
        while ((readed = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, readed);
        }
        outputStream.close();
    }

    public static Properties loadProperties(Class refClass, String fileName) throws ResourceAccessFailed {
        InputStream stream = getStream(refClass, fileName);
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new ResourceAccessFailed("Could not load properties file");
        }
        return properties;
    }

    public static SortedSet<Object> loadPropertyKeys(String fileName, Class refClass) throws IOException {
        Properties root = new Properties();
        root.load(getStream(refClass, fileName));
        TreeSet<Object> result = new TreeSet<Object>();
        result.addAll(root.keySet());
        return result;
    }

    public static InputStream getStream(Class refClass, String fileName) throws ResourceAccessFailed {
        InputStream stream = refClass.getResourceAsStream(fileName);
        if (stream == null) {
            throw new ResourceAccessFailed("Resource file '" + fileName + "' not found for class: " + refClass.getName());
        }
        return stream;
    }

    public static void createParentDirs(File file) {
        if (file == null) {
            return;
        }
        File parent = file.getParentFile();
        if (parent == null) {
            return;
        }
        parent.mkdirs();
    }

    public static void copyInUtf8(InputStream contentAsStream, String charset, OutputStream fileOutputStream) throws IOException {
        InputStreamReader inputStreamReader;
        if (Strings.isNotEmpty(charset)) {
            inputStreamReader = new InputStreamReader(contentAsStream, charset);
        } else {
            inputStreamReader = new InputStreamReader(contentAsStream);
        }
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        char[] chars = new char[1024];
        int readed;
        while ((readed = inputStreamReader.read(chars)) != -1) {
            outputStreamWriter.write(chars, 0, readed);
        }
        outputStreamWriter.close();
    }

    public static boolean copyDirectory(File source, File target, Set<String> filesToIgnore) {
        try {
            File[] files = source.listFiles();
            if (files != null && files.length != 0) {
                for (File file : files) {
                    File targetFile = new File(target, file.getName());
                    if (!filesToIgnore.contains(targetFile.getName())) {
                        if (file.isDirectory()) {
                            if (!targetFile.mkdir()) {
                                return false;
                            }
                            if (!copyDirectory(file, targetFile, filesToIgnore)) {
                                return false;
                            }
                        } else {
                            copyFile(file, targetFile);
                        }
                    }
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
        copyStream(inputStream, new BufferedOutputStream(new FileOutputStream(targetFile)));
        inputStream.close();
    }

    public static String read(InputStream content, Charset charset) throws IOException {
        InputStreamReader reader = new InputStreamReader(content, charset);
        StringBuilder stringBuffer = new StringBuilder();
        char[] tmp = new char[1024];
        int read;
        while ((read = reader.read(tmp, 0, tmp.length)) != -1) {
            stringBuffer.append(tmp, 0, read);
        }
        return stringBuffer.toString();
    }
}
