package cpslabteam.bank.database.objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonView;

import cpslabteam.bank.jsonserialization.JsonViews;

/**
 * Superclass of all objects that represent a table in the database. This class
 * declares the Identifier field (id) used by all data objects. This field
 * serves as the primary key value for the mapped tables, and is assigned
 * Sequentially by when the entity is persisted.
 */

@MappedSuperclass
public abstract class BaseDataObject {

	@JsonView(JsonViews.Info.class)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "BaseDataObject [id=" + id + "]";
	}

}
