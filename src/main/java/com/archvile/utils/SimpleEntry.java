package com.archvile.utils;

import java.util.Map;

public final class SimpleEntry<Key, Value> implements Map.Entry<Key, Value> {

    private final Key key;
    private Value value;
	
    public SimpleEntry(Key key, Value value) {
        this.key = key;
        this.value = value;
    }
	
	@Override
	public Key getKey() {
		return key;
	}

	@Override
	public Value getValue() {
		return value;
	}

	@Override
	public Value setValue(Value value) {
        Value old = this.value;
        this.value = value;
        return old;
	}

}