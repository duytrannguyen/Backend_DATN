package com.poly.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
public class Sellers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private int sellersId;

    @Column(name = "shop_name")
    @Nationalized
    private String shopName;

    @Column(name = "shop_avatar")
    private String avatarShop;

    @Column(name = "background")
    private String background;

    @Column(name = "type_business")
    private String typeBusiness;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "identity_card")
    private String identityCard;

    @Column(name = "image_front_identity_card")
    private String imageFrontIdentityCard;

    @Column(name = "image_back_identity_card")
    private String imageBackIdentityCard;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User usersId;


}
