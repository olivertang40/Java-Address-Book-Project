package com.tts.java_address_book_project;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddressBook {

    private static List<Entry> addresses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean quit = false;

    public static void main(String[] args) {
        Entry anderson = new Entry("Anderson", "Ivan", "xxx-xxxx-xxxx", "AndersonIvan@foo.bar");
        System.out.println();
        int option;

        while(!quit){
            //Display Menu options
            System.out.println("***************************************");
            System.out.println("*** Welcome to Address Book app menu ***\n");
            System.out.println("\t1) Add an entry\n");
            System.out.println("\t2) Remove an entry\n");
            System.out.println("\t3) Search for a specific entry\n");
            System.out.println("\t4) Print Address Book\n");
            System.out.println("\t5) Delete Book\n");
            System.out.println("\t6) Quit\n");

            //quit the program
            option = getValidNumber("Please choose what you'd like to do with the database:");
            if(option == 6){
                return;
            }

            //validate user input
            if(option < 1 || option > 6){
                System.out.println("Invalid option number");
                continue;
            }

            if(addresses.size() == 0 && option > 1){
                System.out.println("Address book is Empty, please add entry first!");
                continue;
            }

            switch (option){
                case 1:
                    addEntry();
                    break;
                case 2:
                    removeEntry();
                    break;
                case 3:
                    search();
                    break;
                case 4:
                    printAddressBook();
                    break;
                case 5:
                    clear();
                    break;
                default:
                    quit = true;
                    System.out.println("Exiting the application");
                    break;

            }
        }



    }

    public static Entry addEntry(){
        String firstName;
        String lastName;
        String phone;
        String email;


        System.out.print("First Name: ");
        firstName = scanner.nextLine();
        System.out.print("\nLast Name: ");
        lastName = scanner.nextLine();
        System.out.print("\nPhone Number: ");
        phone = scanner.nextLine();
        System.out.print("\nEmail Address: ");
        email = scanner.nextLine();
        System.out.println();

//        scanner.next
        Entry entry = new Entry();
        entry.setEmail(email);
        entry.setLastName(lastName.toLowerCase());
        entry.setFirstName(firstName.toLowerCase());
        entry.setPhone(phone);

        for (Entry address : addresses) {
            if (address.getEmail().equalsIgnoreCase(entry.getEmail())) {
                System.out.println(address);
                System.out.println("The entry email shall not be duplicate");
                return null;
            }
        }
        addresses.add(entry);
        System.out.println("Added new entry!\n");
        return entry;
    }


    public static void clear(){
        System.out.println("Warning! You are going to delete the whole address book. Do you confirm you want to continue? (Y/N)");
        String input = scanner.nextLine();
        if(input.toLowerCase().startsWith("y")){
            addresses.clear();
            System.out.println("Address book cleared");
        }

    }

    public static int getValidNumber(String prompt){
        int number = 0;

        while(number == 0){
            System.out.println(prompt);
            String input = scanner.nextLine();

            if(input.length() != 0){
                try{
                    number = Integer.parseInt(input);
                }catch(Exception e){
                    System.out.println("Please enter a valid number within range");
                }

            }
        }
        return number;
    }


    public static void search(){
        System.out.println("1) First Name\n");
        System.out.println("2) Last Name\n");
        System.out.println("3) Phone Number\n");
        System.out.println("4) Email Address\n");

        int type = 0;
        while(type < 1 || type > 4){
            type = getValidNumber("Choose a search type (1-4): ");
        }

        System.out.print("Enter your search: ");
        String input = scanner.nextLine();

        List<Entry> res;

        if(type == 1){
            res = addresses.stream().filter((a) -> a.getFirstName().contains(input.toLowerCase())).collect(Collectors.toList());
        }else if(type == 2){
            res = addresses.stream().filter(a -> a.getLastName().contains(input.toLowerCase())).collect(Collectors.toList());
        }else if(type == 3){
            res = addresses.stream().filter(a -> a.getPhone().equalsIgnoreCase(input)).collect(Collectors.toList());
        }else if(type == 4){
            res = addresses.stream().filter(a -> a.getEmail().equalsIgnoreCase(input)).collect(Collectors.toList());
        }else{
            System.out.println("\nYour search type is invalid");
            return;
        }

        if(res.size() == 0){
            System.out.println("No results found!");
            return;
        }

        for (Entry entry : res) {
            printEntry(entry);
        }

    }

    public static void printAddressBook(){
        addresses.stream().forEach(AddressBook::printEntry);
    }

    public static void removeEntry(){
        System.out.println("Enter an entry's email to remove:");
        String email = scanner.nextLine();
        Entry found = null;

        for (Entry entry : addresses) {
            if(entry.getEmail().equalsIgnoreCase(email)){
                found = entry;
                break;
            }
        }

        if(found != null){
            addresses.remove(found);
            System.out.println("Deleted the following entry:\n");
            printEntry(found);
            return ;
        }

        try {
            throw new UserPrincipalNotFoundException("The required entry is not found in the book with given email");
        } catch (UserPrincipalNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printEntry(Entry entry){
        System.out.println("**********");
        System.out.println("First Name: " + entry.getFirstName());
        System.out.println("Last Name: " + entry.getLastName());
        System.out.println("Phone Number: " + entry.getPhone());
        System.out.println("Email: " + entry.getEmail());
        System.out.println("**********");
        System.out.println();
    }



}
