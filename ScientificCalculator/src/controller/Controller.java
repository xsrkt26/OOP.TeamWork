package controller;
/**
 * @author ：kiyotaka
 * @description：
 * Controller接口， 实现Controller的类需要能够输入数据，进行运算，输出答案
 * @date ：2023/12/12 18:14
 */
interface Controller {
    void transmitData(String inputInformation);
    String getOutputInformation(String key);
    void count();
}


