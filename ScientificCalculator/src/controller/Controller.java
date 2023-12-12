package controller;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/12/12 18:14
 */
interface Controller {
    void transmitData(String inputInformation);
    String getOutputInformation(String key);
    void count();
}


