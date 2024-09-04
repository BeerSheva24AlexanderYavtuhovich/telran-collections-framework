package telran.util;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected Set<Entry<K, V>> set;

    protected abstract Set<K> getEmptyKeySet();

    @Override
    public V get(Object key) {
        Entry<K, V> pattern = new Entry<>((K) key, null);
        Entry<K, V> entry = set.get(pattern);
        V res = null;
        if (entry != null) {
            res = entry.getValue();
        }
        return res;
    }

    @Override
    public V put(K key, V value) {
        Entry<K, V> entryToAdd = new Entry<>(key, value);
        Entry<K, V> existingEntry = set.get(entryToAdd);
        V res = null;

        if (existingEntry != null) {
            res = existingEntry.getValue();
            existingEntry.setValue(value);
        } else {
            set.add(entryToAdd);
        }
        return res;
    }

    @Override
    public boolean containsKey(Object key) {
        Entry<K, V> pattern = new Entry<>((K) key, null);
        return set.contains(pattern);
    }

    @Override
    public boolean containsValue(Object value) {
        Iterator<Entry<K, V>> iterator = set.iterator();
        boolean found = false;
        while (iterator.hasNext() && !found) {
            Entry<K, V> entry = iterator.next();
            V entryValue = entry.getValue();
            if (value == null) {
                found = entryValue == null; 
            } else {
                found = value.equals(entryValue); 
            }
        
        }
        return found;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = getEmptyKeySet();
        for (Entry<K, V> entry : set) { 
            keySet.add(entry.getKey()); 
        }
        return keySet;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    @Override
    public Collection<V> values() {
            Collection<V> collection = new LinkedList<>(); 
            for (Entry<K, V> entry : set) { 
                collection.add(entry.getValue()); 
            }
            return collection;
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
