package com.tts.java_address_book_project;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
            System.out.println("**************************");
            System.out.println("*** Welcome to Address Book app menu ***");
            System.out.println("\t1) - Add an entry\n");
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
                System.out.println("The address list is Empty, please add entry first!");
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
                    break;

            }
        }



    }

    public static Entry addEntry(){
        String firstName;
        String lastName;
        String phone;
        String email;


        System.out.println("First Name: ");
        firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        lastName = scanner.nextLine();
        System.out.println("Phone Number: ");
        phone = scanner.nextLine();
        System.out.println("Email Address: ");
        email = scanner.nextLine();

//        scanner.next
        Entry entry = new Entry();
        entry.setEmail(email);
        entry.setLastName(lastName);
        entry.setFirstName(firstName);
        entry.setPhone(phone);

        for (Entry address : addresses) {
            if (address.getEmail().equalsIgnoreCase(entry.getEmail())) {
                System.out.println(address);
                System.out.println("The entry email shall not be duplicate");
                return null;
            }
        }
        addresses.add(entry);
        System.out.println("Added new entry!");
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
        String input;

        while(number == 0){
            System.out.println(prompt);
            input = scanner.nextLine();

            if(input.length() != 0){
                System.out.println(input);
                try{
                    number = Integer.parseInt(input);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Please enter a valid number from 1 - 6");
                }

            }
        }
        return number;
    }


    public static Entry search(String type, String input){
        Optional<Entry> optionalEntry;
        if(type.equalsIgnoreCase("firstName")){
            optionalEntry = addresses.stream().filter((a) -> a.getFirstName().equalsIgnoreCase(input)).findFirst();
        }else if(type.equalsIgnoreCase("lastName")){
            optionalEntry = addresses.stream().filter(a -> a.getLastName().equalsIgnoreCase(input)).findFirst();
        }else if(type.equalsIgnoreCase("phone") || type.equalsIgnoreCase("phone number")){
            optionalEntry = addresses.stream().filter(a -> a.getPhone().equalsIgnoreCase(input)).findFirst();
        }else if(type.equalsIgnoreCase(("email"))){
            optionalEntry = addresses.stream().filter(a -> a.getEmail().equalsIgnoreCase(input)).findFirst();
        }else{
            System.out.println("Your input type shall be within('firstName','lastName','phone' and 'email')");
            return null;
        }

        if(optionalEntry.isPresent()){
            return optionalEntry.get();
        }
        try {
            throw new UserPrincipalNotFoundException("the entry with given type and input is not found");
        } catch (UserPrincipalNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public static void printAddressBook(){
        System.out.println(addresses);
    }

    public static Entry removeEntry(){
        System.out.println("Enter an entry's email to remove:");
        String email = scanner.nextLine();

        for (Entry entry : addresses) {
            if(entry.getEmail().equalsIgnoreCase(email)){
                addresses.remove(entry);
                System.out.println("Deleted the following entry:");
                printEntry(entry);

            }
        }
        try {
            throw new UserPrincipalNotFoundException("The required entry is not found in the book with given email");
        } catch (UserPrincipalNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void printEntry(Entry entry){
        System.out.println("**********");
        System.out.println(entry);
        System.out.println("**********");
    }



}
