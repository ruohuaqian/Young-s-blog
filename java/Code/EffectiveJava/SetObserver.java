package EffectiveJava;

/**
 * @author xijin yang
 * @date 2022/10/19
 */
@FunctionalInterface
public interface SetObserver<E> {
    void added(ObservableSet<E> set, E element);
}
