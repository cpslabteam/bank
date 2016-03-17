package cpslabteam.bank.database.objects;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonView;

import cpslabteam.bank.jsonserialization.JSONViews;

@Entity(name = "Branch")
public class Branch extends BaseDataObject {

	@JsonView(JSONViews.Info.class)
	@Column(name = "name", nullable = false)
	@NaturalId (mutable = true)
	private String name;

	@JsonView(JSONViews.Info.class)
	@Column(name = "city", nullable = false)
	private String city;

	@JsonView(JSONViews.Details.class)
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
