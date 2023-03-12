// Dhananjay Yelwande
// Mar 12, 2023
// Usage java ReadFile.java marks.txt

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    public static void main(String[] args) {
        // check if filename is provided as command line argument
        if (args.length == 0) {
            System.out.println("No file provided");
            return;
        }

        // check if only one filename is provided as command line argument
        if (args.length != 1) {
            System.out.println("Error in input");
            return;
        }

        String filename = args[0];
        File file = new File(filename);

        // check if the file exists
        if (!file.exists()) {
            System.out.println("No such file");
            return;
        }

        // initialize array to store marks for each student-course pair
        double[][] marks = new double[500][500];
        String[] students = new String[500];
        int[] numCourses = new int[500];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String student = tokens[0].toLowerCase();
                String course = tokens[1];
                double mark = Double.parseDouble(tokens[2]);

                // check if mark is valid
                if (mark < 0 || mark > 100) {
                    System.out.println("Invalid marks");
                    return;
                }

                // find index of student in array
                int index = -1;
                for (int i = 0; i < students.length; i++) {
                    if (students[i] == null) {
                        break;
                    }
                    if (students[i].equals(student)) {
                        index = i;
                        break;
                    }
                    
                }

                // add student to array if not already present
                if (index == -1) {

                    index = findEmptyIndex(students);
                    students[index] = student;
                }

                // add mark to array
                marks[index][numCourses[index]] = mark;
                numCourses[index]++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // find student with highest average mark
        double highestAvg = -1;
        String[] highestStudents = new String[500];
        int numHighest = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {
                break;
            }
            double sum = 0;
            for (int j = 0; j < numCourses[i]; j++) {
                sum += marks[i][j];
            }
            double avg = sum / numCourses[i];
            if (avg > highestAvg) {
                highestAvg = avg;
                highestStudents = new String[500];
                highestStudents[0] = students[i];
                numHighest = 1;
            } else if (avg == highestAvg) {
                highestStudents[numHighest] = students[i];
                numHighest++;
            }
        }

        // print names of highest-scoring students in alphabetical order
        sort(highestStudents, numHighest);
        for (int i = 0; i < numHighest; i++) {
            System.out.println(highestStudents[i]);
        }
    }

 // helper method to find first empty index in array
    private static int findEmptyIndex(String[] arr) {    
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                return i;
            }
        }
        return -1; // array is full
    }

    // helper method to sort array of strings in alphabetical order
    private static void sort(String[] arr, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i].compareTo(arr[j]) > 0) {
                    String temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
