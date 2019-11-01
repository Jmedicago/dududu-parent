package cn.org.dududu.core.utils;

import java.io.Serializable;

public class KV<K, V> implements Serializable {
    private K k;

    private V v;

    public KV(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    @Override
    public int hashCode() {
        int result = 17;
        int c = 0;
        if (k instanceof Boolean) {
            c = (Boolean) k ? 1 : 0;
            return 37 * result + c;
        }
        if (k instanceof Byte || k instanceof Character || k instanceof Short || k instanceof Integer) {
            c = (Integer) k;
            return 37 * result + c;
        }
        if (k instanceof Long) {
            long temp = (Long) k;
            c = (int) (temp ^ (temp >>> 32));
            return 37 * result + c;
        }
        if (k instanceof Float) {
            float temp = (Float) k;
            c = Float.floatToIntBits(temp);
            return 37 * result + c;
        }
        if (k instanceof Double) {
            long temp = Double.doubleToLongBits((Double) k);
            c = (int) (temp ^ (temp >>> 32));
            return 37 * result + c;
        }
        if (k instanceof Object) {
            c = k.hashCode();
            return 37 * result + c;
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj || !(obj instanceof KV)) return false;
        return this.getK().equals(((KV<?, ?>) obj).getK());
    }

    public String toString() {
        return "{\"k\":\"" + k + "\",\"v\":\"" + v + "\"}";
    }
}
