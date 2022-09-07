### 方法

- computeIfAbsent()

  对 hashMap 中指定 key 的值进行重新计算，如果不存在这个 key，则添加到 hashMap 中。 computeIfAbsent() 方法的语法为:
  ```java
  hashmap.computeIfAbsent(K key, Function remappingFunction)
  ```
  源码(Map接口)
  ```java
    default V computeIfAbsent(K key,
            Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }

        return v;
    }
    ```
  参考用法
  ```java
  //对 Map<String ,List<String>> 添加value
  aMap.computeIfAbsent(key,v -> new ArrayList<>()).add(x);
  ``` 
