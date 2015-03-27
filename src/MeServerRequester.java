import java.io.*;
import java.net.*;
import java.util.Set;

class MeServerRequester extends Thread {

  private Socket commReq;
  private Socket commInfo;
  private Carte map;
  private int id;
  private int idrequest;
  private ObjectInputStream ois; // used on commReq
  private ObjectOutputStream oos; // used on commReq
  private PrintStream ps; // used on commInfo

  private String playername;

  public MeServerRequester(Socket commReq, Carte map) {
    this.commReq = commReq;
    this.map = map;
  }

  public void run() {
    try {
      /* create streams with MeClientRequester */
      ois = new ObjectInputStream(commReq.getInputStream());
      oos = new ObjectOutputStream(commReq.getOutputStream());

      /* NB : since the MeClientMessenger is created before creating above streams
	 we are nearly sure that the thread is ready to receive connection demands.
	 Nevertheless, there is the possibility that the JVM has not yet begun its
	 execution and in this case, a connection refused occurs.
      */
      
      /* connect to the MeClientMessenger thread : 
	 - ip = commReq.getInetAddress()
	 - port = 1+port used by the client socket commReq (NB : given by the OS),
	          thus 1+commReq.getPort();
      */
      commInfo = new Socket(commReq.getInetAddress(), commReq.getPort()+1);
      /* create streams with MeClientMessenger */
      ps = new PrintStream(commInfo.getOutputStream());


      /* à compléter :
	 - créer un Me,
	 - renvoyer l'id et la position du Me au client
      */

      playername = (String) ois.readObject();

      id = map.createMe();
      oos.writeInt(id);
      int[] coord = map.getMeCoords(id);
      oos.writeInt(coord[0]);
      oos.writeInt(coord[1]);
      oos.flush();

      /* à compléter :
	 - tant que vrai :
	    - recevoir l'id d'une requête
	    - appeler la méthode associée à la requête pour exécuter son protocole
      */

      while (true) {
        idrequest = ois.readInt();
        if (idrequest == 1) {
          requestMoveOf();
        }
        if (idrequest == 2) {
          requestNeighbors();
        }
      }
    }
    catch(IOException e) {
      System.out.println("Problème communication dans le thread "+id);
    } catch (ClassNotFoundException e) {
      System.out.println("Problème communication dans le thread " + id + "\nMessage d'erreur : " + e.getMessage());
    }
  }

  public void requestMoveOf() {
    try {
      id = ois.readInt();
      int moveX = ois.readInt();
      int moveY = ois.readInt();
      if (map.relativeMove(id, moveX, moveY)) {
        int[] coord = map.getMeCoords(id);
        oos.writeInt(coord[0]);
        oos.writeInt(coord[1]);
      } else {
        int[] coord = map.getMeCoords(id);
        oos.writeInt(coord[0]);
        oos.writeInt(coord[1]);
      }
      oos.flush();
    } catch (IOException e) {
      System.out.println("Problème communication dans le thread " + id);
    }
  }

  public void requestNeighbors() {
    try {
      id = ois.readInt();
      Set<Me> neighbors;
      neighbors = map.getMeNeighbors(id);

      oos.writeObject(neighbors);
      oos.flush();
    } catch (IOException e) {
      System.out.println("Problème communication dans le thread " + id);
    }
  }
}
