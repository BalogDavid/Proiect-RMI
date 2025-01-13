import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class VectorSumImpl extends UnicastRemoteObject implements VectorSum {

    protected VectorSumImpl() throws RemoteException {
        super();
    }

    @Override
    public int calculateSum(int[] vector) throws RemoteException {
        // Calcularea sumei elementelor din segment
        int sum = Arrays.stream(vector).sum();
        System.out.println("Suma segmentului: " + sum); // Debugging
        return sum;
    }

    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            VectorSumImpl server = new VectorSumImpl();
            java.rmi.Naming.rebind("VectorSumService", server);
            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}