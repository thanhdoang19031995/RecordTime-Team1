/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageTag;

import ManagerTime.BusManageTime;
import ManagerTime.RecordTimeform;

import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.*;

/**
 *
 * @author BaoNguyen
 */
public class ManageTagform extends Form implements CommandListener {

    Ticker newsTicker = new Ticker("Start Team");
    private Display display;
    Form form;
    Command comBack;
    Command comAdd;
    Command comEdit;
    Command comSave;
    Command comDelete;
    ChoiceGroup listGroup;
    BusManageTag tagBus = new BusManageTag();
    TextField txtTag = new TextField("Name Tag", "", 100, TextField.ANY);
    int iItemList;
    public String IdUser;
    public String IdTag;
    Vector listTags = new Vector();
    String currentTagIdselected = null;

    public ManageTagform(String title, String uids) {
        super(title);
        showMenu();
        this.IdUser = uids;
        listag();
    }

    private void listag() {
        listGroup.deleteAll();
        listTags.removeAllElements();
        String all = tagBus.ListTag(IdUser);
        String trim = all.trim();
        String[] list = Split(trim, "\n");
        for (int i = 0; i < list.length; i++) {
            String[] part = Split(list[i], ";");
            //listGroup.append("" + part[1], null);
            //System.err.println(part[1]);
            listGroup.append(part[1], null);
            listTags.addElement(part[0]);
        }
    }

    private void showMenu() {
        listTags.removeAllElements();
        append(txtTag);
        form = this;
        listGroup = new ChoiceGroup("List Tag:", Choice.EXCLUSIVE);
        comAdd = new Command("Add", Command.OK, 2);
        comBack = new Command("Back", Command.OK, 2);
        comEdit = new Command("Edit", Command.OK, 2);
        comSave = new Command("Save", Command.OK, 2);

        comDelete = new Command("Delete", Command.OK, 2);

        this.setCommandListener(this);
        //ADD ITEM
        this.addCommand(comBack);
        this.addCommand(comAdd);
        this.addCommand(comEdit);
        this.addCommand(comSave);
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

        } else if (c == comAdd) {
            try {
                if (!testCase()) {
                    return;
                }
                String name = txtTag.getString();
                BusManageTag busAdd = new BusManageTag();
                String re = busAdd.AddTag(IdUser, name);
                Alert altest = new Alert("", "Added New Tag Successful", null, AlertType.INFO);
                display.setCurrent(altest, this);
                listag();

            } catch (Exception e) {
                e.getMessage();
            }
        } else if (c == comEdit) {
            try {
                int iSelected = listGroup.getSelectedIndex();
                String sSelected = listGroup.getString(iSelected);
                txtTag.setString(sSelected);
                this.currentTagIdselected = (String) listTags.elementAt(iSelected);

            } catch (Exception e) {
                e.getMessage();
            }
        } else if (c == comDelete) {
            try {
                int iSelected = listGroup.getSelectedIndex();
                this.currentTagIdselected = (String) listTags.elementAt(iSelected);
                tagBus.DeleteTag(currentTagIdselected);
                currentTagIdselected = null;

                Alert altest = new Alert("", "Deleted Successful", null, AlertType.INFO);
                display.setCurrent(altest, this);
                listag();

            } catch (Exception e) {
                e.getMessage();
            }
        } else if (c == comSave) {
            if (!testCase()) {
                return;
            }
            if (currentTagIdselected != null) {
                tagBus.Edittag(IdUser, this.currentTagIdselected, txtTag.getString());
                currentTagIdselected = null;
                Alert altest = new Alert("", "Edited Successful", null, AlertType.INFO);
                display.setCurrent(altest, this);
                listag();
            }
        }
    }

    public boolean testCase() {
        if (txtTag.getString().equals("")) {
            Alert altest = new Alert("", MessageTag.sTagNoInformation, null, AlertType.WARNING);
            display.setCurrent(altest, this);
            return false;
        } else if (txtTag.getString().length() > 0 && txtTag.getString().trim().equals("")) {
            Alert altest = new Alert("", MessageTag.sTagSpace, null, AlertType.WARNING);
            display.setCurrent(altest, this);
            txtTag.setString("");
            return false;
        }
        if (BusManageTime.isSpecalCharacter(txtTag.getString())) {
            Alert altest = new Alert("", MessageTag.sErrorSpecialCharacter, null, AlertType.WARNING);
            display.setCurrent(altest, this);
            txtTag.setString("");
            return false;
        }
        return true;
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
