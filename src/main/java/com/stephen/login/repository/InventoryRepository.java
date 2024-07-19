package com.stephen.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stephen.login.entity.Film;
import com.stephen.login.entity.Inventory;
import com.stephen.login.entity.Store;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	@Query("SELECT i.film FROM Inventory i WHERE i.store = ?1 GROUP BY i.film")
	List<Film> findByStoreId(Store store);
	
}
