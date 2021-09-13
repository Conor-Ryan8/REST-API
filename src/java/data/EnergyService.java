package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnergyService 
{
    private Connection conn = null;
    
    public EnergyService() 
    {
        try 
        {           
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/EnergyData",
                    "conor",
                    "password");
            System.out.println("Connected to Database");
        } 
        catch (Exception e) 
        {
            System.err.println("Exception");
            e.printStackTrace();
        }
    }
    
    public EnergyReading getReading(String time)
    {
        EnergyReading reading = null;        
        try 
        {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM APP.UNIT1 WHERE (TIME = ?)");
            ps.setString(1, time);
            ResultSet result = ps.executeQuery();
            
            if (!result.next()) 
            {
                return null;
            }
            else
            {               
                reading = new EnergyReading(
                        result.getString("TIME"),
                        result.getInt("VOLTAGE"),
                        result.getFloat("AMPERAGE"),
                        result.getFloat("POWERFACTOR"),
                        result.getDouble("KILOWATTHOURS"));
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("getReadingInfo Error");
            e.printStackTrace();
        }
        return reading;
    }
    
    public ArrayList<EnergyReading> getAllReadings() 
    {
        ArrayList<EnergyReading> readings = new ArrayList<>();
        try 
        {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM APP.UNIT1");

            ResultSet result = ps.executeQuery();
            while (result.next()) 
            {
                EnergyReading reading = new EnergyReading(
                        result.getString("TIME"),
                        result.getInt("VOLTAGE"),
                        result.getFloat("AMPERAGE"),
                        result.getFloat("POWERFACTOR"),
                        result.getDouble("KILOWATTHOURS"));

                readings.add(reading);
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("getAllReadings Error");
            e.printStackTrace();
        }
        return readings;
    }
    
    public void deleteReading(String time) 
    {
        try 
        {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM APP.UNIT1 WHERE (TIME = ?)");
            ps.setString(1, time);
            ps.executeUpdate();

        } 
        catch (SQLException e) 
        {
            System.err.println("deleteReading Error");
            e.printStackTrace();
        }
    }
    
    public void deleteAllReadings() 
    {
        try 
        {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM APP.UNIT1");
            ps.execute();            
        } 
        catch (SQLException e) 
        {          
            System.err.println("deleteAllReadings Error");
            e.printStackTrace();
        }
    }

    public boolean addReading(EnergyReading reading) 
    {
        try 
        {
            if (getReading(reading.getTime()) != null) 
            {
                return false;
            } 
            else 
            {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO APP.UNIT1 (TIME, VOLTAGE,AMPERAGE,"
                                + "POWERFACTOR, KILOWATTHOURS) "
                                + "VALUES (?,?,?,?,?)");
                ps.setString(1, reading.getTime());
                ps.setInt(2, reading.getVoltage());
                ps.setFloat(3, reading.getAmperage());
                ps.setFloat(4, reading.getPfactor());
                ps.setDouble(5, reading.getUnits());
                ps.executeUpdate();
            }   
        } 
        catch (SQLException e) 
        {
            System.err.println("addReading Error");
            e.printStackTrace();
            return false;
        }        
        return true;
    }
     
    public boolean updateReading(EnergyReading reading) 
    {
        try 
        {
            if (getReading(reading.getTime()) == null) 
            {
                return false;
            } 
            else 
            { 
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE APP.UNIT1 SET VOLTAGE=?, AMPERAGE=?, "
                                + "POWERFACTOR=?, KILOWATTHOURS=? "
                                + "WHERE (TIME=?)");
                ps.setInt(1, reading.getVoltage());
                ps.setFloat(2, reading.getAmperage());
                ps.setFloat(3, reading.getPfactor());
                ps.setDouble(4, reading.getUnits());
                ps.setString(5, reading.getTime());
                ps.executeUpdate();
            }   
        } 
        catch (SQLException e) 
        {
            System.err.println("updateReading Error");
            e.printStackTrace();
            return false;
        }       
        return true;
    }  
    
    
    //public static void main(String[] args)     
    //{
        //EnergyService testService = new EnergyService();
        //System.out.println(testService.getReading("2020-10-02 12:23:03"));
        //System.out.print(testService.getAllReadings());
        //testService.deleteReading("2020-10-02 12:23:03");
        //System.out.println(testService.getReading("2020-10-02 12:23:03")); 
        //testService.deleteAllReadings();
        //System.out.print(testService.getAllReadings());        
        //EnergyReading testreading = new EnergyReading("2020-12-01 11:49:34",223,23.5f,0.996f,212.24432);
        //testService.addReading(testreading);
        //System.out.print(testService.getAllReadings());   
        //EnergyReading updatedreading = new EnergyReading("2020-12-01 11:49:34",212,23.5f,0.999f,212.11111);
        //testService.updateReading(updatedreading);
        //System.out.print(testService.getAllReadings().getClass());   
    //}     
}