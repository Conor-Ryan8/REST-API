package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="energyreading")
@XmlType(propOrder={"time","voltage","amperage","pfactor","units"})
public class EnergyReading
{
    private String time;
    private int voltage;
    private float amperage;
    private float pfactor;
    private double units;
    
    public EnergyReading(){}
    
    public EnergyReading(String t, int v, float a, float p, double u)
    {
        this.time = t;
        this.voltage = v;
        this.amperage = a;
        this.pfactor = p;
        this.units = u;
    }
    
    @XmlElement
    public String getTime() 
    {
        return time;
    }
    public void setTime(String time) 
    {
        this.time = time;
    }
    
    @XmlElement
    public int getVoltage() 
    {
        return voltage;
    }
    public void setVoltage(int v) 
    {
        this.voltage = v;
    }
    
    @XmlElement
    public float getAmperage() 
    {
        return amperage;
    }
    public void setAmperage(float a) 
    {
        this.amperage = a;
    }
      
    @XmlElement
    public float getPfactor() 
    {
        return pfactor;
    }
    public void setPfactor(float p) 
    {
        this.pfactor = p;
    }
      
    @XmlElement
    public double getUnits() 
    {
        return units;
    }
    public void setUnits(double u) 
    {
        this.units = u;
    }

    @Override
    public String toString() {
        return "EnergyReading{" + "time=" + time + ", voltage=" + voltage + ", amperage=" + amperage + ", pfactor=" + pfactor + ", units=" + units + '}';
    }  
}