package cli;

import store.storeEntity.Order;

import java.io.IOException;
import java.time.LocalDate;


public class StoreMenu extends Menu {

    private String command;
    public StoreMenu(CliService cliService) {
        super(cliService);
    }

    @Override
    public void init() {
        System.out.println(
                "Press:\n" +
                        "1 - place order\n" +
                        "2 - find purchase by ID\n" +
                        "3 - delete purchase by ID\n" +
                        "4 - get inventory by status\n" +
                        "5 - return to main menu");


        while (true) {
            command = cliService.getScanner().nextLine();
            switch (command) {
                case "1":
                    placeOrder();
                    break;
                case "2":
                    findOrderById();
                    break;
                case "3":
                    deleteOrderById();
                    break;
                case "4":
                    getInventory();
                    break;
                case "5":
                   returnToMainMenu();
                    break;

                default:
                    unknownCommand(command);
            }
        }
    }

    private void getInventory() {
        try {
            System.out.println(cliService.getStoreHttpService().getInventory());
            Thread.sleep(2000);
            returnToStoreMenu();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void returnToStoreMenu() {
        cliService.setMenu(new StoreMenu(cliService));

    }

    private void deleteOrderById() {
        while (true) {
            try {
                System.out.println("Enter \"ok\" to return on the main menu");
                System.out.println("Enter id to delete order");
                String command = cliService.getScanner().nextLine();
                if (command.equals("ok")) {
                    returnToStoreMenu();
                } else {
                    try {
                        if (cliService.getStoreHttpService().getOrderById(Integer.parseInt(command)) == null) {
                            continue;
                        } else
                            System.out.println("Result:" + cliService.getStoreHttpService().delete(Integer.parseInt(command)) + "\n");
                    } catch (NumberFormatException ex) {
                        unknownCommand(command);

                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void findOrderById() {
        while (true) {
            try {
                System.out.println("Enter \"ok\" to return on the main menu");
                System.out.println("Enter id to get order");
                String command = cliService.getScanner().nextLine();
                if (command.equals("ok")) {
                    returnToStoreMenu();
                } else {
                    try {
                        if (cliService.getStoreHttpService().getOrderById(Integer.parseInt(command)) == null) {
                            continue;
                        } else
                            System.out.println("Result:" + cliService.getStoreHttpService().getOrderById(Integer.parseInt(command)) + "\n");
                    } catch (NumberFormatException ex) {
                        unknownCommand(command);

                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
//return empty object fix data should be via local date
    private void placeOrder() {
        while (true) {
            Order order;
            long id = 0;
            long petId = 0;
            int quantity= 0;
            int day = 0;
            int month= 0;
            int year=0;
            LocalDate shipDate = null;
            Order.Status status = null;
           boolean complete = true;

            try {
                while (id == 0) {
                    System.out.println("Enter order id:");

                    try {
                        id = Integer.parseInt(cliService.getScanner().nextLine());
                    } catch (Exception ex) {
                        System.out.println("enter correct id");
                    }
                }
                while (petId == 0) {
                    System.out.println("Enter pet id:");
                    try {
                        petId = Integer.parseInt(cliService.getScanner().nextLine());
                    } catch (Exception ex) {
                        System.out.println("enter correct id");
                    }
                }

                while (quantity == 0) {
                    System.out.println("Enter qty id:");
                    try {
                        quantity = Integer.parseInt(cliService.getScanner().nextLine());
                    } catch (Exception ex) {
                        System.out.println("enter correct id");
                    }
                }

                   while (day <= 0 || day > 31) {
                       System.out.println("Enter ship day:");
                       try {
                           day = Integer.parseInt(cliService.getScanner().nextLine());
                       } catch (Exception ex) {
                           System.out.println("enter correct day 1-31");
                       }
                   }

                   while (month <= 0 || month > 12) {
                       System.out.println("Enter month day:");
                       try {
                           month = Integer.parseInt(cliService.getScanner().nextLine());
                       } catch (Exception ex) {
                           System.out.println("enter correct month 1-12");
                       }
                   }
                   while (year < 2022 || year > 2025) {
                       System.out.println("Enter year day:");
                       try {
                           year = Integer.parseInt(cliService.getScanner().nextLine());
                       } catch (Exception ex) {
                           System.out.println("enter correct year 2022-2025");
                       }
                   }
                // TODO to fix: null data if e.x input 31 of February
                   try {

                       shipDate = LocalDate.of(year, month, day);
                   } catch (Exception ex) {
                       System.out.println("enter correct date");
                   }




                System.out.println("Enter status (placed, approved, delivered):");
                while (status == null) {
                    String statusTry = cliService.getScanner().nextLine();
                    try {
                        status = Order.Status.valueOf(statusTry);
                    } catch (Exception ex) {
                        System.out.println("enter correct status");
                    }
                }
                order = new Order(id,petId,quantity,String.valueOf(shipDate), status, true);
                System.out.println("Result:" + cliService.getStoreHttpService().placeOrder(order) + "\n");
                Thread.sleep(3000);
                returnToStoreMenu();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}
