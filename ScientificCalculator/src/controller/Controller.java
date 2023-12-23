package controller;
/**
 * @author ：kiyotaka
 * @description：
 * Controller接口， 实现Controller的类需要能够输入数据，进行运算，输出答案
 * @date ：2023/12/12 18:14
 */
interface Controller {
    /**
     * @author: kiyotaka
     * @description: 所有的Model数据输入形式都是一个字符串，
     * 不同的Model所需要的格式不同，在个Controller中可以看到格式要求。
     * @date: 2023/12/23 11:25
     * @return
     */
    void transmitData(String inputInformation);

    /**
     * @author: kiyotaka
     * @description: 对于输出结果的只有一个的Model,调用该函数得到输出结果统一为“answer”
     * 对于多个输出结果的Model,调用该函数得到不同的输出结果具体看Model的实现。
     * @date: 2023/12/23 11:25
     * @return
     */
    String getOutputInformation(String key);

    /**
     * @author: kiyotaka
     * @description: 计算函数，所有Model均一致。
     * @date: 2023/12/23 11:25
     * @return
     */
    void count();
}


