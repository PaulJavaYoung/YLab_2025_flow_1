package me.oldboy;

import me.oldboy.menu.MainMenu;

import java.util.Scanner;

public class FinTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainMenu.getInstance();
        MainMenu.startMainMenu(scanner);

        scanner.close();
    }
}
