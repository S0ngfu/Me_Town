import java.io.*;
import java.net.*;

class MeServerMain {

  ServerSocket conn;
  Socket comm;
  int port = -1;
  int sizeX;
  int sizeY;
  Carte map = null;

  public MeServerMain(int port, int sizeX, int sizeY) throws IOException {

    conn = new ServerSocket(port);
    map = new Carte(sizeX,sizeY);
  }

  public void mainLoop() throws IOException {

    while (true) {
      try {
	comm = conn.accept();
	MeServerRequester t = new MeServerRequester(comm,map);
	t.start();
      }
      catch(IOException e) {
	System.out.println("problème demande connexion au serveur : "+e.getMessage());
      }
    }
  }
}
