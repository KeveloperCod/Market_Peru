package com.cibertec.servicelmplement;

import com.cibertec.model.DetalleVenta;
import com.cibertec.model.Producto;
import com.cibertec.model.Venta;
import com.cibertec.repository.ProductoRepository;
import com.cibertec.repository.VentaRepository;
import com.cibertec.service.VentaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    private static final Logger logger = LoggerFactory
    		.getLogger(VentaServiceImpl.class);

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    public Venta registrarVenta(Venta venta) {
        logger.info("Verificando los detalles de la venta...");
        
        // Verificamos si los detalles de la venta están presentes
        if (venta.getDetalleVenta() == null || venta.getDetalleVenta().isEmpty()) {
            logger.error("Los detalles de la venta no fueron proporcionados.");
            throw new RuntimeException("Los detalles de la venta no fueron proporcionados.");
        }

        // Listamos los ID de los productos en la venta para evitar consultas repetidas
        List<Integer> productIds = venta.getDetalleVenta().stream()
                .map(detalle -> detalle.getProducto() != null ? detalle.getProducto().getIdProducto() : null)
                .collect(Collectors.toList());

        // Asegúrate de que el arreglo de IDs no esté vacío ni contenga valores nulos
        if (productIds.contains(null) || productIds.isEmpty()) {
            logger.error("Algunos productos tienen ID nulo o no se proporcionaron productos.");
            throw new RuntimeException("Algunos productos tienen ID nulo o no se proporcionaron productos.");
        }

        // Convierte la lista de IDs en una cadena separada por comas
        String productIdsStr = productIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        
        logger.info("IDs de productos en venta: {}", productIdsStr);

        // Usamos la consulta personalizada para obtener los productos de la base de datos
        List<Producto> productos = productoRepository.findProductosByIds(productIdsStr); // Asegúrate de que este método reciba el string correctamente

        if (productos.size() != productIds.size()) {
            logger.error("Algunos productos no se encuentran en la base de datos.");
            throw new RuntimeException("Algunos productos no se encuentran en la base de datos.");
        }

        // Asignamos el producto correspondiente a cada detalle de la venta
        for (DetalleVenta detalle : venta.getDetalleVenta()) {
            logger.info("Procesando detalle de venta con producto ID: {}", detalle.getProducto().getIdProducto());
            Producto producto = productos.stream()
                .filter(p -> p.getIdProducto() == detalle.getProducto().getIdProducto())
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Producto con ID {} no encontrado.", detalle.getProducto().getIdProducto());
                    return new RuntimeException("Producto con ID " + detalle.getProducto().getIdProducto() + " no encontrado.");
                });

            if (!producto.isEsActivo()) {
                logger.error("Producto con ID {} está inactivo.", detalle.getProducto().getIdProducto());
                throw new RuntimeException("Producto con ID " + detalle.getProducto().getIdProducto() + " está inactivo.");
            }

            detalle.setProducto(producto);
            detalle.setVenta(venta);  // Asociamos el detalle a la venta

            // Calculamos el total del detalle
            BigDecimal totalDetalle = detalle.getPrecio().multiply(new BigDecimal(detalle.getCantidad()));
            detalle.setTotal(totalDetalle);
            logger.info("Detalle de venta actualizado: Producto ID: {}, Total: {}", detalle.getProducto().getIdProducto(), totalDetalle);

            // Aseguramos que el detalle se agregue a la lista de detalles de la venta
            venta.addDetalleVenta(detalle);
        } 

        // Calculamos el total de la venta
        BigDecimal totalVenta = venta.getDetalleVenta().stream()
                .map(DetalleVenta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        venta.setTotal(totalVenta);
        logger.info("Total de la venta calculado: {}", totalVenta);

        // Guardamos la venta en la base de datos
        return ventaRepository.save(venta);
    }



    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }
}
