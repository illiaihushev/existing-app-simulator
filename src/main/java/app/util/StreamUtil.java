package app.util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collector.Characteristics.UNORDERED;

public class StreamUtil {

    public static <T> Collector<List<T>, List<T>,  List<T>> concatLists() {
        return new Collector<List<T>, List<T>, List<T>>() {

            @Override
            public Supplier<List<T>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<List<T>, List<T>> accumulator() {
                return List::addAll;
            }

            @Override
            public BinaryOperator<List<T>> combiner() {
                return (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                };
            }

            @Override
            public Function<List<T>, List<T>> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return  unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
            }
        };
    }

    public static <T> Collector<Collection<T>, Set<T>,  Set<T>> concatCollectionsToSet() {
        return new Collector<Collection<T>, Set<T>,  Set<T>>() {

            @Override
            public Supplier<Set<T>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<T>, Collection<T>> accumulator() {
                return Set::addAll;
            }

            @Override
            public BinaryOperator<Set<T>> combiner() {
                return (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                };
            }

            @Override
            public Function<Set<T>, Set<T>> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return  unmodifiableSet(EnumSet.of(IDENTITY_FINISH, UNORDERED));
            }
        };
    }
}
