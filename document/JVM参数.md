#JVM参数

## 堆设置

```
-Xms512m（堆的初始容量）
-Xmx512m（堆的最大容量）
-Xss256K（JVM栈的最大容量 默认1m）
-Xmn2g（设置年轻代大小）
-XX:NewRatio=4（新生代:老年代 = 1:4）
-XX:SurvivorRatio=8 （Eden 区所占比例 默认80%；2*survivor:eden = 2:8）
```

## 设置元空间

```
-XX:MetaspaceSize=100m（元空间的初始容量 默认20.8 M，出发full gc 的阈值）
-XX:MaxMetaspaceSize=100m（元空间的最大容量 默认系统的内存大小）
-XX:MinMetaspaceFreeRatio=40（最小空闲比 默认40%，gc后元空间扩容）
-XX:MaxMetaspaceFreeRatio=70（最大空闲比 默认70%，gc后元空间缩小）
```

## 设置GC种类

```
-XX:+UseSerialGC（设置串行收集器）
-XX:+UseParallelGC（设置并行收集器）
-XX:+UseParalledlOldGC（设置并行年老代收集器）
-XX:+UseConcMarkSweepGC（设置并发收集器）
```

## 打印GC信息

```
-XX:+PrintGCDetails （打印GC日志）
-XX:+PrintGCDateStamps （打印GC日志时间信息）
-Xloggc:targetDir/gc.log
-XX:ErrorFile=targetDir/hs_err_pid_%p.log
```
