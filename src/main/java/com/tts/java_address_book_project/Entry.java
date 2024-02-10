package com.tts.java_address_book_project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entry {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public static void main(String[] args) {
        System.out.println("\t1) - Add an entry\n");
        System.out.println("1) - Add an entry");
    }

}
