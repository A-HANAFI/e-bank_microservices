package net.ahmed.bank.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
@Table(name = "accounts")
public class Accounts extends  BaseEntity{

    @Column(name = "customer_id")
    private long customerId;

    @Id
    @Column(name = "account_number")
    private Long  accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "phone_number")
    private String  phoneNumber;

    @Column(name = "branch_address")
    private String branchAddress;


}
