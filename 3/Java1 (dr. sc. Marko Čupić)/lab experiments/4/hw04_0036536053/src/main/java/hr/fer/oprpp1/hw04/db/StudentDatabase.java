package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * functional interface [StudentDatabase]
 * 
 * @author Jure Rajcic
 */
public class StudentDatabase {

    ArrayList<StudentRecord> list; // list of student records from database.txt
    HashMap<String, Integer> map; // map of Key: student records, Value: index in list

    /**
     * factory method @return and create StudentDatabaes from database.txt file
     */
    public static StudentDatabase database() {
        StudentDatabase sd = null;
        try {
            List<String> database = Files.readAllLines(Path.of("src/main/java/hr/fer/oprpp1/hw04/db/database.txt"),
                    StandardCharsets.UTF_8);
            sd = new StudentDatabase(database); // getSomehowOneRecord();
        } catch (IOException e) {
        }
        return sd;
    }

    /**
     * private constructor used from factry method
     * fills map and list from Strings ( @param database )
     * 
     * @throws IllegalArgumentException if database has more students with same
     *                                  jmbag or invalid grade
     */
    private StudentDatabase(List<String> database) {
        map = new HashMap<String, Integer>();
        int index = 0;
        list = new ArrayList<StudentRecord>();
        for (String line : database) {
            String[] atributes = line.split("\t");
            if (map.containsKey(atributes[0]))
                throw new IllegalArgumentException("multiple students have same jmbag [" + atributes[0] + "]");
            else
                map.put(atributes[0], index++);
            switch (atributes[3]) {
                case "1", "2", "3", "4", "5" -> {
                }
                default ->
                    throw new IllegalArgumentException(
                            "student [" + atributes[0] + "] cant have [" + atributes[3] + "] for grade");
            }
            list.add(new StudentRecord(atributes));
        }
    }

    /**
     * @returns student record for @param jmbag
     */
    public StudentRecord forJMBAG(String jmbag) {
        if (map.containsKey(jmbag))
            return list.get(map.get(jmbag));
        return null;
    }

    /**
     * @return student records from database that satsfy @param filter conditions
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> filterList = new ArrayList<>();
        for (StudentRecord sr : this.list)
            if (filter.accepts(sr))
                filterList.add(sr);
        return filterList;
    }

}
