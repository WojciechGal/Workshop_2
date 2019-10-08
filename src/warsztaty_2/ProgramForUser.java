package warsztaty_2;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Scanner;

public class ProgramForUser {

    public static void main(String[] args) {

        UsersDAO usersDAO = new UsersDAO();

        Users[] allUsers = usersDAO.readAllUsers();

        int[] idsOfAllUsers = new int[0];

        for (Users us : allUsers) {
            idsOfAllUsers = ArrayUtils.add(idsOfAllUsers, us.getId());
        }

        System.out.println("Witaj użytkowniku. Podaj swój numer.");

        Scanner scan = new Scanner(System.in);

        String idUser = scan.nextLine();

        while (isNotPositiveNumberAndIsNotInSpecialArray(idUser, idsOfAllUsers)) {
            System.out.println("Podałeś niepoprawny numer lub numer użytkownika nieistniejącego. Podaj jeszcze raz.");
            idUser = scan.nextLine();
        }
        int idIntUser = Integer.parseInt(idUser);

        programForUser(idIntUser);

    }

    static void programForUser(int id) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add - dodawanie rozwiązania");
        System.out.println("view - przeglądanie swoich rozwiązań");
        System.out.println("quit - zakończenie programu");

        String answer = scan.nextLine();

        while (!answer.equals("add") && !answer.equals("view") && !answer.equals("quit")) {
            System.out.println("Niepoprawna komenda. Spróbuj jeszcze raz.");
            answer = scan.nextLine();
        }

        if (answer.equals("add")) {

            Solution solution = new Solution();

            Solution[] solutions = solution.findAllByUserId(id);

            ExerciseDAO exerciseDAO = new ExerciseDAO();

            Exercise[] allExercises = exerciseDAO.readAllExercises();

 //           Exercise[] exercisesFromSolution = new Exercise[0];

            for (Solution sol : solutions) {
 //               ArrayUtils.add(exercisesFromSolution, sol.getExercise());
                ArrayUtils.removeElement(allExercises, sol.getExercise());
            }

            int[] idsOfExerciseWithoutSolution = new int[0];

            for (Exercise exe : allExercises) {
                System.out.println(exe);
                idsOfExerciseWithoutSolution = ArrayUtils.add(idsOfExerciseWithoutSolution, exe.getId());
            }

            System.out.println("Podaj numer zadania do którego chcesz dodać rozwiązanie.");
            String exeId = scan.nextLine();

            while (isNotPositiveNumberAndIsNotInSpecialArray2(exeId, idsOfExerciseWithoutSolution)) {
                System.out.println("Podałeś niepoprawny numer lub numer zadania rozwiązanego. Podaj jeszcze raz.");
                exeId = scan.nextLine();
            }

            int idOfExeToBeSolved = Integer.parseInt(exeId);

            System.out.println("Podaj rozwiązanie zadania.");

            String descriptionOfSolution = scan.nextLine();

            UsersDAO usersDAO = new UsersDAO();

            Solution createsolution = new Solution(descriptionOfSolution, exerciseDAO.readExerciseById(idOfExeToBeSolved), usersDAO.readUserById(id));

            SolutionDAO solutionDAO = new SolutionDAO();

            solutionDAO.createSolution(createsolution);

            programForUser(id);

        } else if (answer.equals("view")) {

            Solution solution = new Solution();

            Solution[] solutions = solution.findAllByUserId(id);

            for (Solution sol : solutions) {
                System.out.println(sol);
            }


            programForUser(id);

        } else if (answer.equals("quit")) {
            System.out.println("Do zobaczenia.");
        }

        scan.close();

    }

    static boolean isNotPositiveNumberAndIsNotInSpecialArray(String str, int[]tab) {
        try {
            int x = Integer.parseInt(str);
            if (x > 0 && ArrayUtils.contains(tab, x)) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return true;
        }

    }

    static boolean isNotPositiveNumberAndIsNotInSpecialArray2(String str, int[]tab) {
        try {
            int x = Integer.parseInt(str);
            if (x > 0 && ArrayUtils.contains(tab, x)) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return true;
        }

    }

}
