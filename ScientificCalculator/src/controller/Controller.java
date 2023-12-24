package controller;

/**
 * 提供给前端调用的运算器Model的方法接口
 * @author ：kiyotaka
 * @date ：2023/12/12 18:14
 */
interface Controller {
    void transmitData(String inputInformation);
    String getOutputInformation(String key);
    void count();
}