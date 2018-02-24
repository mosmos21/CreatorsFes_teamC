package jp.co.unirita.creatorsfes.teamc.util;

import jp.co.unirita.creatorsfes.teamc.model.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FileUtil {
    public static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String AXIS_PACKAGE_NAME = "jp.co.unirita.creatorsfes.teamc.util.axis";
    private static final String PACKAGE_SEPARATOR = ".";
    private static final String CLASS_SUFFIX = ".class";

    public static Set<String> getClassList(String packageName) throws IOException, URISyntaxException {
        String rootPackageName = packageName.replace(PACKAGE_SEPARATOR, File.separator);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Enumeration<URL> rootUrls = classLoader.getResources(rootPackageName);

        Set<String> classNames = new HashSet<>();
        while (rootUrls.hasMoreElements()) {
            URL rootUrl = rootUrls.nextElement();
            Path rootPath = Paths.get(rootUrl.toURI());

            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    String pathName = path.toString();
                    if (pathName.endsWith(CLASS_SUFFIX)) {
                        int beginIndex = pathName.lastIndexOf(rootPackageName);
                        int endIndex = pathName.lastIndexOf(CLASS_SUFFIX);
                        String className = pathName.substring(beginIndex, endIndex).replace(File.separator, PACKAGE_SEPARATOR);
                        if (!className.equals(AXIS_PACKAGE_NAME + ".Axis")) {
                            classNames.add(className);
                        }
                    }
                    return super.visitFile(path, attrs);
                }
            });
        }
        return classNames;
    }

    public static List<Record> loadRecordList(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        List<Record> records = new ArrayList<>();
        String line =  br.readLine();
        String[] key = line.split(",");
        while((line = br.readLine()) != null) {
            Record record = new Record();
            String[] values = line.split(",");
            if(key.length != values.length) {
                throw new RuntimeException("invalid data");
            }
            for(int idx = 0; idx < key.length; idx++) {
                record.setParam(key[idx], values[idx]);
            }
            records.add(record);
        }
        logger.info("[loadRecordList] Load " + records.size() + " records.");
        return records;
    }
}
