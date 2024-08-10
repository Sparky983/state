package me.sparky983.state;

import java.util.function.Consumer;
import java.util.function.Function;
import org.jspecify.annotations.Nullable;

/**
 * An {@code Observer} is something that emits values.
 *
 * @param <T> the type of the values
 * @since 0.2
 */
public interface Observer<T extends @Nullable Object> {
  /**
   * Maps this with the given mapping function.
   *
   * @param mapper the mapping function
   * @return the new observer
   * @param <R> the type of the new observer's values
   * @throws NullPointerException if the mapper is {@code null}.
   * @since 0.2
   */
  <R extends @Nullable Object> Observer<R> map(Function<? super T, ? extends R> mapper);

  /**
   * Creates a new subscription and emits values to the given subscriber by calling its
   * {@link Consumer#accept(Object)} method.
   *
   * <p>Once the subscriber has been subscribed, it will receive events indefinitely until
   * {@linkplain Subscription#cancel() canceled}.
   *
   * @param subscriber the given subscriber
   * @return the subscription
   * @throws NullPointerException if the subscriber is {@code null}.
   * @since 0.2
   */
  Subscription subscribe(Consumer<? super T> subscriber);
}
