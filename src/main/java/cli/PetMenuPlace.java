package cli;

import pet.petEntity.Category;
import pet.petEntity.Pet;
import pet.petEntity.Tag;

import java.util.ArrayList;
import java.util.List;

public class PetMenuPlace extends MenuPlace {
    public PetMenuPlace(CliService cliService) {
        super(cliService);
    }

    private String command;

    @Override
    public void init() {
        System.out.println(
                "Press:\n" +
                        "1 - uploadImage\n" +
                        "2 - add new pet\n" +
                        "3 - update an exist pet\n" +
                        "4 - find pets by status\n" +
                        "5 - find pet by ID\n" +
                        "6 - update a pet with the form\n" +
                        "7 - delete\n" +
                        "8 - exit");


        while (true) {
            command = cliService.getScanner().nextLine();
            switch (command) {
                case "1":
                    uploadImage();
                    break;
                case "2":
                    addNewPet();
                    break;
                case "3":
                    updateExistPet();
                    break;
                case "4":
                    findPetByStatus();
                    break;
                case "5":
                    findPetById();

                    break;
                case "6":
                    updatePetWithForm();
                    break;
                case "7":
                    delete();
                    break;
                case "8":
                    System.exit(0);
                    break;

                default:
                    unknownCommand(command);
            }
        }
    }

    private void delete() {
    }

    private void updatePetWithForm() {
    }

    private void findPetById() {
        while (true) {
            try {
                System.out.println("Enter \"ok\" to return on the main menu");
                System.out.println("Enter id to get pet");
                String command = cliService.getScanner().nextLine();
                if (command.equals("ok")) {
                    returnToPetMenu();
                } else {
                    try {
                        if (cliService.getPetHttpService().getPetById(Integer.parseInt(command)) == null) {
                            continue;
                        } else
                            System.out.println("Result:" + cliService.getPetHttpService().getPetById(Integer.parseInt(command)) + "\n");
                    } catch (NumberFormatException ex) {
                        unknownCommand(command);

                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }


    private void findPetByStatus() {
    }

    private void updateExistPet() {
    }

    private void addNewPet() {
        while (true) {
            Pet pet;
            int id = 0;
            int categoryId = 0;
            String categoryName;
            String petName;
            List<String> photoUrls;
            int tagId = 0;
            String tagName;
            Pet.Status status = null;

            try {
                while (id == 0) {
                    System.out.println("Enter pet id:");

                    try {
                        id = Integer.parseInt(cliService.getScanner().nextLine());
                    } catch (Exception ex) {
                        System.out.println("enter correct id");
                    }
                }
                while (categoryId == 0) {
                    System.out.println("Enter category id:");
                    try {
                        categoryId = Integer.parseInt(cliService.getScanner().nextLine());
                    } catch (Exception ex) {
                        System.out.println("enter correct id");
                    }
                }
                System.out.println("Enter category name:");
                categoryName = cliService.getScanner().nextLine();
                System.out.println("Enter pet name:");
                petName = cliService.getScanner().nextLine();
                System.out.println("Enter photoUrls:");
                photoUrls = new ArrayList<String>();
                photoUrls.add(cliService.getScanner().nextLine());

                while (tagId == 0) {
                    System.out.println("Enter tag id:");
                    try {
                        tagId = Integer.parseInt(cliService.getScanner().nextLine());
                    } catch (Exception ex) {
                        System.out.println("enter correct id");
                    }
                }
                System.out.println("Enter tag name:");
                tagName = cliService.getScanner().nextLine();
                System.out.println("Enter status (available, pending, sold):");
                while (status == null) {
                    String statusTry = cliService.getScanner().nextLine();
                    try {
                        status = Pet.Status.valueOf(statusTry);
                    } catch (Exception ex) {
                        System.out.println("enter correct status");
                    }
                }
                pet = new Pet(id,
                        new Category(categoryId, categoryName), petName, photoUrls,
                        new ArrayList<Tag>(List.of(new Tag(tagId, tagName))), status);
                System.out.println("Result:" + cliService.getPetHttpService().createNewPet(pet) + "\n");
                Thread.sleep(5000);
                returnToPetMenu();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }


    }

    private void uploadImage() {
    }

    private void returnToPetMenu() {
        cliService.setMenu(new PetMenuPlace(cliService));

    }
}



