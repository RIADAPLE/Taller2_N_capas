package com.uca.spring.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uca.spring.model.Vehiculo;

public interface VehiculoRepository extends JpaRepository <Vehiculo, Integer> {

	@Query("SELECT x "
            + " FROM Vehiculo x "
            + "WHERE"  
            + "  (:idCarro is null or :idCarro = x.idCarro )   "
            + " and (:marca is null or x.marca = :marca ) "
            + " and (:placa is null or x.placa = :placa ) "
            + " and (:color is null or x.color = :color ) "
            + " and (:modelo is null or x.modelo = :modelo ) "            
           )
	Page<Vehiculo> findByFilters(Pageable page  ,@Param("idCarro")  Integer idCarro ,@Param("marca")  String marca ,@Param("placa")  String placa ,@Param("color")  String color ,@Param("modelo")  String modelo);
	

	 @Query(value ="SELECT  x.idCarro  ,  x.marca  ,  x.placa  ,  x.color ,  x.modelo "
	            + " FROM vehiculo x "
	            + "WHERE"  
	            + "  (:idCarro is null or :idCarro = x.idCarro )   "
	            + " and (:marca is null or x.marca = :marca ) "
	            + " and (:placa is null or x.placa = :placa ) "
	            + " and (:color is null or x.color = :color ) "
	            + " and (:modelo is null or x.modelo = :modelo ) "   
	            + " ORDER BY x.idCarro ASC ",nativeQuery=true)
	 List<Object[]> findByFilters(@Param("idCarro")  Integer idCarro ,@Param("marca")  String marca ,@Param("placa")  String placa ,@Param("color")  String color ,@Param("modelo")  String modelo);
	
	

}
