package exercicio;

// import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert // gera sql de insert com apenas os campos modificados
@DynamicUpdate // gera sql de update com apenas os campos modificados
@Table(name = "product")
public class Product {
	private Long id;
	private String name;
	private double price;
	// private Date createdAt;
	// private Date updatedAt;

	public Product() {
	}

	public Product(String name,
			double price) {
		this.name = name;
		this.price = price;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column()
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Column()
	public double getPrice() {
		return price;
	}

	// @Column()
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// public Date getCreatedAt() {
	// return createdAt;
	// }

	// @Column()
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// public Date getUpdatedAt() {
	// return updatedAt;
	// }

	@Transient
	public String getFormattedPrice() {
		return Util.doubleToStr(price);
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + name + ", preco=R$" + price + "]";
	}

	// @SuppressWarnings("unused")
	// private void setCreatedAt(Date createdAt) {
	// this.createdAt = createdAt;
	// }

	// @SuppressWarnings("unused")
	// private void setUpdatedAt(Date updatedAt) {
	// this.updatedAt = updatedAt;
	// }
}
