package co.aurasphere.interview.server.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 * Represents a technology.
 * 
 * @author Donato Rimenti
 */
public class Technology implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The technology name.
	 */
	@Id
	private String name;

	/**
	 * Instantiates a new Technology.
	 */
	public Technology() {
	}

	/**
	 * Instantiates a new Technology.
	 *
	 * @param name
	 *            the {@link #name}.
	 */
	public Technology(String name) {
		this.name = name;
	}

	/**
	 * Gets the {@link #name}.
	 *
	 * @return the {@link #name}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the {@link #name}.
	 *
	 * @param name
	 *            the new {@link #name}.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Technology other = (Technology) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Technology [name=" + name + "]";
	}

}
