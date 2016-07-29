/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerTime;

import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.*;

/**
 *
 * @author BaoNguyen
 */
public class HistoryTimeform extends Form implements CommandListener {

    Ticker newsTicker = new Ticker("Start Team");
    private Display display;
    Form form;
    Command comBack;
    Command comAdd;
    Command comEdit;
    Command comSave;
    Command comDelete;
    ChoiceGroup listGroup;
    BusManageTime busTime = new BusManageTime();
    int iItemList;
    public String IdUser;
    public String IdTag;
    Vector listTime = new Vector();
    //Vector listTimeAll=new Vector();
    String currentTimeIdselected = null;

    public HistoryTimeform(String title, String uids) {
        super(title);
        showMenu();
        this.IdUser = uids;
        listime();
    }

    private void listime() {
        listGroup.deleteAll();
        listTime.removeAllElements();
        //listTimeAll.removeAllElements();//edit
        String all = busTime.ListTime(IdUser);
        String trim = all.trim();
        String[] list = Split(trim, "\n");//show list
        for (int i = 0; i < list.length; i++) {
            String[] part = Split(list[i], ";");
            //listGroup.append("" + part[1], null);
            //System.err.println(part[1] + " " + part[5] + "\n" + part[4] + " " + part[2] + " " + part[3]);
            listGroup.append(part[1] + " " + part[5] + "\n" + part[4] + " " + part[2] + " " + part[3], null);
            listTime.addElement(part[0]);
            //listTimeAll.addElement(list[i]);//edit
        }
    }

    private void showMenu() {
        listTime.removeAllElements();
        //listTimeAll.removeAllElements();//edit
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
        } else if (c == comEdit) {
            try {
                int iSelected = listGroup.getSelectedIndex();
                this.currentTimeIdselected = (String) listTime.elementAt(iSelected);
                EditTimeform f1 = new EditTimeform("Edit Record Time", this.currentTimeIdselected);
                f1.setTicker(newsTicker);
                f1.setDisplay(this.display);
                this.display.setCurrent(f1);
                currentTimeIdselected = null;
            } catch (Exception e) {
                e.getMessage();
            }
        } else if (c == comDelete) {
            int iSelected = listGroup.getSelectedIndex();
            this.currentTimeIdselected = (String) listTime.elementAt(iSelected);
            Alert deleteAlert = new Alert("Delete Record Time",
                    "Are you sure you want to delete record time?", null, AlertType.WARNING);
            deleteAlert.addCommand(new Command("Yes", Command.EXIT, 1));
            deleteAlert.addCommand(new Command("No", Command.SCREEN, 2));
            deleteAlert.setCommandListener(new CommandListener() {
                public void commandAction(Command c, Displayable d) {
                    String label = c.getLabel();
                    if (label.equals("Yes")) {
                        busTime.DeleteTime(currentTimeIdselected);
                        currentTimeIdselected = null;
                        listime();
                    }
                    display.setCurrent(form);
                }
            });
            deleteAlert.setTimeout(Alert.FOREVER);
            display.setCurrent(deleteAlert);
        }

    }

    public static String[] Split(String splitStr, String delimiter) {
        StringBuffer token = new StringBuffer();
        Vector tokens = new Vector();
        // split
        char[] chars = splitStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (delimiter.indexOf(chars[i]) != -1) {
                // we bumbed into a delimiter
                if (token.length() > 0) {
                    tokens.addElement(token.toString());
                    token.setLength(0);
                }
            } else {
                token.append(chars[i]);
            }
        }
        // don't forget the "tail"...
        if (token.length() > 0) {
            tokens.addElement(token.toString());
        }
        // convert the vector into an array
        String[] splitArray = new String[tokens.size()];
        for (int i = 0; i < splitArray.length; i++) {
            splitArray[i] = (String) tokens.elementAt(i);
        }
        return splitArray;
    }
}
