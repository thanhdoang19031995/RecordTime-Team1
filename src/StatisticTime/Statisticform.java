/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatisticTime;

import ManagerTime.*;
import javax.microedition.lcdui.DateField;
import java.util.Vector;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.*;
import java.util.Date;

public final class Statisticform extends Form implements CommandListener {

    Ticker newsTicker = new Ticker("Start Team");
    private Display display;
    Command Back;
    Command View;
    Command ManageTag;
    Command History;
    Displayable main;
    int iItemList;
    public String IdUser;
    Vector listTags = new Vector();
    ChoiceGroup listGroup;
    DateField beginDate;
    DateField endDate;

    public Statisticform(String title, String iUd) {

        super(title);
        IdUser = iUd;
        //draw 
        this.drawGUI();
        // a menu with items
        this.showMenu();
        //load data
        //main = Main;

    }

    public Statisticform(String title, Displayable Main, String uids) {
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
        Date dateDefaul = new Date();

        //beginTime
        beginDate = new DateField("Begin Date", DateField.DATE);
        beginDate.setDate(dateDefaul);
        this.append(beginDate);

        //endTime
        endDate = new DateField("End Date", DateField.DATE);
        endDate.setDate(dateDefaul);
        this.append(endDate);
        listGroup = new ChoiceGroup("Tag:", Choice.EXCLUSIVE);
        this.append(listGroup);
    }

    public void showMenu() {
        View = new Command("View", Command.OK, 2);
        Back = new Command("Back", Command.EXIT, 2);
        this.addCommand(Back);
        this.addCommand(View);
        this.setCommandListener(this);
    }

    //Action
    public void commandAction(Command c, Displayable d) {
        String label = c.getLabel();
        if (label.equals("View")) {
            if (!testCase()) {
                return;
            }
            this.showListTag();
        } else if (label.equals("Back")) {
            RecordTimeform f1 = new RecordTimeform("Record Time", IdUser);
            f1.setTicker(newsTicker);
            f1.setDisplay(this.display);
            this.display.setCurrent(f1);
        }
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void showListTag() {
        listGroup.deleteAll();
        try {
            BusStatistic bus = new BusStatistic();
            String sBeginTime = BusStatistic.getDateString(beginDate.getDate());
            String sEndTime = BusStatistic.getDateString(endDate.getDate());
            String sList = bus.ListTime(IdUser, sBeginTime, sEndTime);
            String trim = sList.trim();
            String[] list = BusStatistic.Split(trim, "\n");//show list
            for (int i = 0; i < list.length; i++) {
                String[] part = BusStatistic.Split(list[i], ";");
                // System.err.println(list[i]);
                listGroup.append("Tag: " + part[1] + "\n" + "Content: " + part[4] + "\n" + BusStatistic.getTimeString(part[2], part[3]), null);
            }
        } catch (Exception e) {
            Alert altest = new Alert("", "zxczxc", null, AlertType.WARNING);
            altest.setTimeout(Alert.FOREVER);
            display.setCurrent(altest, this);
        }
    }

    public boolean testCase() {

        if (endDate.getDate().getTime() < beginDate.getDate().getTime()) {
            Alert altest = new Alert("", MessageRecordTime.sErrorBeginTime, null, AlertType.WARNING);
            display.setCurrent(altest, this);
            return false;
        }
        return true;
    }
}
