# 大数据练习
# hadoop mapper-reducer
## 1. 统计单词出现的次数
统计一个文件中各个单词出现的次数<br>
[实现代码](Test118/src/com/sxt/hdfs/mr/wc)

## 2. 找出每个月气温最高的2天

1949-10-01 14:21:02	34c<br>
1949-10-01 19:21:02	38c<br>
1949-10-02 14:01:02	36c<br>
1950-01-01 11:21:02	32c<br>
1950-10-01 12:21:02	37c<br>
1951-12-01 12:21:02	23c<br>
1950-10-02 12:21:02	41c<br>
1950-10-03 12:21:02	27c<br>
1951-07-01 12:21:02	45c<br>
1951-07-02 12:21:02	46c<br>
1951-07-03 12:21:03	47c<br>
[实现代码](Test118/src/com/bjsxt/tq)

## 3. 推荐好友的好友
![好友关系图](/img/fd.png)

tom hello hadoop cat<br>
world hadoop hello hive<br>
cat tom hive<br>
mr hive hello<br>
hive cat hadoop world hello mr<br>
hadoop tom hive world<br>
hello tom world hive mr<br>
[实现代码](Test118/src/com/bjsxt/fd)
