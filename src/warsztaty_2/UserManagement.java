package warsztaty_2;

import java.util.Scanner;

public class UserManagement {

    public static void main(String[] args) {

        System.out.println("Witaj w UserManagement.");
        System.out.println();

        userManagement();

    }

    static void userManagement() {
        UsersDAO userDAO = new UsersDAO();

        for (Users user : userDAO.readAllUsers()) {
            System.out.println(user);
        }

        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add - dodanie użytkownika");
        System.out.println("edit - edycja użytkownika");
        System.out.println("delete - usunięcie użytkownika");
        System.out.println("quit - zakończenie programu");

        Scanner scan = new Scanner(System.in);

        String answer = scan.nextLine();

        while (!answer.equals("add") && !answer.equals("edit") && !answer.equals("delete") && !answer.equals("quit")) {
            System.out.println("Niepoprawna komenda. Spróbuj jeszcze raz.");
            answer = scan.nextLine();
        }

        if (answer.equals("add")) {
            System.out.println("Podaj imię użytkownika.");
            String name = scan.nextLine();
            System.out.println("Podaj email użytkownika.");
            String email = scan.nextLine();
            System.out.println("Podaj hasło użytkownika.");
            String password = scan.nextLine();

            System.out.println("Podaj numer grupy.");
            String groupId = scan.nextLine();
            while (isNotPositiveNumber(groupId)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                groupId = scan.nextLine();
            }
            int groupIdInt = Integer.parseInt(groupId);

            GroupDAO groupDAO = new GroupDAO();

            Group group = groupDAO.readGroupById(groupIdInt);

            Users newuser = new Users(name, email, password, group);

            UsersDAO usersDAO = new UsersDAO();

            usersDAO.createUser(newuser);



            userManagement();

        } else if (answer.equals("edit")) {
            System.out.println("Podaj numer użytkownika.");
            String id = scan.nextLine();

            while (isNotPositiveNumber(id)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                id = scan.nextLine();
            }
            int idInt = Integer.parseInt(id);

            System.out.println("Podaj imię użytkownika.");
            String name = scan.nextLine();
            System.out.println("Podaj email użytkownika.");
            String email = scan.nextLine();
            System.out.println("Podaj hasło użytkownika.");
            String password = scan.nextLine();

            System.out.println("Podaj numer grupy.");
            String groupId = scan.nextLine();
            while (isNotPositiveNumber(groupId)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                groupId = scan.nextLine();
            }
            int groupIdInt = Integer.parseInt(groupId);

            GroupDAO groupDAO = new GroupDAO();

            Group group = groupDAO.readGroupById(groupIdInt);

            Users edituser = new Users(name, email, password, group);
            edituser.setId(idInt);

            UsersDAO usersDAO = new UsersDAO();

            usersDAO.updateUser(edituser);



            userManagement();

        } else if (answer.equals("delete")) {
            System.out.println("Podaj numer użytkownika.");
            String id = scan.nextLine();

            while (isNotPositiveNumber(id)) {
                System.out.println("Niepoprawny numer. Podaj jeszcze raz.");
                id = scan.nextLine();
            }
            int idInt = Integer.parseInt(id);

            UsersDAO usersDAO = new UsersDAO();

            usersDAO.deleteUserById(idInt);


            userManagement();

        } else if (answer.equals("quit")) {
            System.out.println("Do zobaczenia.");
        }

        scan.close();
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
