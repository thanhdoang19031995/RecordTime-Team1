/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerTime;


import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.*;


public final class RecordTimeform extends Form implements CommandListener {

    Ticker newsTicker = new Ticker("Java J2ME");
    private Display display;
    Command Logout;
    Displayable main;
    

    public RecordTimeform(String title) {
        super(title);
        //draw 
        this.drawGUI();
        // a menu with items
        this.showMenu();
        //load data
    }

    public RecordTimeform(String title, Displayable Main) {
        super(title);
        //draw 
        this.drawGUI();
        // a menu with items
        this.showMenu();
        //load data
        main = Main;
    }

    protected void drawGUI() {
        
    }

    public void showMenu() {

        
    }
    
    //Action
    public void commandAction(Command c, Displayable d) {
        String label = c.getLabel();
        if (label.equals("Logout")) {
            this.display.setCurrent(main);
        } 
    }

    void setDisplay(Display display) {
        this.display = display;
    }
}
