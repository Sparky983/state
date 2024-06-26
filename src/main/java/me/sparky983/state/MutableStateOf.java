package me.sparky983.state;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jspecify.annotations.Nullable;

/**
 * The default {@link MutableState} implementation, that takes a value as input.
 *
 * @param <T> the type of the state
 */
final class MutableStateOf<T extends @Nullable Object> implements MutableState<T> {
  private Map<Subscription, Consumer<? super T>> subscriptions = Collections.emptyMap();

  private T value;

  MutableStateOf(T value) {
    this.value = value;
  }

  @Override
  public void set(T value) {
    this.value = value;
    for (Consumer<? super T> subscriber : this.subscriptions.values()) {
      try {
        subscriber.accept(value);
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public T get() {
    return this.value;
  }

  @Override
  public Subscription subscribe(Consumer<? super T> subscriber) {
    Objects.requireNonNull(subscriber, "subscriber cannot be null");

    try {
      subscriber.accept(this.value);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return this.subscribe0(subscriber);
  }

  @Override
  public <R extends @Nullable Object> State<R> map(Function<? super T, ? extends R> mapper) {
    MutableState<R> state = MutableState.of(mapper.apply(this.value));
    this.subscribe0(value -> state.set(mapper.apply(value)));
    return state;
  }

  private synchronized Subscription subscribe0(Consumer<? super T> subscriber) {
    Map<Subscription, Consumer<? super T>> copy = new HashMap<>(this.subscriptions);
    Subscription subscription = new MutableStateSubscription();
    copy.put(subscription, subscriber);
    this.subscriptions = copy;
    return subscription;
  }

  private final class MutableStateSubscription implements Subscription {
    @Override
    public void cancel() {
      synchronized (MutableStateOf.this) {
        Map<Subscription, Consumer<? super T>> copy =
            new HashMap<>(MutableStateOf.this.subscriptions);
        copy.remove(this);
        MutableStateOf.this.subscriptions = copy;
      }
    }

    @Override
    public boolean isCanceled() {
      return !MutableStateOf.this.subscriptions.containsKey(this);
    }
  }
}
