#Spark问题记录

1.版本>=3.1的spark,map(df2dataSet)提示错误：No implicits found fo parameter evidence$6: Encoder(Array[Byte])
> Spark needs to know how to serialize JVM types to send them from workers to the master. In some cases they can be
> automatically generated and for some types there are explicit implementations written by Spark devs.
>
> In this case you can implicitly pass them. If your SparkSession is named spark then you miss following line:
> 
> ```import spark.implicits._```

**注意导入的是变量的implicits._**