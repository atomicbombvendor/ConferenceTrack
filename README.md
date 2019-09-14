## Conference Tack Management

### 解题思路

1. 根据题目描述，设想使用**01背包**算法可以解决问题。
2. 早上的Session和下午的Session是两个背包，每一个Conference是一个物品。重量为时长，价值为时长对应的比例（时长/5）.
3. 先计算早上的Session安排，在计算晚上的Session安排，组成一天，并插入午饭和Networking Event的Conference。
4. 使用以上的设计，可以满足每一个背包的价值最大时，时长也是最大的。
5. 打印每一天的数据。

### 流程图

````mermaid
graph TD
    A(读取Resource文件夹下txt文件) --> B(得到String List)
    B --> C(校验每一行)
    C -- 校验满足题目中要求,设置属性 --> D(Conference List)
    D -- 01背包 --> E(计算每一个Session)
    E --> F(打印Track)
````

