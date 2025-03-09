package me.oldboy.menu.submenu;

import me.oldboy.controller.AdminController;

import java.util.Scanner;

public class AdminMenu {

    private AdminController adminController = AdminController.getInstance();

    public void viewAdminMenu(Scanner scanner){
        adminController.viewUserState(scanner);
    }
}
