package cn.jimyag.util;

/**
 * 二元组
 * @param <A> A
 * @param <B> B
 */
public class TwoTuple<A,B> {
    public final A first;

    public final B second;

    public TwoTuple(A a, B b){
        first = a;
        second = b;
    }

    public TwoTuple() {
        this(null,null);
    }

    @Override
    public String toString() {
        return "cn.jimyag.util.TwoTuple{" +
                "first=" + first +
                ", second=" + second +
                "}";
    }
}
