import java.io.*;
import java.net.*;
import java.util.*;

class MeClientRequester  {

  String ipServer;
  int portServer;
  String playerName;
  Socket commReq = null;
  ObjectInputStream ois = null;
  ObjectOutputStream oos = null;
  int idMe;
  int posX;
  int posY;

  public MeClientRequester(String playerName, String ipServer, int portServer) throws IOException {

    this.playerName = playerName;
    this.ipServer = ipServer;
    this.portServer = portServer;

    // connection to the server
    commReq = new Socket(ipServer, portServer);
    /* create the thread that manages info messages.*/
    MeClientMessenger t = new MeClientMessenger(commReq.getLocalPort()+1);
    t.start();

    // creating threads
    oos = new ObjectOutputStream(commReq.getOutputStream());
    oos.flush();
    ois = new ObjectInputStream(commReq.getInputStream());
  }

  public void handshake() throws IOException {

    // sending player name
    oos.writeObject(playerName);
    oos.flush();

    // receiving my identity, and position
    idMe = ois.readInt();
    posX = ois.readInt();
    posY = ois.readInt();
    System.out.println("My me is #"+idMe+" and is in ["+posX+","+posY+"]");
  }

  public void requestLoop() throws IOException {

    String reqLine = null;
    BufferedReader consoleIn = null;
    String[] reqParts = null;

    try {
      consoleIn = new BufferedReader(new InputStreamReader(System.in));
      System.out.print("MeClient> ");
      reqLine = consoleIn.readLine();
      reqParts = reqLine.split(" ");
      while (!reqParts[0].equals("QUIT")) {

        switch (reqParts[0]) {
          case "1":
              requestMoveOf(Integer.parseInt(reqParts[1]), Integer.parseInt(reqParts[2]));
            break;

          case "2":
            requestNeighbors();
          break;

          case "3":
            /*
              Appeler méthode pour envoyer msg aux voisins
              Mettre reqParts en paramètres et parourir reqParts de 1 à length et comparer avec requestNeighbors les id (contains?)
             */

          default:
            System.out.println("Requètes disponibles : \n1 x y : Permet de déplacer son me de x et y\n2 : Permet de voir ses voisins");
            break;
        }

	    System.out.print("MeClient> ");
	    reqLine = consoleIn.readLine();
	    reqParts = reqLine.split(" ");
      }
    }
    catch(IOException e) {
      System.out.println("communication problem: "+e.getMessage());
    }
  }

  public void requestMoveOf(int moveX, int moveY) {
    try {
      oos.writeInt(1);
      oos.writeInt(idMe);
      oos.writeInt(moveX);
      oos.writeInt(moveY);
      oos.flush();

      posX = ois.readInt();
      posY = ois.readInt();
      System.out.println("My me is #"+idMe+" and is in ["+posX+","+posY+"]");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void requestNeighbors() {
    try {
      oos.writeInt(2);
      oos.writeInt(idMe);
      oos.flush();

      Set<Me> neighbors;
      neighbors = (Set<Me>) ois.readObject();
      if (neighbors.isEmpty()) {
        System.out.println("I have no neighbors");
      } else {
        System.out.println("My neighbors are : ");
        Iterator<Me> i = neighbors.iterator();
        while (i.hasNext()) {
          System.out.print(i.next().getId() + " ; ");
        }
        System.out.println("");
      }
    } catch (IOException e) {
      System.out.println("communication problem: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      System.out.println("communication problem: " + e.getMessage());
    }
  }
}
		
