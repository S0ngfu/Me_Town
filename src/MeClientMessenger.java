import java.io.*;
import java.net.*;

class MeClientMessenger extends Thread  {

  int port;
  Socket commInfo = null;
  ServerSocket connInfo = null;
  BufferedReader br  = null;

  public MeClientMessenger( int port) {
    this.port = port;
  }

  public void run() {
    String line = "";
    try {     
      connInfo = new ServerSocket(port);
      // waiting for the connection of my thread
      commInfo = connInfo.accept();
      br = new BufferedReader(new InputStreamReader(commInfo.getInputStream()));
      while(true) {
        line = br.readLine();
        if ((line != null) && (!line.isEmpty())) {
          System.out.println("Message from: "+line);
        }
      }
    }
    catch(IOException e) {
      System.out.println("communication problem: "+e.getMessage());
    }
  }
}