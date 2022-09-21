# @FunctionalInterface

- An **informative** annotation type used to indicate that an interface type declaration is intended to be a functional
  interface as defined by the Java Language Specification.
- **Conceptually, a functional interface has exactly one abstract method**（不含default methods
  和override了Object类public方法的抽象方法）.

- 标识**功能接口**-->将方法作为参数使用。
- 遗漏了只会导致理解问题
- eg.Comparable ， Runnable ， EventListener ， Comparator等
- 应用实例：

```mermaid
classDiagram 

direction RL

class Optional~T~ {
+ ...
+ ifPresent(Consumer~? super T~ consumer)
}

class Consumer~T~ {
+ accept()
+ default andThen(Consumer~? super T~ after)
}

Consumer..Optional

```

```java

@FunctionalInterface
public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
```

```java
public void ifPresent(Consumer<? super T>consumer){
        if(value!=null)
        consumer.accept(value);
        }
```

```mermaid
flowchart TD

A["新建视图"] -->x2[...]-->|"查materialization表内
（同存储类别同storeId的）"|B{"指定group有
正在运行的定时任务"}
B --> |YES| E["跳过"]
B --> |NO| C["新增记录"]
C --> D["逐个提交group灌库任务到Gear"]-->F["save新物化表记录"]

A1["更新job的定时任务"]-->X1["查询定时任务执行列表"]-->B1["生成新记录"]-->c1["存入job表"]
A2["上线"]-->|"查materialization表"|B2{"指定group有coId
且swich为true"}
B2-->|"YES"| C2["跳过"]
B2-->|"NO"|D2["运行并更新coId和switch"]
```
