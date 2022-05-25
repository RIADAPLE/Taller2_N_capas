package com.uca.spring.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {
	String name;
	String extension;
	byte[] archivo;
	
	public String getName() {
	  	 return this.name;
	}   
	    
	 public String getExtension() {
	  	 return this.extension;
	 }
	 
	 public byte[] getArchivo() {
	  	 return this.archivo;
	 }
	    
	 public void setName(String newName) {
		 newName = name;
	 }
	 public void setExtension(String newExtension) {
		 newExtension = extension;
	 }
	 public void setArchivo(byte[] newArchivo) {
		 newArchivo = archivo;
	 }

}
