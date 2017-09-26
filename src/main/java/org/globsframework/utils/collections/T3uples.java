package org.globsframework.utils.collections;

public class T3uples<T, D, E> extends Pair<T, D> {
  private E third;

  public T3uples(T first, D second, E third) {
    super(first, second);
    this.third = third;
  }

  public E getThird() {
    return third;
  }
}
