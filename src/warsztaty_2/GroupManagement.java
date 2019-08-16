package warsztaty_2;

import java.util.Scanner;

public class GroupManagement {

    public static void main(String[] args) {

        System.out.println("Witaj w GroupManagement.");
        System.out.println();

        groupManagement();

    }

    static void groupManagement() {
        GroupDAO groupsDAO = new GroupDAO();

        for (Group group : groupsDAO.readAllGroups()) {
            System.out.println(group);
        }

        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add - dodanie grupy");
        System.out.println("edit - edycja grupy");
        System.out.println("delete - usunięcie grupy");
        System.out.println("quit - zakończenie programu");

        Scanner scan = new Scanner(System.in);

        String answer = scan.nextLine();

        while (!answer.equals("add") && !answer.equals("edit") && !answer.equals("delete") && !answer.equals("quit")) {
            System.out.println("Niepoprawna komenda. Spróbuj jeszcze raz.");
            answer = scan.nextLine();
        }

        if (answer.equals("add")) {
            System.out.println("Podaj nazwę grupy.");
            String name = scan.nextLine();

            Group newgroup = new Group(name);

            GroupDAO groupDAO = new GroupDAO();

            groupDAO.createGroup(newgroup);

            scan.close();

            groupManagement();

        } else if (answer.equals("edit")) {
            System.out.println("Podaj numer grupy.");
            String id = scan.nextLine();

            while (isNotPositiveNumber(id)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                id = scan.nextLine();
            }
            int idInt = Integer.parseInt(id);

            System.out.println("Podaj nazwę grupy.");
            String name = scan.nextLine();

            Group editgroup = new Group(name);
            editgroup.setId(idInt);

            GroupDAO groupDAO = new GroupDAO();

            groupDAO.createGroup(editgroup);

            scan.close();

            groupManagement();

        } else if (answer.equals("delete")) {
            System.out.println("Podaj numer grupy.");
            String id = scan.nextLine();

            while (isNotPositiveNumber(id)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                id = scan.nextLine();
            }
            int idInt = Integer.parseInt(id);

            GroupDAO groupDAO = new GroupDAO();

            groupDAO.deleteGroupById(idInt);

            scan.close();

            groupManagement();

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
