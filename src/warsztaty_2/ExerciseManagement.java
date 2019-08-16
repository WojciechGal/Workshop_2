package warsztaty_2;

import java.util.Scanner;

public class ExerciseManagement {

    public static void main(String[] args) {

        System.out.println("Witaj w ExerciseManagement.");
        System.out.println();

        exerciseManagement();

    }

    static void exerciseManagement() {
        ExerciseDAO exercisesDAO = new ExerciseDAO();

        for (Exercise exercise : exercisesDAO.readAllExercises()) {
            System.out.println(exercise);
        }

        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add - dodanie zadania");
        System.out.println("edit - edycja zadania");
        System.out.println("delete - usunięcie zadania");
        System.out.println("quit - zakończenie programu");

        Scanner scan = new Scanner(System.in);

        String answer = scan.nextLine();

        while (!answer.equals("add") && !answer.equals("edit") && !answer.equals("delete") && !answer.equals("quit")) {
            System.out.println("Niepoprawna komenda. Spróbuj jeszcze raz.");
            answer = scan.nextLine();
        }

        if (answer.equals("add")) {
            System.out.println("Podaj tytuł zadania.");
            String title = scan.nextLine();
            System.out.println("Podaj opis zadania.");
            String description = scan.nextLine();

            Exercise newexercise = new Exercise(title, description);

            ExerciseDAO exerciseDAO = new ExerciseDAO();

            exerciseDAO.createExercise(newexercise);

            scan.close();

            exerciseManagement();

        } else if (answer.equals("edit")) {
            System.out.println("Podaj numer zadania.");
            String id = scan.nextLine();

            while (isNotPositiveNumber(id)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                id = scan.nextLine();
            }
            int idInt = Integer.parseInt(id);

            System.out.println("Podaj tytuł zadania.");
            String title = scan.nextLine();
            System.out.println("Podaj opis zadania.");
            String description = scan.nextLine();

            Exercise editexercise = new Exercise(title, description);
            editexercise.setId(idInt);

            ExerciseDAO exerciseDAO = new ExerciseDAO();

            exerciseDAO.updateExercise(editexercise);

            scan.close();

            exerciseManagement();

        } else if (answer.equals("delete")) {
            System.out.println("Podaj numer zadania.");
            String id = scan.nextLine();

            while (isNotPositiveNumber(id)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                id = scan.nextLine();
            }
            int idInt = Integer.parseInt(id);

            ExerciseDAO exerciseDAO = new ExerciseDAO();

            exerciseDAO.deleteExerciseById(idInt);

            scan.close();

            exerciseManagement();

        } else if (answer.equals("quit")) {
            System.out.println("Do zobaczenia.");
            scan.close();
        }
    }

    static boolean isNotPositiveNumber(String str) {
        try {
            int x = Integer.parseInt(str);
            if (x > 0) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return true;
        }
    }

}
