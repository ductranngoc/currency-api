package com.cathay.currency.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    public Currency(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
    
	@Id
    private String code;  
    private String quote;  
    private String rate;
    private String date;
    private String name;
    
    
}
