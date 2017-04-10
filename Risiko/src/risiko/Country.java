package risiko;


public class Country {
    
    private final String name;
    private int armies;
    
    public Country(String name){
        this.name= name;
    }
  
    public String getName(){
        return this.name;
    }
    
    public void setArmies(int armies){
        this.armies = armies;
    }
    
    public int getArmies(){
        return this.armies;
    }
    
    public void removeArmies(int armies){
       this.armies-= armies;
    }
    
    public void addArmies(int armies){
        this.armies+=armies;
    }
    
    public boolean isConquered(){
        return (armies<=0);
    }
}
