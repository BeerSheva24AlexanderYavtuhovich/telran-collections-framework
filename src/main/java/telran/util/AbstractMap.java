package telran.util;

public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected Set<Entry<K, V>> set;

    protected abstract Set<K> getEmptyKeySet();

    @Override
    public V get(Object key) {
        Entry<K, V> entry = set.get(key);
        V res = null;
        if (entry != null) {
            res = entry.getValue();
        }
        return res;
    }

    @Override
    public V put(K key, V value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

    @Override
    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsKey'");
    }

    @Override
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsValue'");
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = getEmptyKeySet();
        // TODO
        // foreach
        // keyset add
        // should be exactly type of set
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    @Override
    public Collection<V> values() {
        // TODO
        // any collection that allows duplicate
        // LinkedList fr example
        throw new UnsupportedOperationException("Unimplemented method");
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

}
