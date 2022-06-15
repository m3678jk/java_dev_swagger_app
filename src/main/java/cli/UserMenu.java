package cli;

import user.userEntity.User;
import java.io.IOException;


public class UserMenu extends Menu {
    public UserMenu(CliService cliService) {
        super(cliService);
    }

    private String command;

    @Override
    public void init() {
        System.out.println(
                "Press:\n" +
                        "1 - create list of users \n" +
                        "2 - get user by username\n" +
                        "3 - update user\n" +
                        "4 - delete\n" +
                        "5 - login user\n" +
                        "6 - logout user\n" +
                        "7 - create new user\n" +
                        "8 - return to main menu");


        while (true) {
            command = cliService.getScanner().nextLine();
            switch (command) {
                case "1":
                    createListOfUsers();
                    break;
                case "2":
                    getUserByUsername();
                    break;
                case "3":
                    updateUser();
                    break;
                case "4":
                    delete();
                    break;
                case "5":
                    login();

                    break;
                case "6":
                    logout();
                    break;
                case "7":
                    createNewUser();
                    break;
                case "8":
                    returnToMainMenu();
                    break;

                default:
                    unknownCommand(command);
            }
        }
    }

    private void updateUser() {
        while (true) {
            User user;
            int id = 0;
            String username;
            String firstName = "";
            String lastName = "";
            String email = "";
            String password;
            String phone;
            int userStatus = 0;

            try {
                while (id == 0) {
                    System.out.println("Enter user id:");

                    try {
                        id = Integer.parseInt(cliService.getScanner().nextLine());
                    } catch (Exception ex) {
                        System.out.println("enter correct id");
                    }
                }

                System.out.println("Enter username:");
                username = cliService.getScanner().nextLine();
                System.out.println("Enter first name:");
                firstName = cliService.getScanner().nextLine();
                System.out.println("Enter last name:");
                lastName = cliService.getScanner().nextLine();
                System.out.println("Enter email:");
                email = cliService.getScanner().nextLine();
                System.out.println("Enter password:");
                password = cliService.getScanner().nextLine();
                System.out.println("Enter phone:");
                phone = cliService.getScanner().nextLine();

                while (userStatus == 0) {
                    System.out.println("Enter user statue id:");
                    try {
                        userStatus = Integer.parseInt(cliService.getScanner().nextLine());
                    } catch (Exception ex) {
                        System.out.println("enter correct id");
                    }
                }

                user = new User(id, username, firstName, lastName, email, password, phone, userStatus);
                System.out.println("Result:" + cliService.getUserHttpService().updateUser(username, user) + "\n");
                Thread.sleep(5000);
                returnToUserMenu();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    private void createNewUser() {
        while (true) {
            User user;
            int id = 0;
            String username;
            String firstName = "";
            String lastName = "";
            String email = "";
            String password;
            String phone;
            int userStatus = 0;

            try {
                while (id == 0) {
                    System.out.println("Enter user id:");

                    try {
                        id = Integer.parseInt(cliService.getScanner().nextLine());
                    } catch (Exception ex) {
                        System.out.println("enter correct id");
                    }
                }

                System.out.println("Enter username:");
                username = cliService.getScanner().nextLine();
                System.out.println("Enter first name:");
                firstName = cliService.getScanner().nextLine();
                System.out.println("Enter last name:");
                lastName = cliService.getScanner().nextLine();
                System.out.println("Enter email:");
                email = cliService.getScanner().nextLine();
                System.out.println("Enter password:");
                password = cliService.getScanner().nextLine();
                System.out.println("Enter phone:");
                phone = cliService.getScanner().nextLine();

                while (userStatus == 0) {
                    System.out.println("Enter user statue id:");
                    try {
                        userStatus = Integer.parseInt(cliService.getScanner().nextLine());
                    } catch (Exception ex) {
                        System.out.println("enter correct id");
                    }
                }

                user = new User(id, username, firstName, lastName, email, password, phone, userStatus);
                System.out.println("Result:" + cliService.getUserHttpService().createNewUser(user) + "\n");
                Thread.sleep(5000);
                returnToUserMenu();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void logout() {
        try {
            int statusCode = cliService.getUserHttpService().logout();
            if (statusCode == 200) {
                System.out.println("User is logged out");
                Thread.sleep(3000);
                returnToUserMenu();
            } else {
                System.out.println("User was not logged in");
            }

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    private void login() {
        while (true) {
            String username = null;
            String password = null;
            try {

                System.out.println("Enter username to login");
                username = cliService.getScanner().nextLine();
                System.out.println("Enter password to login");
                password = cliService.getScanner().nextLine();

                int statusResponse = cliService.getUserHttpService().login(username, password);
                if (statusResponse == 200) {
                    System.out.println("User logged in");
                    Thread.sleep(3000);
                } else {
                    System.out.println("username or password are incorrect");
                }
                returnToUserMenu();

            } catch (NumberFormatException | InterruptedException | IOException ex) {
                unknownCommand(command);

            }
        }


    }

    private void delete() {
        while (true) {
            try {
                System.out.println("Enter \"ok\" to return on the main menu");
                System.out.println("Enter username to delete user");
                String command = cliService.getScanner().nextLine();
                if (command.equals("ok")) {
                    returnToUserMenu();
                } else {
                    try {
                        if (cliService.getUserHttpService().getUserByName(command) == null) {
                            continue;
                        } else
                            System.out.println("Result:" + cliService.getUserHttpService().delete(command) + "\n");
                    } catch (NumberFormatException ex) {
                        unknownCommand(command);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void getUserByUsername() {
        while (true) {
            try {
                System.out.println("Enter \"ok\" to return on the main menu");
                System.out.println("Enter name to get user");
                String command = cliService.getScanner().nextLine();
                if (command.equals("ok")) {
                    returnToUserMenu();
                } else {
                    try {
                        if (cliService.getUserHttpService().getUserByName(command) == null) {
                            continue;
                        } else {
                            System.out.println("Result:" + cliService.getUserHttpService().getUserByName(command) + "\n");
                        }
                    } catch (Exception exception) {
                        unknownCommand(command);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void returnToUserMenu() {
        cliService.setMenu(new UserMenu(cliService));
    }

    private void createListOfUsers() {
        System.out.println("not implemented for CLI\n" +
                "to check this request pls go to user/UserTest class");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        returnToMainMenu();
    }
}
