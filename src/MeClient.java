import java.io.*;
import java.net.*;
import java.util.*;

class MeClient  {

  public static void main(String []args) {

    MeClientRequester requester = null;
    int port = -1;

    if (args.length != 3) {
      System.out.println("usage: MeClient player_name ip_server port_server");
      System.exit(1);
    }
    port = Integer.parseInt(args[2]);

    try {
      requester = new MeClientRequester(args[0], args[1], port);
      requester.handshake();
      requester.requestLoop();
    }
    catch(IOException e) {
      System.out.println("cannot connect to server: "+e.getMessage());
      System.exit(1);
    }
  }
}
		
