package me.sparky983.state;

import java.util.function.Consumer;
import java.util.function.Function;
import org.jspecify.annotations.Nullable;

/**
 * A {@code State} is a value that can be observed changing over time.
 *
 * <p>This interface defines the read-only aspect of state, but a writable version is also provided
 * through the {@link MutableState} interface.
 *
 * @param <T> the type of the state
 * @see MutableState
 * @since 0.1
 */
public interface State<T extends @Nullable Object> {
  /**
   * Returns the current value of this state.
   *
   * @since 0.1
   */
  T get();

  /**
   * Maps this with the given mapping function.
   *
   * @param mapper the mapping function
   * @return the new state
   * @param <R> the type of the new state
   * @throws NullPointerException if the mapper is {@code null}.
   * @since 0.1
   */
  <R extends @Nullable Object> State<R> map(Function<? super T, ? extends R> mapper);

  /**
   * Creates a new subscription and emits values to the given subscriber by calling its
   * {@link Consumer#accept(Object)} method.
   *
   * <p>The latest value is instantly replayed.
   *
   * <p>Once the subscriber has been subscribed, it will receive events indefinitely until
   * {@linkplain Subscription#cancel() canceled}.
   *
   * @param subscriber the given subscriber
   * @return the subscription
   * @throws NullPointerException if the subscriber is {@code null}.
   * @since 0.1
   */
  Subscription subscribe(Consumer<? super T> subscriber);
}
