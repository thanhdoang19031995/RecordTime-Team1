package managetime;

import ManagerTime.RecordTimeform;
import ConnectWeb.Connect;
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
    public void menu() {
        RecordTimeform f1 = new RecordTimeform("Record Time", Display.getDisplay(this).getCurrent());
        f1.setTicker(newsTicker);
        display = Display.getDisplay(this);
        display.setCurrent(f1);
    }

    public void tryAgain() {
        Alert error = new Alert("Login Incorrect", "Please try again", null, AlertType.ERROR);
        error.setTimeout(Alert.FOREVER);
        txtuserName.setString("");
        txtpassword.setString("");
        display.setCurrent(error, form);
    }
    ConnectWeb.Connect User = new Connect();

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
