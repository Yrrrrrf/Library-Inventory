package model.db;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.library.Inventory;

public class DBAuxiliarMethods {

    // Inventory Table
    public static JScrollPane showInventory() {
        JTable inventoryTable = Inventory.getBooksListTable();
        inventoryTable.setRowHeight(32);
        JScrollPane scrollPane = new JScrollPane(inventoryTable); 
        scrollPane.setBounds(0, 0, 520, 360);
        return scrollPane;
    }


    
}
