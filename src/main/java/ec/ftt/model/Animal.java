package ec.ftt.model;

import java.util.Objects;

public final class Animal {

	private long id, breed;
	private String name,
	               color;
	
	public Animal() {
		
	}
	public Animal(String id, String name, String breed, String color) {
		super();
		setId(id);
		setName(name);
		setBreed(breed);
		setColor(color);
	}
	
	public Animal(long id, String name, Long breed, String color) {
		super();
		setId(id);
		setName(name);
		setBreed(breed);
		setColor(color);
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		
		if (id.length()==0)
			setId(0);
		else
			setId(Long.valueOf(id));
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the breed
	 */
	public long getBreed() {
		return breed;
	}
	/**
        * @param breed
	 */
	public void setBreed(Long breed) {
		this.breed = breed;
	}
        
        public void setBreed(String breed) {
		
		if (breed.length()==0)
			setBreed(Long.valueOf(0));
		else
			setBreed(Long.valueOf(breed));
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Animal [id=" + id + ", name=" + name + ", breed=" + breed + ", color=" + color + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, breed, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Animal)) {
			return false;
		}
		Animal other = (Animal) obj;
		return Objects.equals(color, other.color) && 
                       Objects.equals(breed, other.breed) && id == other.id &&
                       Objects.equals(name, other.name);
	}
	
	
	
	
}
