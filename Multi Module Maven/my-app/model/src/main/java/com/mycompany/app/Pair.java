package com.mycompany.app;

public class Pair extends AbstractPair {
    public Pair(String key, String value)  {
        super(key, value);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
    
    public String getKeyValue() {
        if (this.key.isEmpty() && this.value.isEmpty()) {
            return null;
        }
        return this.key+","+this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair p = (Pair) o;
        if (this.key.equals(p.getKey()) && this.value.equals(p.getValue())){
            return true;
        }
        return false;
    }
}
