package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * Main program to start simpulation
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
            List<String> output = RecordFormatter.format(records);
            if(output.size() > 2)
                output.forEach(System.out::println);
            System.out.println("Records selected: " + records.size());
            System.out.print("> ");

        }
        sc.close();
    }


    /**
     * class that creates 
     */
    public static class RecordFormatter {
        public static List<String> format(List<StudentRecord> records) {
            int jmbag = 0, lastName = 0, firstName = 0, grade = 0;
            for (StudentRecord r : records) {
                if (r.getJmbag().length() > jmbag)
                    jmbag = r.getJmbag().length();
                if (r.getFirstName().length() > firstName)
                    firstName = r.getFirstName().length();
                if (r.getLastName().length() > lastName)
                    lastName = r.getLastName().length();
                if (r.getFinalGrade().length() > grade)
                    grade = r.getFinalGrade().length();
            }
            jmbag += 2;
            firstName += 2;
            lastName += 2;
            grade += 2;

            List<String> list = new ArrayList<String>();
            String border = String.format("+%s+%s+%s+%s+", "=".repeat(jmbag), "=".repeat(firstName),
                    "=".repeat(lastName), "=".repeat(grade));
            list.add(border);
            for (StudentRecord r : records) {
                String stringJmbag = String.format("%1$" + (jmbag - 2) + "s", r.getJmbag());
                String stringLastNaem = String.format("%1$" + (lastName - 2) + "s", r.getLastName());
                String StringFirstName = String.format("%1$" + (firstName - 2) + "s", r.getFirstName());
                String stringGrade = String.format("%1$" + (grade - 2) + "s", r.getFinalGrade());
                String row = String.format("| %s | %s | %s | %s |", stringJmbag, stringLastNaem, StringFirstName,
                        stringGrade);
                list.add(row);
            }
            list.add(border);
            if(records.size() == 2) // 2 borders
                list.clear();
            return list;
        }
    }

}
