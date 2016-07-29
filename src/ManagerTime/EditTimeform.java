/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerTime;

import ManageTag.BusManageTag;
import java.util.Date;
import javax.microedition.lcdui.DateField;
import java.util.Vector;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.*;

public final class EditTimeform extends Form implements CommandListener {

    BusManageTag tagBus = new BusManageTag();
    BusManageTime bus = new BusManageTime();
    Ticker newsTicker = new Ticker("Start Team");
    private Display display;
    Command Logout;
    Command Edit;
    Command Back;

    Displayable main;
    int iItemList;
    Vector listTags = new Vector();
    ChoiceGroup listGroup;
    TextField content;
    DateField date;
    DateField beginTime;
    DateField endTime;
    String sIdTime;
    String sIdUser;
    String sIdTag;

    public EditTimeform(String title, String RecordTime) {

        super(title);
        this.sIdTime = RecordTime;
        //draw ad
        this.drawGUI();
        // a menu with items
        this.showMenu();
        //load data

        this.loadEditRecordTime();
    }

    public EditTimeform(String title, Displayable Main, String uids) {
        super(title);
        sIdUser = uids;
        //draw 
        this.drawGUI();
        // a menu with items
        this.showMenu();
        //load data
        main = Main;

        this.loadEditRecordTime();
    }

    protected void drawGUI() {
        content = new TextField("Content:", "", 30, TextField.ANY);
        this.append(content);
        //DateField
        date = new DateField("Date", DateField.DATE);
        date.setDate(new java.util.Date());
        this.append(date);

        //beginTime
        beginTime = new DateField("Begin Time", DateField.DATE_TIME);
        date.setDate(new java.util.Date());
        this.append(beginTime);

        //endTime
        endTime = new DateField("End Time", DateField.DATE_TIME);
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
            HistoryTimeform f1 = new HistoryTimeform("History", sIdUser);
            f1.setTicker(newsTicker);
            f1.setDisplay(this.display);
            this.display.setCurrent(f1);
        } else if (label.equals("Save")) {
            if (!testCase()) {
                return;
            }
            editRecordTime();
        }
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void editRecordTime() {
        String sContent = content.getString();
        String sDate = BusManageTime.getDateString(date.getDate());
        //System.out.println(sDate);
        String sBeginTime = BusManageTime.getTimeString(beginTime.getDate());
        //System.out.println(sBeginTime);
        String sEndTime = BusManageTime.getTimeString(endTime.getDate());
        String sIdTag = listTags.elementAt(listGroup.getSelectedIndex()).toString();
        BusManageTime bus = new BusManageTime();
        String sResult = bus.EditTime(sIdTime, sIdTag, sDate, sBeginTime, sEndTime, sContent);
        Alert altest = new Alert("", "Edited Time Successful", null, AlertType.INFO);
        display.setCurrent(altest, this);
    }

    private void loadEditRecordTime() {
        // System.out.println(sIdTime);

        String sRecordTime = bus.ListTimeID(sIdTime);
        //System.out.println(sRecordTime);

        //date.setTime(new Date(date.getTime().getTime() - 5 * 86400000));
        //IdTime+";"+IdUser+";"+IdTag+";"+Content+";"+BeginTime+";"+EndTime+";"+DayEnd
        String[] part = BusManageTime.Split(sRecordTime, ";");//time record
        content.setString(part[3]);
        try {
            //System.err.println(part[6]);
            //date.setDate(BusManageTime.getTimeOfDay(part[6])); 
            Date dateDefaulte = new Date();
            long longDay = BusManageTime.getTimeOfDay(part[6]);
            dateDefaulte.setTime(longDay);
            date.setDate(dateDefaulte);
            dateDefaulte.setTime(longDay + BusManageTime.getTime(part[4]));
            beginTime.setDate(dateDefaulte);
            dateDefaulte.setTime(longDay + BusManageTime.getTime(part[5]));
            endTime.setDate(dateDefaulte);
            // System.err.println(BusManageTime.getDefaulDate());
            sIdUser = part[1];
            //   System.err.println("IDUSer: " + sIdUser);
            sIdTag = part[2].trim();

            listTag();
        } catch (Exception d) {
            // System.err.println(d.getMessage());
        }

    }

    private void listTag() {
        listGroup.deleteAll();
        listTags.removeAllElements();
        String all = tagBus.ListTag(sIdUser);
        String trim = all.trim();
        String[] list = BusManageTime.Split(trim, "\n");
        for (int i = 0; i < list.length; i++) {
            String[] part = BusManageTime.Split(list[i], ";");
            //listGroup.append("" + part[1], null);

            //System.err.println(part[1]);
            listGroup.append(part[1], null);
            listTags.addElement(part[0]);
        }
        // System.err.println("IDTAG:" + sIdTag);
        listGroup.setSelectedIndex(listTags.indexOf(sIdTag), true);
        //listGroup.setS
    }

    public boolean testCase() {
        if (content.getString().equals("")) {
            Alert altest = new Alert("", MessageRecordTime.sErrorContent, null, AlertType.WARNING);
            display.setCurrent(altest, this);
            return false;
        }

        if (content.getString().length() > 0 && content.getString().trim().equals("")) {
            Alert altest = new Alert("", MessageRecordTime.sContentSpace, null, AlertType.WARNING);
            display.setCurrent(altest, this);
            content.setString("");
            return false;
        }
        if (BusManageTime.isSpecalCharacter(content.getString())) {
            Alert altest = new Alert("", MessageRecordTime.sErrorSpecialCharacter, null, AlertType.WARNING);
            display.setCurrent(altest, this);
            content.setString("");
            return false;
        }

        if (endTime.getDate().getTime() <= beginTime.getDate().getTime()) {
            Alert altest = new Alert("", MessageRecordTime.sErrorBeginTime, null, AlertType.WARNING);
            display.setCurrent(altest, this);
            return false;
        }
        return true;
    }

}
