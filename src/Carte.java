import java.util.*;

class Carte {
  
  private static Random loto = new Random(Calendar.getInstance().getTimeInMillis());

  private static int nbMe=0;
  private Map<Integer,Me> pop;
  private int sizeX;
  private int sizeY;

  public Carte(int sizeX, int sizeY) {
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    pop = new HashMap<Integer,Me>();
  }

  public synchronized int createMe() {
    nbMe += 1;
    Me m = new Me(nbMe,loto.nextInt(sizeX),loto.nextInt(sizeY));
    pop.put(nbMe,m);
    return nbMe;
  }

  public synchronized boolean relativeMove(int idMe, int moveX, int moveY) {
    boolean ret = false;
    /*
      - prend dans pop Me dont l'id est idMe
      - récupère les coordonnées x,y du Me
      - si x+moveX sort de la carte retourne false
      - si y+moveY sort de la carte retourne false
      - sinon, déplace le Me en x+moveX,y+moveY
      - retourne true
    */

    Me me = pop.get(idMe);
    if (me != null) {
      int meX = me.getX();
      int meY = me.getY();
      if (meX+moveX > -1 && meX+moveX < sizeX && meY+moveY > -1 && meY+moveY < sizeY) {
        me.goTo(meX+moveX,meY+moveY);
        ret = true;
      }
    }
    return ret;
  }

  public synchronized int[] getMeCoords(int idMe) {
    int[] ret = null;
    /*
      - prend dans pop Me dont l'id est idMe
      - récupère les coordonnées x,y du Me
      - instancie un tableau tab de 2 entiers
      - tab[0] = x, tab[1] = y
      - retourne tab
    */

    Me me = pop.get(idMe);
    if (me != null) {
      ret = new int[2];
      int meX = me.getX();
      int meY = me.getY();
      ret[0] = meX;
      ret[1] = meY;
    }
    return ret;
  }

  public synchronized Set<Me> getMeNeighbors(int idMe) {
    Set<Me> ret = null;
    /*
      - prend dans pop Me dont l'id est idMe
      - récupère les coordonnées x,y du Me
      - instancie un HashSet de Me
      - parcourt pop pour trouver les Me qui sont en x,y et les ajouter au set
      - retourne le HashSet
    */

    Me me = pop.get(idMe);
    if (me != null) {
      int meX = me.getX();
      int meY = me.getY();
      ret = new HashSet<>();
      Me neighbor;

      for (Integer i : pop.keySet()) {
        neighbor = pop.get(i);
        if (neighbor != null) {
          int neighborX = neighbor.getX();
          int neighborY = neighbor.getY();
          if (meX == neighborX && meY == neighborY && neighbor.getId() != idMe) {
            ret.add(neighbor);
          }
        }
      }

    }
    return ret;
  }
}
