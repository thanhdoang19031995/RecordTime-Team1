/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerTime;

import javax.microedition.lcdui.DateField;
import java.util.Vector;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.*;

public final class EditTimeform extends Form implements CommandListener {

    Ticker newsTicker = new Ticker("Java J2ME");
    private Display display;
    Command Logout;
    Command Edit;
    Command Back;

    Displayable main;
    int iItemList;
    public String IdUser;
    Vector listTags = new Vector();
    ChoiceGroup listGroup;

    public EditTimeform(String title, String iUd) {

        super(title);
        IdUser = iUd;
        //draw 
        this.drawGUI();
        // a menu with items
        this.showMenu();
        //load data
    }

    public EditTimeform(String title, Displayable Main, String uids) {
        super(title);
        IdUser = uids;
        //draw 
        this.drawGUI();
        // a menu with items
        this.showMenu();
        //load data
        main = Main;
    }

    protected void drawGUI() {
        TextField content = new TextField("Content:", "", 30, TextField.ANY);
        this.append(content);
        //DateField
        DateField date = new DateField("Date", DateField.DATE);
        date.setDate(new java.util.Date());
        this.append(date);

        //beginTime
        DateField beginTime = new DateField("Begin Time", DateField.DATE);
        date.setDate(new java.util.Date());
        this.append(beginTime);

        //endTime
        DateField endTime = new DateField("End Time", DateField.TIME);
        date.setDate(new java.util.Date());
        this.append(endTime);
        //list
        listGroup = new ChoiceGroup("Tag:", Choice.EXCLUSIVE);
        this.append(listGroup);
    }

    public void showMenu() {
        Back = new Command("Back", Command.OK, 2);
        this.addCommand(Back);
        Edit = new Command("Save", Command.OK, 2);
        this.addCommand(Edit);

        this.setCommandListener(this);
    }

    //Action
    public void commandAction(Command c, Displayable d) {
        String label = c.getLabel();
        if (label.equals("Back")) {
            HistoryTimeform f1 = new HistoryTimeform("History", IdUser);
            f1.setTicker(newsTicker);
            f1.setDisplay(this.display);
            this.display.setCurrent(f1);
        }
    }

    public void setDisplay(Display display) {
        this.display = display;
    }
}
