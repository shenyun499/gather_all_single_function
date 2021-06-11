package org.example.datastructure.com.design.prototype;

public class Key implements Comparable<Key> {
	private String name;

	public Key(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "Key [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Key o) {
		//名字相同，不添加
		if (this.name.equals(o.name))
			return 0;
		//根据名字长读排序
		return o.name.length() - this.name.length();
	}
}
