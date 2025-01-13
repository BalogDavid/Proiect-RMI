import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VectorSum extends Remote {
    int calculateSum(int[] segment) throws RemoteException;
}