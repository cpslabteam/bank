package cpslabteam.bank.database.objects;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonView;

import cpslabteam.bank.jsonserialization.JsonViews;

@Entity(name = "Branch")
public class Branch extends BaseDataObject {

	@JsonView(JsonViews.Info.class)
	@Column(name = "name", nullable = false)
	@NaturalId(mutable = true)
	private String name;

	@JsonView(JsonViews.Info.class)
	@Column(name = "city", nullable = false)
	private String city;

	@JsonView(JsonViews.Details.class)
	@Column(name = "assets", precision = 20, scale = 2, columnDefinition = "NUMERIC(20,2)", nullable = false)
	private BigDecimal assets;

	public Branch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getAssets() {
		return assets;
	}

	public String getCity() {
		return city;
	}

	public String getName() {
		return name;
	}

	public void setAssets(BigDecimal assets) {
		this.assets = assets;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Branch [id=" + getId() + ", name=" + name + ", city=" + city + ", assets=" + assets + "]";
	}

}
