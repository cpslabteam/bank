package cpslabteam.bank.database.objects;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.NaturalId;

@Entity(name = "Branch")
public class Branch extends BaseDataObject {

	@Column(name = "name", nullable = false)
	@NaturalId
	private String name;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "assets", precision = 20, scale = 2, columnDefinition = "NUMERIC(20,2)", nullable = false)
	private BigDecimal assets;

	public Branch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getAssets() {
		return assets;
	}

	public void setAssets(BigDecimal assets) {
		this.assets = assets;
	}

}
