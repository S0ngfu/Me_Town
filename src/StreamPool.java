import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

class StreamPool {
    Set<PrintStream> pool;

    public StreamPool() {
        pool = new HashSet<PrintStream>();
    }

    public synchronized void addStream(PrintStream ps) {
        pool.add(ps);
    }

    public synchronized void removeStream(PrintStream ps) {
        pool.remove(ps);
    }

    public synchronized void sendToAll (String msg) {
        for(PrintStream s : pool) {
            s.println(msg);
        }
    }

    public synchronized void sendTo (PrintStream[] ps, String msg) {
        for (int i = 0 ; i < ps.length ; i++) {
            if(pool.contains(ps[i])) {
                ps[i].println(msg);
            }
        }
    }

}