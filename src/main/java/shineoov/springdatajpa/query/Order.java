package shineoov.springdatajpa.query;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "QueryOrder")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private int orderAmount;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;




}
