import java.io.Serializable;

class Me implements Serializable {

  private int x;
  private int y;
  private int id;

  public Me(int id, int x, int y) {
    this.id = id;
    goTo(x,y);
  }

  public void goTo(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() { return x;}
  public int getY() { return y;}
  public int getId() { return id;}
}
