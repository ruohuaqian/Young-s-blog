# ThreadLocal

- **望文生义** ：线程、本地->即线程间隔离的something
- 使用场景：假设一个多流程的处理过程，对于各个流程都要应用到同一个对象，在单程中，可以用static定义该对象，但在多线程中，需要用到*threadLocal*确保对象流程共享且线程安全。
- threadLocals是一个map（线程中的局部变量），用完后应及时remove掉（否则由于不会自动GC，故很可能导致outofMemeory）；
## 实现线程独有

- 在使用ThreadLocal时，单个ThreadLocal为各个线程保存了该**线程独有**的值，这一实现由每个Thread对象都持有的[ThreadLocalMap](#ThreadLocalMap)对象(成员变量)来保证。
  ```Java
  //from Thread.java
  ThreadLocal.ThreadLocalMap threadLocals = null;
  ```
  

  此Map的key为ThreadLocal对象，value为绑定的值，每个线程调用ThreadLocal对象的set(T value)方法时，都会将kv值存入该Map=>**同一个ThreadLocal在不同线程中对应不同的value。**
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


