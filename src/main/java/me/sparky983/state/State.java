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
public interface State<T extends @Nullable Object> extends Observer<T> {
  /**
   * Returns the current value of this state.
   *
   * @since 0.1
   */
  T get();

  /**
   * {@inheritDoc}
   *
   * @param mapper {@inheritDoc}
   * @return new state
   * @param <R> {@inheritDoc}
   * @throws NullPointerException {@inheritDoc}
   * @since 0.1
   */
  @Override
  <R extends @Nullable Object> State<R> map(Function<? super T, ? extends R> mapper);

  /**
   * {@inheritDoc}
   *
   * <p>The latest value is instantly replayed.
   *
   * @param subscriber {@inheritDoc}
   * @return {@inheritDoc}
   * @throws NullPointerException {@inheritDoc}
   * @since 0.1
   */
  @Override
  Subscription subscribe(Consumer<? super T> subscriber);
}
