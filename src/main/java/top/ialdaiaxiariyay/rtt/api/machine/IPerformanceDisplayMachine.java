package top.ialdaiaxiariyay.rtt.api.machine;

public interface IPerformanceDisplayMachine extends IServerTickMachine {

    int rtt$getTickTime();

    void rtt$observe();
}
