package com.contactregistry.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "contacts") 
public class Contact {  

    @Id
    @Column(name = "id")
    private Long id;  

    @Column(name = "full_name")
    private String fullName;  

    @Column(name = "phone_number")
    private String phoneNumber;  

    @Column(name = "email")
    private String email;  

    @Column(name = "id_number")
    private String idNumber;  

    @Column(name = "date_of_birth")
    private String dateOfBirth;  

    @Column(name = "gender")
    private String gender;  

    @Column(name = "organization")
    private String organization;  

    @Column(name = "masked_name")
    private String maskedName;  

    @Column(name = "masked_phone")
    private String maskedPhone;  

    @Column(name = "hashed_phone")
    private String hashedPhone;  
}
