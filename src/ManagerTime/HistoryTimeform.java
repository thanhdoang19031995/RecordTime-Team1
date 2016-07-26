/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerTime;

import ManagerTime.RecordTimeform;

import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.*;

/**
 *
 * @author BaoNguyen
 */
public class HistoryTimeform extends Form implements CommandListener {

    Ticker newsTicker = new Ticker("Java J2ME");
    private Display display;
    Form form;
    Command comBack;
    Command comAdd;
    Command comEdit;
    Command comSave;
    Command comDelete;
    ChoiceGroup listGroup;
    //  BusManageTag tagBus = new BusManageTag()
    int iItemList;
    public String IdUser;
    public String IdTag;
    Vector listTags = new Vector();
    String currentTagIdselected = null;

    public HistoryTimeform(String title, String uids) {
        super(title);
        showMenu();
        this.IdUser = uids;
    }

    private void showMenu() {
        listTags.removeAllElements();
        form = this;
        listGroup = new ChoiceGroup("List Record Time:", Choice.EXCLUSIVE);
//        comAdd = new Command("Add", Command.OK, 2);
        comBack = new Command("Back", Command.OK, 2);
        comEdit = new Command("Edit", Command.OK, 1);
        comDelete = new Command("Delete", Command.OK, 1);

        this.setCommandListener(this);
        //ADD ITEM
        this.addCommand(comBack);
//        this.addCommand(comAdd);
        this.addCommand(comEdit);
        this.addCommand(comDelete);
        iItemList = append(listGroup);
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    //Action
    public void commandAction(Command c, Displayable dsplbl) {
        if (c == comBack) {
            RecordTimeform f1 = new RecordTimeform("Record Time", IdUser);
            f1.setTicker(newsTicker);
            f1.setDisplay(this.display);
            this.display.setCurrent(f1);
        }else if (c == comEdit) {
            EditTimeform f1 = new EditTimeform("Edit Record Time", IdUser);
            f1.setTicker(newsTicker);
            f1.setDisplay(this.display);
            this.display.setCurrent(f1);
        }

    }
}
