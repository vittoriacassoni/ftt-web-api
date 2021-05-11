package ec.ftt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ec.ftt.model.Animal;
import ec.ftt.util.DBUtil;
import java.util.HashMap;
import java.util.Map;

public class AnimalDao {

    private Connection connection;

    public AnimalDao() {
        connection = DBUtil.getConnection();
    } 

    public void addAnimal(Animal animal) {
        
    	try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO ftt.ANIMAL (NAME, BREED, COLOR) VALUES (?, ?, ?)");
            
            // Parameters start with 1
            preparedStatement.setString(1, animal.getName());
            preparedStatement.setLong(2, animal.getBreed());
            preparedStatement.setString(3, animal.getColor());
        
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    
    public void deleteAnimal(Long id) {
    	
    	Animal animal = new Animal();
    	animal.setId(id);
    	
    	deleteAnimal(animal);
    	
    }

    public void deleteAnimal(Animal animal) {
        try {
            
        	PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM ftt.ANIMAL WHERE ID=?");
            
            // Parameters start with 1
            preparedStatement.setLong(1, animal.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 

    public void updateAnimal(Animal animal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE ftt.ANIMAL SET NAME=?, BREED=?, " +
                    		       "COLOR=? WHERE ID=?");
            
            // Parameters start with 1
            preparedStatement.setString(1, animal.getName());
            preparedStatement.setLong(2, animal.getBreed());
            preparedStatement.setString(3, animal.getColor());
            
            preparedStatement.setLong(4, animal.getId());
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 

    public List<Animal> getAllAnimal() throws SQLException {
        
    	List<Animal> animalList = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM ftt.ANIMAL";
            PreparedStatement add = connection.prepareStatement(query);

            ResultSet rs = add.executeQuery();     
            while (rs.next()) {
                
            	Animal animal = new Animal();
                
            	animal.setId(rs.getLong("ID"));
                animal.setName(rs.getString("NAME"));
                animal.setBreed(rs.getLong("BREED"));
                animal.setColor(rs.getString("COLOR"));

                animalList.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animalList;
    } 

    public Animal getAnimalById(Long id) {
    	
    	Animal animal = new Animal();
    	animal.setId(id);
    	
    	return getAnimalById(animal);
    	
    } 
    
    
    	
    public Animal getAnimalById(Animal animal) {

    	Animal animalOutput = null;
        
    	try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * from ftt.ANIMAL WHERE ID=?");
            
            preparedStatement.setLong(1, animal.getId());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                animalOutput = new Animal();
            	animalOutput.setId(rs.getLong("ID"));
            	animalOutput.setName(rs.getString("NAME"));
            	animalOutput.setBreed(rs.getLong("BREED"));
            	animalOutput.setColor(rs.getString("COLOR"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animalOutput;
    } 
    
    public String getDbDate() {
    	String output="";
    	
    	try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT now() NOW");
            
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	output = rs.getString("NOW");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }
    
    public List<Integer> CountBreedAnimals(){
        Map<Integer, Integer> breeds = new HashMap<>();
        breeds.put(1,0);
        breeds.put(2,0);
        breeds.put(3,0);
        breeds.put(4,0);
        breeds.put(5,0);
        breeds.put(6,0);
        breeds.put(7,0);
        
        List<Integer> breedList = new ArrayList<>();
        try {
            String query = "SELECT count(breed), breed FROM ftt.ANIMAL GROUP BY breed ORDER BY breed";
            PreparedStatement add = connection.prepareStatement(query);

            ResultSet rs = add.executeQuery();
            
            while (rs.next()) {
                if(breeds.containsKey(rs.getInt("breed"))){
                    breeds.put(rs.getInt("breed"), rs.getInt("count(breed)"));
                }
            }
            
            for(int i = 1; i <= breeds.size(); i++){
                breedList.add(breeds.get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return breedList;
    }
} 