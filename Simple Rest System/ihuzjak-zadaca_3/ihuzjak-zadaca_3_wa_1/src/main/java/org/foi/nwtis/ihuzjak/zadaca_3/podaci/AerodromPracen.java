package org.foi.nwtis.ihuzjak.zadaca_3.podaci;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor()
public class AerodromPracen {
	
	@Getter
    @Setter 
    @NonNull 
	String ident;
	
	@Getter
    @Setter 
    @NonNull 
	String stored;
}
