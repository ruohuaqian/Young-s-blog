# ThreadLocal

- **望文生义** ：线程、本地->即线程间隔离的something
-

使用场景：假设一个多流程的处理过程，对于各个流程都要应用到同一个对象，在单程中，可以用static定义该对象，但在多线程中，需要用到*
threadLocal*确保对象流程共享且线程安全。

- threadLocals是一个map（线程中的局部变量），用完后应及时remove掉（否则由于不会自动GC，故很可能导致outofMemeory）；

## 实现线程独有

- 在使用ThreadLocal时，单个ThreadLocal为各个线程保存了该**线程独有**
  的值，这一实现由每个Thread对象都持有的[ThreadLocalMap](#ThreadLocalMap)对象(成员变量)来保证。
  ```Java
  //from Thread.java
  ThreadLocal.ThreadLocalMap threadLocals = null;
  ```

此Map的key为ThreadLocal对象，value为绑定的值，每个线程调用ThreadLocal对象的set(T value)方法时，都会将kv值存入该Map=>**
同一个ThreadLocal在不同线程中对应不同的value。**

- 源码
  ```Java
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
  ```
    - <small style="color:grey">*注：在类中定义ThreadLocal变量时，建议同时进行实例化*</small>

每个线程调用TheadLocal对象的get()方法时，就可以根据当前的ThreadLocal对象获取通过***ThreadLocalMap***绑定的value值

## ThreadLocalMap

```mermaid
classDiagram
    
    class ThreadLocalMap{
       -int INITIAL_CAPACITY $=16
       -Entry[] table
       -int size=0
       -int threshold
       
       ThreadLocalMap(ThreadLocal~?~,Object)
       -ThreadLocalMap(ThreadLocalMap)
       
       -setThreshold(int)
       -nextIndex(int,int)$
       -prevIndex(int,int)$  int
       -getEntry(ThreadLocal~?~) Entry
       -getEntryAfterMiss(ThreadLocal~?~,int,Entry) Entry
       -set(ThreadLocal~?~,Object)
       -remove(ThreadLocal~?~)
       -replaceStaleEntry(ThreadLocal~?~ key,Object,int)
       -expungeStaleEntry(int) int
       -cleanSomeSlots(int,int) boolean
       -rehash()
       -resize()
       -expungeStaleEntries()
   }
```

- **ThreadLocal.ThreadLocalMap.Entry**

  当抛弃掉ThreadLocal对象时，垃圾收集器会忽略这个key的引用而清理掉ThreadLocal对象，防止了内存泄漏。
  > <small>The entries in this hash map extend [WeakReference](../java-base/Reference.md), **using its main ref field as the key** (which is always a
  ThreadLocal object). Note that null keys (i.e. entry.get() == null) mean that the key is no longer referenced, so the
  entry can be expunged from table. Such entries are referred to as "stale entries" in the code that follows.</small>
  ```java
    // from ThreadLocal.java, in ThreadLocalMap
    static class Entry extends WeakReference<ThreadLocal<?>> {
      /** The value associated with this ThreadLocal. */
      Object value;
  
      Entry(ThreadLocal<?> k, Object v) {
          super(k);
          value = v;
      }
  
  }
  ```

