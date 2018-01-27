import java.util.ArrayList;

public class PortScanner {

    private ArrayList<Integer> openPortList = null;
    private ArrayList<PortThread> threadList = new ArrayList<>();
    private ArrayList<Integer> deadThreads = new ArrayList<>();
    private int port = 0;
    private int usedThreads = 0;

    public PortScanner(String address, int minPort, int maxPort, int threadCount, int timeoutMil) {
        if(threadCount>(maxPort-minPort+1)) {
            threadCount = (maxPort-minPort+1);
        }

        if (minPort > 0 && minPort < 65536 && maxPort > 1 && maxPort < 65536 && timeoutMil > 0) {
            int port = minPort;
            while (port < maxPort+1) {
                while (threadList.size() < threadCount) {
                    threadList.add(new PortThread("Portthread: " + (threadCount - threadList.size() + " Port: " + port), address, port, timeoutMil));
                    port++;
                }
                for (PortThread thread : threadList) {
                    if (thread.getState() == Thread.State.TERMINATED) {
                        deadThreads.add(threadList.indexOf(thread));
                    } else {
                        if (thread.getState() == Thread.State.NEW && ((threadList.size()-(threadList.indexOf(thread)+1)))-1<((maxPort-minPort))-usedThreads){
                            thread.start();
                            usedThreads++;
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
        }
    }
}
