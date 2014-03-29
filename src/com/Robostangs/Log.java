package com.Robostangs;
/*
import com.sun.squawk.microedition.io.FileConnection;
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.Connector;



/**
 *
 * @author Robostangs
 */
public class Log {
    /*
    private static Log instance = null;
    private static PrintStream output;
    private static Date current;
    
    private Log() throws IOException {
        current = new Date();
        String date = current.toString();
        date = date.replace(';', '_');
        String filename = date + Timer.getFPGATimestamp() + ".csv";
        filename = filename.replace(':', '-');
        try {
            FileConnection fconn = (FileConnection) Connector.open("file:///" + filename, Connector.READ_WRITE);
            if (!fconn.exists()) {
                fconn.create();
            }
            OutputStream out = fconn.openOutputStream();
            output = new PrintStream(out);
        } catch(ConnectionNotFoundException error) {
            System.out.println("Connection Not Found " + error);
        } catch(IOException error) {
            System.out.println("IOE");
            System.out.println(error);
        }
    }
    
    public static synchronized Log getInstance() {
        if(instance == null){
            try {
                instance = new Log();
            } catch (IOException ex) {
                System.out.println("Failed to create Log instance:\n");
            }
        }
        return instance;
    }
    public static void write(String data) {
        output.println(Timer.getFPGATimestamp() + "," + data + ";");
    }
    */
}
