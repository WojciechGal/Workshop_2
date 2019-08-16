package warsztaty_2;

import java.util.Scanner;

public class SolutionToUserAndExerciseManagement {

    public static void main(String[] args) {

        System.out.println("Witaj w SolutionToUserAndExerciseManagement.");
        System.out.println();

        solutionToUserAndExerciseManagement();

    }

    static void solutionToUserAndExerciseManagement() {

        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add - przypisywanie zadań do użytkowników");
        System.out.println("view - przeglądanie rozwiązań danego użytkownika");
        System.out.println("quit - zakończenie programu");

        Scanner scan = new Scanner(System.in);

        String answer = scan.nextLine();

        while (!answer.equals("add") && !answer.equals("view") && !answer.equals("quit")) {
            System.out.println("Niepoprawna komenda. Spróbuj jeszcze raz.");
            answer = scan.nextLine();
        }

        if (answer.equals("add")) {

            UsersDAO usersDAO = new UsersDAO();
            for (Users user : usersDAO.readAllUsers()) {
                System.out.println(user);
            }

            System.out.println("Podaj numer uzytkownika.");

            String idUser = scan.nextLine();

            while (isNotPositiveNumber(idUser)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                idUser = scan.nextLine();
            }
            int idIntUser = Integer.parseInt(idUser);

            ExerciseDAO exerciseDAO = new ExerciseDAO();

            for (Exercise exercise : exerciseDAO.readAllExercises()) {
                System.out.println(exercise);
            }

            System.out.println("Podaj numer zadania.");

            String idExercise = scan.nextLine();

            while (isNotPositiveNumber(idExercise)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                idExercise = scan.nextLine();
            }
            int idIntExercise = Integer.parseInt(idExercise);

            Users user = usersDAO.readUserById(idIntUser);

            Exercise exercise = exerciseDAO.readExerciseById(idIntExercise);

            Solution solution = new Solution(exercise, user);

            SolutionDAO solutionDAO = new SolutionDAO();

            solutionDAO.createSolution(solution);

            scan.close();

            solutionToUserAndExerciseManagement();

        } else if (answer.equals("view")) {

            System.out.println("Podaj numer uzytkownika.");

            String idUser = scan.nextLine();

            while (isNotPositiveNumber(idUser)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                idUser = scan.nextLine();
            }
            int idIntUser = Integer.parseInt(idUser);

            Solution solution = new Solution();

            Solution[] solutions = solution.findAllByUserId(idIntUser);

            for (Solution sol : solutions) {
                System.out.println(sol);
            }

            scan.close();

            solutionToUserAndExerciseManagement();

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
