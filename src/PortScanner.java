import java.util.ArrayList;

public class PortScanner {

    private ArrayList<Integer> openPortList = null;
    private ArrayList<PortThread> threadList = new ArrayList<>();
    private ArrayList<Integer> deadThreads = new ArrayList<>();

    public PortScanner(String address, int minPort, int maxPort, int threadCount, int timeoutMil) {
        if (minPort > 0 && minPort < 65536 && maxPort > 1 && maxPort < 65536 && timeoutMil > 0) {

            int port = minPort;
            while (port < maxPort) {
                while (threadList.size() < threadCount) {
                    threadList.add(new PortThread("Portthread: " + (threadCount - threadList.size() + " Port: " + port), address, port, timeoutMil));
                    port++;
                }
                for (PortThread thread : threadList) {
                    if (thread.isOld()) {
                        deadThreads.add(threadList.indexOf(thread));
                    } else {
                        if (thread.isNew() && !thread.isInterrupted() && !thread.isAlive() && threadList.indexOf(thread)<maxPort) {
                            thread.start();
                        }
                    }
                }
                int offset = 0;
                boolean removed = false;
                for (Integer i : deadThreads) {
                    removed = true;
                    threadList.remove(i.intValue() - offset);
                    offset++;
                }
                if (removed) {
                    deadThreads.clear();
                }
            }

            //openPortList=PortThread.getOpenPortList();
        }
    }

    public void getOpenPortList() {
        System.out.println("The following ports are open:");
        for (Integer port : openPortList) {
            System.out.println(port);
        }
    }
}
