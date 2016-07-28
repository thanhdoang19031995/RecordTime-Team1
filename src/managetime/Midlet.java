package managetime;

import ManagerTime.RecordTimeform;
import java.util.Random;
import java.util.Vector;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;

public class Midlet extends MIDlet implements CommandListener {

    Ticker newsTicker = new Ticker("Java J2ME");
    private Display display;
    private final TextField txtuserName;
    private final TextField txtpassword;
    private final Form form;
    private final Command cancel;
    private final Command login;
    private final Command register;
    private final Command recover;
    public static String uid;

    public Midlet() {
        txtuserName = new TextField("Email         ", "", 30, TextField.ANY);
        txtpassword = new TextField("Password", "", 30, TextField.PASSWORD);

        form = new Form("Log in");
        cancel = new Command("Cancel", Command.CANCEL, 2);
        login = new Command("Login", Command.OK, 2);
        register = new Command("Register", Command.OK, 2);
        recover = new Command("Recover PassWord", Command.OK, 2);

    }

    public void startApp() {
        display = Display.getDisplay(this);
        form.setTicker(newsTicker);
        form.append(txtuserName);
        form.append(txtpassword);
        form.addCommand(cancel);
        form.addCommand(login);
        form.addCommand(register);
        form.addCommand(recover);
        form.setCommandListener(this);
        display.setCurrent(form);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }

    public void tryAgain() {
        Alert error = new Alert("Login Incorrect", "Please try again", null, AlertType.ERROR);
        error.setTimeout(Alert.FOREVER);
        txtuserName.setString("");
        txtpassword.setString("");
        display.setCurrent(error, form);
    }
    BusManageAccount User = new BusManageAccount();

    public void commandAction(Command c, Displayable d) {
        //String label = c.getLabel();
        if (c == cancel) {
            destroyApp(true);
        } else if (c == login) {
            try {
                String Email = txtuserName.getString();
                String Password = txtpassword.getString();
                String result = User.Login(Email, Password);
                String[] parts = Split(result, ";");
                String part1 = parts[0];
                String part2 = parts[1];
                int idUser = Integer.parseInt(part2.trim());
                if (part1.equals("Ok")) {
                    uid = "" + idUser;
                    menu();
                    System.out.println("SUCCESSFUL");
                }
            } catch (Exception e) {
                Alert error = new Alert("Login Incorrect", "Please try again", null, AlertType.ERROR);
                error.setTimeout(Alert.FOREVER);
                txtuserName.setString("");
                txtpassword.setString("");
                display.setCurrent(error, form);
                e.getMessage();
                System.out.println("Fail");
            }
        } else if (c == register) {
            try {
                String Email = txtuserName.getString();
                String[] nameEmail = Split(Email, "@");
                String nameTest = nameEmail[1];
                if (nameTest.equals("gmail.com")) {
                    Random rand = new Random();
                    int RandomPass = rand.nextInt((10000 - 10000) + 10000);
                    String Pass = "" + RandomPass;
                    BusManageAccount bus = new BusManageAccount();
                    String result = bus.registerUser(Email, Pass);
                    if (result.startsWith("Ok")) {
                        String result2 = bus.SendMailUser(Email, Pass);
                        System.out.println("zzzcsssd" + result2);
                        if (result2.startsWith("Oke")) {
                            Alert altest = new Alert("Register Success", "Please check your email", null, AlertType.INFO);
                            altest.setTimeout(Alert.FOREVER);
                            txtuserName.setString("");
                            txtpassword.setString("");
                            display.setCurrent(altest, form);

                        } else {
                            Alert altest = new Alert("Register Fail", result2, null, AlertType.ERROR);
                            altest.setTimeout(Alert.FOREVER);
                            txtuserName.setString("");
                            txtpassword.setString("");
                            display.setCurrent(altest, form);
                        }
                    } else {
                        Alert altest = new Alert("Register Fail", result, null, AlertType.ERROR);
                        altest.setTimeout(Alert.FOREVER);
                        txtuserName.setString("");
                        txtpassword.setString("");
                        display.setCurrent(altest, form);
                    }
                } else {
                    Alert altest = new Alert("Register Fail", "Wrong format email", null, AlertType.WARNING);
                    altest.setTimeout(Alert.FOREVER);
                    txtuserName.setString("");
                    txtpassword.setString("");
                    display.setCurrent(altest, form);
                }
            } catch (Exception e) {
                Alert altest = new Alert("Register Fail", "Wrong format email", null, AlertType.WARNING);
                altest.setTimeout(Alert.FOREVER);
                txtuserName.setString("");
                txtpassword.setString("");
                display.setCurrent(altest, form);
            }

        } else if (c == recover) {
            try {
                String Email = txtuserName.getString();
                String[] nameEmail = Split(Email, "@");
                String nameTest = nameEmail[1];
                if (nameTest.equals("gmail.com")) {
                    Random rand = new Random();
                    int RandomPass = rand.nextInt((10000 - 10000) + 10000);
                    String Pass = "" + RandomPass;
                    BusManageAccount bus = new BusManageAccount();
                    String result = bus.registerUser(Email, Pass);
                    if (result.startsWith("Ok")) {
                        String result2 = bus.SendMailUser(Email, Pass);
                        System.out.println("zzzcsssd" + result2);
                        if (result2.startsWith("Oke")) {
                            Alert altest = new Alert("Recover Success", "Please check your email", null, AlertType.INFO);
                            altest.setTimeout(Alert.FOREVER);
                            txtuserName.setString("");
                            txtpassword.setString("");
                            display.setCurrent(altest, form);

                        } else {
                            Alert altest = new Alert("Recover Fail", result2, null, AlertType.ERROR);
                            altest.setTimeout(Alert.FOREVER);
                            txtuserName.setString("");
                            txtpassword.setString("");
                            display.setCurrent(altest, form);
                        }
                    } else {
                        Alert altest = new Alert("Recover Fail", result, null, AlertType.ERROR);
                        altest.setTimeout(Alert.FOREVER);
                        txtuserName.setString("");
                        txtpassword.setString("");
                        display.setCurrent(altest, form);
                    }
                } else {
                    Alert altest = new Alert("Recover Fail", "Wrong format email", null, AlertType.WARNING);
                    altest.setTimeout(Alert.FOREVER);
                    txtuserName.setString("");
                    txtpassword.setString("");
                    display.setCurrent(altest, form);
                }
            } catch (Exception e) {
                Alert altest = new Alert("Recover Fail", "Wrong format email", null, AlertType.WARNING);
                altest.setTimeout(Alert.FOREVER);
                txtuserName.setString("");
                txtpassword.setString("");
                display.setCurrent(altest, form);
            }
        }
    }

    public void menu() {
        RecordTimeform f1 = new RecordTimeform("Record Time", Display.getDisplay(this).getCurrent(), uid);
        //ManageTag.ManageTagform f1 = new ManageTagform(display, uid);
        f1.setTicker(newsTicker);
        f1.setDisplay(this.display);
        display = Display.getDisplay(this);
        display.setCurrent(f1);
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
