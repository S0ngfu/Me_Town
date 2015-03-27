import java.io.*;
import java.net.*;

class MeServer {

  public static void main(String[] args) {
	
    int port = -1;
    int sizeX;
    int sizeY;
    MeServerMain server = null;
	
    if (args.length != 3) {
      System.out.println("usage: MeServer port tailleX tailleY");
      System.exit(1);
    }

    try {
      port = Integer.parseInt(args[0]);
      sizeX = Integer.parseInt(args[1]);
      sizeY = Integer.parseInt(args[2]);
      server = new MeServerMain(port,sizeX,sizeY);
      server.mainLoop();
    }
    catch(IOException e) {
      System.out.println("problème creation serveur : "+e.getMessage());
      System.exit(1);
    }
  }
}
