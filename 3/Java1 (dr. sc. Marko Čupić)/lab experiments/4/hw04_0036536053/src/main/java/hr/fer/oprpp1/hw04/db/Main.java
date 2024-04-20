package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * Main program to start simpulation
     * eg. query lastName LIKE "B*" showing jmbag, jmbag, firstname, lastname, jmbag
     */
    public static void main(String[] args) {
        StudentDatabase db = null;
        try{
            db = StudentDatabase.database();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("> ");
        while (sc.hasNextLine()){
            String input = sc.nextLine().strip();
            if (input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            QueryParser parser = new QueryParser(input);
            List<StudentRecord> records = new ArrayList<>();
            if (parser.isDirectQuery()) {
                StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
                System.out.println("Using index for record retrieval.");
                records.add(r);
            } else {
                for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
                    records.add(r);
                }
            }

            String[] columns = parser.columns;
            if (columns == null) {
                columns = new String[] {"jmbag", "firstname", "lastname", "grade"};
            } 
            RecordFormatter.showing(records, columns);
            System.out.println("Records selected: " + records.size());
            System.out.print("> ");

        }
        sc.close();
    }


    /**
     * class that creates 
     */
    public static class RecordFormatter {

        public static void showing(List<StudentRecord> records, String[] columns) {
            if(records.size() == 0) return;
            List<String> list = new ArrayList<String>();
            String border = "";
            for (String atr : columns) {
                int repetition = 0;
                if (atr.equals("jmbag")) 
                    repetition = records.stream().mapToInt(r -> r.getJmbag().length()).max().getAsInt();
                else if (atr.equals("firstname"))
                    repetition = records.stream().mapToInt(r -> r.getFirstName().length()).max().getAsInt();
                else if (atr.equals("lastname"))
                    repetition = records.stream().mapToInt(r -> r.getLastName().length()).max().getAsInt();
                else if (atr.equals("grade"))
                    repetition = records.stream().mapToInt(r -> r.getFinalGrade().length()).max().getAsInt();
                border += String.format("+%s", "=".repeat(repetition + 2));
            }
            border += "+";
            list.add(border);
            for (StudentRecord r : records) {
                String output = "";
                for (String s : columns) {
                    if (s.equals("jmbag")) {
                        int repetition = records.stream().mapToInt(rec -> rec.getJmbag().length()).max().getAsInt();
                        output += String.format("| %s ", r.getJmbag() + " ".repeat(repetition - r.getJmbag().length()));
                    }
                    else if (s.equals("firstname")) {
                        int repetition = records.stream().mapToInt(rec -> rec.getFirstName().length()).max().getAsInt();
                        output += String.format("| %s ", r.getFirstName() + " ".repeat(repetition - r.getFirstName().length()));
                    }
                    else if (s.equals("lastname")) {
                        int repetition = records.stream().mapToInt(rec -> rec.getLastName().length()).max().getAsInt();
                        output += String.format("| %s ", r.getLastName() + " ".repeat(repetition - r.getLastName().length()));
                    }
                    else if (s.equals("grade")) {
                        int repetition = records.stream().mapToInt(rec -> rec.getFinalGrade().length()).max().getAsInt();
                        output += String.format("| %s ", r.getFinalGrade() + " ".repeat(repetition - r.getFinalGrade().length()));
                    }
                }
                output += "|";
                list.add(output);
            }
            list.add(border);
            list.forEach(System.out::println);
        }
    }

}
