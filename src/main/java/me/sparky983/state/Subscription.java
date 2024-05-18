package me.sparky983.state;

/**
 * A {@code Subscription} is used to cancel a subscription to a {@link State}.
 *
 * @since 0.1
 */
public interface Subscription {
  /**
   * Cancels this subscription.
   *
   * <p>Canceling an already canceled subscription has no effect.
   *
   * @since 0.1
   */
  void cancel();

  /**
   * Returns whether this subscription has been canceled.
   *
   * @since 0.1
   */
  boolean isCanceled();
}
