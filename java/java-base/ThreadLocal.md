## ThreadLocal

- **望文生义** ：线程 本地->即线程间隔离的something
- 使用场景：假设一个多流程的处理过程，对于各个流程都要应用到同一个对象，在单程中，可以用static定义该对象，但在多线程中，需要用到threadLocal确保对象流程共享且线程安全。
- threadLocals是一个map（线程中的局部变量），用完后应及时remove掉（否则由于不会自动GC，故很可能导致outofMemeory）；