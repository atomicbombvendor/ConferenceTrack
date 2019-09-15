## Conference Tack Management

### 运行
1. 程序入口：App.class Main方法
2. 数据文件：resources目录下txt文件

### 解题思路

1. 根据题目描述，设想使用**01背包**算法可以解决问题。
2. 早上的Session和下午的Session是两个背包，每一个Conference是一个物品。重量为时长，价值为时长对应的比例（时长/5）.
3. 使用以上的设计，可以满足每一个背包的价值最大时，时长也是最大的。
4. 计算早上的Session安排后，传入剩下的没有被安排的conference，计算下午的Session安排。组成一天，并插入Lunch和Networking Event的安排。
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

#### 校验规则

1. 会议标题不能包含数字
2. 会议标题的时间只能为 min。为其它会抛出异常
3. 会议标题中只有以 lightning 结尾的，才可以不包含 min
4. 会议标题不可以为空，比如"90min"

#### 特殊处理

1. 当上午结束时间早于12:00时，Lunch自动设置为12:00
2. 当下午会议结束时间早于16:00时，Networking Event自动设置为16:00

