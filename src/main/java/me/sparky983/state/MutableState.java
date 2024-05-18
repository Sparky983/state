package me.sparky983.state;

import org.jspecify.annotations.Nullable;

/**
 * An extension of {@link State} that allows for programmatic modification of the underlying value.
 *
 * <p>Implementations must be thread-safe.
 *
 * @param <T> the type of the state
 * @see State
 * @since 0.1
 */
public interface MutableState<T extends @Nullable Object> extends State<T> {
  /**
   * Returns a new {@code MutableState} with the given initial value.
   *
   * @param initialValue the initial value
   * @return the {@code MutableState}
   * @param <T> the type of the state
   * @since 0.1
   */
  static <T extends @Nullable Object> MutableState<T> of(T initialValue) {
    return new MutableStateOf<>(initialValue);
  }

  /**
   * Sets the value of this state. All non-cancelled subscribers will be notified of the change.
   *
   * @param value the new value
   * @since 0.1
   */
  void set(T value);
}
