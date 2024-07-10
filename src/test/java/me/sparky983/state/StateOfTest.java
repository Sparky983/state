package me.sparky983.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StateOfTest {
  static int TIMES = 1000;
  static int THREADS = 10;

  State<Integer> state;

  @BeforeEach
  void setUp() {
    this.state = State.of(0);
  }

  @Test
  void testSubscription() {
    List<Integer> values = new ArrayList<>();
    this.state.subscribe(values::add);

    assertEquals(Collections.singletonList(0), values);

    this.state.set(1);

    assertEquals(Arrays.asList(0, 1), values);
  }

  @Test
  void testCancelSubscription() {
    List<Integer> values = new ArrayList<>();
    this.state.subscribe(values::add).cancel();

    this.state.set(1);

    assertEquals(Collections.singletonList(0), values);
  }

  @Test
  void testMap() {
    List<Integer> regularValues = new ArrayList<>();
    this.state.subscribe(regularValues::add);

    State<String> map = this.state.map(String::valueOf);
    List<String> mapValues = new ArrayList<>();
    map.subscribe(mapValues::add);

    this.state.set(1);
    map.set("2");

    assertEquals(Arrays.asList(0, 1), regularValues);
    assertEquals(Arrays.asList("0", "1", "2"), mapValues);
  }

  @Test
  void testSubscribeAndCancelFromDifferentThreads() throws InterruptedException {
    ExecutorService pool = Executors.newFixedThreadPool(THREADS);

    AtomicInteger subscriptions = new AtomicInteger(0);
    for (int i = 0; i < TIMES; i++) {
      pool.submit(() -> {
        Subscription subscription = this.state.subscribe(state -> subscriptions.incrementAndGet());
        subscription.cancel();
      });
    }

    pool.shutdown();
    assertTrue(pool.awaitTermination(1, TimeUnit.SECONDS), "pool failed to terminate");
    this.state.set(1);

    assertEquals(TIMES, subscriptions.get());
  }
}
