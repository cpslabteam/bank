package cpslabteam.bank.database.objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Superclass of all objects that represent a table in the database. This class
 * declares the Identifier field (id) that is required by Hibernate. This field
 * serves as the primary key value for the mapped tables, and is assigned
 * Sequentially by Hibernate when the entity is persisted.
 */
@MappedSuperclass
public abstract class BaseDataObject {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	public Long getId() {
		return id;
	}
}
