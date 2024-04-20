package hr.fer.zemris.java.custom.scripting.exec;

import java.util.*;

public class ObjectMultistack {

    private Map<String, MultistackEntry> map;

    public ObjectMultistack() {
        this.map = new HashMap<>();
    }

    public void push(String keyName, ValueWrapper valueWrapper) {
        MultistackEntry entry = new MultistackEntry(valueWrapper, map.get(keyName));
        map.put(keyName, entry);
    }

    public ValueWrapper pop(String keyName) {
        if (this.isEmpty(keyName)) 
            throw new IllegalArgumentException("Stack is empty...");
        MultistackEntry entry = map.get(keyName);
        map.put(keyName, entry.next);
        return entry.valueWrapper;
    }

    public ValueWrapper peek(String keyName) {
        if (this.isEmpty(keyName)) {
            throw new IllegalArgumentException("Stack is empty...");
        }
        return map.get(keyName).valueWrapper;
    }
    
    public boolean isEmpty(String keyName) {
        return map.containsKey(keyName) && map.get(keyName) == null;
    }


    static class MultistackEntry {

        private ValueWrapper valueWrapper;
        private MultistackEntry next;

        public MultistackEntry(ValueWrapper valueWrapper, MultistackEntry next) {
            this.valueWrapper = valueWrapper;
            this.next = next;
        }
    }

}
