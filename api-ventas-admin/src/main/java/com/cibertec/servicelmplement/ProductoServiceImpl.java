package com.cibertec.servicelmplement;

import com.cibertec.dto.ProductCategoriaDTO;
import com.cibertec.dto.ProductRequest;
import com.cibertec.dto.ProductoCategoriaDTO;
import com.cibertec.model.Producto;
import com.cibertec.repository.ProductoRepository;
import com.cibertec.service.ProductoService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    @PersistenceContext
    private EntityManager entityManager;
    
    
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoById(int id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.orElse(null);
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(int id, Producto producto) {
        if (productoRepository.existsById(id)) {
            producto.setIdProducto(id);
            return productoRepository.save(producto);
        }
        return null;
    }

    @Override
    public boolean deleteProducto(int id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /*
    @Override
    public List<ProductoCategoriaDTO> listarProductosDesdeSP() {
        return productoRepository.listarProductosXCategoria();
    }
    */
    

	@Override
	public List<ProductCategoriaDTO> listarProductosXCategoria() {
	    StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ListarProductosXCategoria");
	    List<Object[]> result = query.getResultList();
	    List<ProductCategoriaDTO> lista = new ArrayList<>();
	    for (Object[] row : result) {
	        lista.add(new ProductCategoriaDTO(row));
	    }
	    return lista;
	}
	
	@Override
	public void registrarProducto(ProductRequest producto) {
	    StoredProcedureQuery query = entityManager.createStoredProcedureQuery("RegistrarProducto");
	    query.registerStoredProcedureParameter("p_nombre", String.class, jakarta.persistence.ParameterMode.IN);
	    query.registerStoredProcedureParameter("p_idCategoria", Integer.class, jakarta.persistence.ParameterMode.IN);
	    query.registerStoredProcedureParameter("p_stock", Integer.class, jakarta.persistence.ParameterMode.IN);
	    query.registerStoredProcedureParameter("p_precio", Double.class, jakarta.persistence.ParameterMode.IN);

	    query.setParameter("p_nombre", producto.nombre);
	    query.setParameter("p_idCategoria", producto.idCategoria);
	    query.setParameter("p_stock", producto.stock);
	    query.setParameter("p_precio", producto.precio);

	    query.execute();
	}

	@Override
	public void actualizarProducto(Integer idProducto, ProductRequest producto) {
	    StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ActualizarProducto");
	    query.registerStoredProcedureParameter("p_idProducto", Integer.class, jakarta.persistence.ParameterMode.IN);
	    query.registerStoredProcedureParameter("p_nombre", String.class, jakarta.persistence.ParameterMode.IN);
	    query.registerStoredProcedureParameter("p_idCategoria", Integer.class, jakarta.persistence.ParameterMode.IN);
	    query.registerStoredProcedureParameter("p_stock", Integer.class, jakarta.persistence.ParameterMode.IN);
	    query.registerStoredProcedureParameter("p_precio", Double.class, jakarta.persistence.ParameterMode.IN);

	    query.setParameter("p_idProducto", idProducto);
	    query.setParameter("p_nombre", producto.nombre);
	    query.setParameter("p_idCategoria", producto.idCategoria);
	    query.setParameter("p_stock", producto.stock);
	    query.setParameter("p_precio", producto.precio);

	    query.execute();
	}

	@Override
	public void desactivarProducto(Integer idProducto) {
	    StoredProcedureQuery query = entityManager.createStoredProcedureQuery("DesactivarProducto");
	    query.registerStoredProcedureParameter("p_idProducto", Integer.class, jakarta.persistence.ParameterMode.IN);
	    query.setParameter("p_idProducto", idProducto);
	    query.execute();
	}
}