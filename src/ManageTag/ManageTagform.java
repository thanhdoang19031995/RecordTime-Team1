/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageTag;

import ManagerTime.RecordTimeform;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.*;

/**
 *
 * @author BaoNguyen
 */
public class ManageTagform extends Form implements CommandListener {

    Ticker newsTicker = new Ticker("Java J2ME");
    private Display display;
    Form form;
    Command comBack;
    Command comAdd;
    Command comEdit;
    Command comSave;
    Command comDelete;
    ChoiceGroup listGroup;

    TextField txtTag = new TextField("", "", 10, TextField.ANY);
    int iItemList;
    Vector listTag;
    public String IdUser;
    public String IdTag;

    public ManageTagform(String title, String uids) {
        super(title);
        showMenu();
        this.IdUser = uids;
        BusManageTag tag = new BusManageTag();
        String all = tag.ListTag(IdUser);
        String trim = all.trim();
        String[] list = Split(trim, "\n");
        for (int i = 0; i < list.length; i++) {
            String[] part = Split(list[i], ";");
            listGroup.append("" + part[1], null);
        }
    }

    private void showMenu() {
        append(txtTag);
        form = this;
        comBack = new Command("Back", Command.OK, 2);
        comAdd = new Command("Add", Command.OK, 2);
        comDelete = new Command("Delete", Command.OK, 2);
        listGroup = new ChoiceGroup("List Tag:", Choice.EXCLUSIVE);
        //ADD ITEM
        this.addCommand(comBack);
        this.addCommand(comAdd);
        this.addCommand(comDelete);
        this.setCommandListener(this);
        iItemList = append(listGroup);
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    //Action
    public void commandAction(Command c, Displayable dsplbl) {
        if (c == comBack) {
            RecordTimeform f1 = new RecordTimeform("Record Time");
            f1.setTicker(newsTicker);
            f1.setDisplay(this.display);
            this.display.setCurrent(f1);
        } else if (c == comAdd) {
            try {
                String name = txtTag.getString();
                BusManageTag busAdd = new BusManageTag();
                String re = busAdd.AddTag(IdUser, name);
                Alert altest = new Alert("", re, null, AlertType.WARNING);
                display.setCurrent(altest, this);

            } catch (Exception e) {
                e.getMessage();
            }

        } else if (c == comDelete) {
            try {

                BusManageTag busDelete = new BusManageTag();
                String re = busDelete.DeleteTag(IdTag);
            } catch (Exception e) {
                e.getMessage();
            }

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
