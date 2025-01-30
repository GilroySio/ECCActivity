public abstract class AbstractPair{
    protected String key;
    protected String value;
    public abstract void setKey(String key);
    public abstract void setValue(String value);
    public abstract String getKey();
    public abstract String getValue();
    public AbstractPair(String key, String value){
        this.key = key;
        this.value = value;
    }
}
