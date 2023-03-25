package util;

public record Pair<T, U>(T first, U second) {

    public T first() {
        return first;
    }

    public U second() {
        return second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
