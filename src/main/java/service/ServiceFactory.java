package service;

public class ServiceFactory {
    private static IService manager;

    //Singleton for the Business Layer
    public static IService GetManager(){
        if (manager == null){
            manager = new ServiceImp();
        }
        return manager;
    }

}
