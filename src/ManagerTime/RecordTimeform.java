/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerTime;

import ManageTag.BusManageTag;
import javax.microedition.lcdui.DateField;
import ManageTag.ManageTagform;
import java.util.Vector;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.*;
import javax.microedition.sensor.Data;
import java.util.Date;
public final class RecordTimeform extends Form implements CommandListener {

    Ticker newsTicker = new Ticker("Java J2ME");
    private Display display;
    Command Logout;
    Command Add;
    Command ManageTag;
    Command History;

    Displayable main;
    BusManageTag tagBus = new BusManageTag();
    int iItemList;
    public String IdUser;
    Vector listTags = new Vector();
    ChoiceGroup listGroup;
    TextField content;
    DateField date;
    DateField beginTime;
    DateField endTime;
    public RecordTimeform(String title, String iUd) {

        super(title);
        IdUser = iUd;
        //draw 
        this.drawGUI();
        // a menu with items
        this.showMenu();
        this.listag();
        //load data
    }

    private void listag() {
        listGroup.deleteAll();
        listTags.removeAllElements();
        String all = tagBus.ListTag(IdUser);
        String trim = all.trim();
        String[] list = ManageTagform.Split(trim, "\n");
        for (int i = 0; i < list.length; i++) {
            String[] part = ManageTagform.Split(list[i], ";");
            System.err.println(part[1]);
            listGroup.append(part[1], null);
            listTags.addElement(part[0]);
        }
    }

    public RecordTimeform(String title, Displayable Main, String uids) {
        super(title);
        IdUser = uids;
        //draw 
        this.drawGUI();
        this.listag();
        // a menu with items
        this.showMenu();
        //load data
        main = Main;
    }

    protected void drawGUI() {
        content = new TextField("Content:", "", 30, TextField.ANY);
        this.append(content);
        //DateField
        date = new DateField("Date", DateField.DATE);
        date.setDate(new java.util.Date());
        this.append(date);

        //beginTime
        beginTime = new DateField("Begin Time", DateField.TIME);
        date.setDate(new java.util.Date());
        this.append(beginTime);

        //endTime
        endTime = new DateField("End Time", DateField.TIME);
        date.setDate(new java.util.Date());
        this.append(endTime);
        //list
        listGroup = new ChoiceGroup("Tag:", Choice.EXCLUSIVE);
        this.append(listGroup);
    }

    public void showMenu() {
        Add = new Command("Add", Command.OK, 2);
        ManageTag = new Command("ManageTag", Command.OK, 2);
        History = new Command("History", Command.OK, 2);

        Logout = new Command("Logout", Command.EXIT, 2);
        this.addCommand(Logout);
        this.addCommand(Add);
        this.addCommand(ManageTag);
        this.addCommand(History);

        this.setCommandListener(this);
    }

    //Action
    public void commandAction(Command c, Displayable d) {
        String label = c.getLabel();
        if (label.equals("Logout")) {
            this.display.setCurrent(main);
        } else if (label.equals("ManageTag")) {
            ManageTagform f1 = new ManageTagform("ManageTag", IdUser);
            f1.setDisplay(this.display);
            f1.setTicker(newsTicker);
            this.display.setCurrent(f1);
        } else if (label.equals("History")) {
            HistoryTimeform f1 = new HistoryTimeform("History",IdUser);
            f1.setDisplay(this.display);
            this.display.setCurrent(f1);

        } else if(label.equals("Add")){
            getRecordTime();
        }
    }

    public void setDisplay(Display display) {
        this.display = display;
    }
    
    public void getRecordTime(){
        String sContent=content.getString();
        String sDate=BusManageTime.getDateString(date.getDate());
        System.out.println(sDate);
        String sBeginTime=BusManageTime.getTimeString(beginTime.getDate());
        System.out.println(sBeginTime);
        String sEndTime=BusManageTime.getTimeString(endTime.getDate());
        String sIdTag=listTags.elementAt(listGroup.getSelectedIndex()).toString();
        
        BusManageTime bus=new BusManageTime();
        String sResult=bus.AddTime(sIdTag, IdUser, sDate, sBeginTime, sEndTime, sContent);
        Alert altest = new Alert("", sResult, null, AlertType.WARNING);
        display.setCurrent(altest, this);
    }
}
