public class Pair extends AbstractPair{
    public Pair(String key, String value){
        super(key, value);
    }
    public void setKey(String key){
        this.key = key;
    }
    public void setValue(String value){
        this.value = value;
    }
    public String getKey(){
        return this.key;
    }
    public String getValue(){
        return this.value;
    }
    public String getKeyValue(){
        if(this.key.isEmpty() && this.value.isEmpty()){
            return null;
        }
        return this.key+","+this.value;
    }
}
