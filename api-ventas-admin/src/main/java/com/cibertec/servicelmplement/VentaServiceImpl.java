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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    private static final Logger logger = LoggerFactory.getLogger(VentaServiceImpl.class);

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Venta registrarVenta(Venta venta) {

        logger.info("Verificando los detalles de la venta…");

        if (venta.getDetalleVenta() == null || venta.getDetalleVenta().isEmpty()) {
            logger.error("Los detalles de la venta no fueron proporcionados.");
            throw new RuntimeException("Los detalles de la venta no fueron proporcionados.");
        }

        List<Integer> productIds = venta.getDetalleVenta().stream()
            .filter(det -> det.getProducto() != null)
            .map(det -> det.getProducto().getIdProducto())
            .distinct()
            .collect(Collectors.toList());

        if (productIds.isEmpty()) {
            logger.error("No se proporcionaron productos válidos en los detalles de la venta.");
            throw new RuntimeException("No se proporcionaron productos válidos en los detalles de la venta.");
        }

        String idsStr = productIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        logger.info("IDs de productos en venta: {}", idsStr);

        List<Producto> productos = productoRepository.findProductosByIds(idsStr);

        if (productos.size() != productIds.size()) {
            logger.error("Algunos productos no se encuentran en la base de datos.");
            throw new RuntimeException("Algunos productos no se encuentran en la base de datos.");
        }

        for (DetalleVenta detalle : venta.getDetalleVenta()) {
            logger.info("Procesando detalle de venta con producto ID: {}", detalle.getProducto().getIdProducto());

            Producto producto = productos.stream()
                .filter(p -> p.getIdProducto() == detalle.getProducto().getIdProducto())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto con ID "
                        + detalle.getProducto().getIdProducto() + " no encontrado."));

            if (!producto.isEsActivo()) {
                logger.error("Producto con ID {} está inactivo.", producto.getIdProducto());
                throw new RuntimeException("Producto con ID " + producto.getIdProducto() + " está inactivo.");
            }

            detalle.setProducto(producto);
            detalle.setVenta(venta);

            BigDecimal totalDetalle = detalle.getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad()));
            detalle.setTotal(totalDetalle);

            logger.info("Detalle actualizado (Producto {}, Total {})", producto.getIdProducto(), totalDetalle);
        }

        BigDecimal totalVenta = venta.getDetalleVenta().stream()
            .map(DetalleVenta::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        venta.setTotal(totalVenta);
        venta.setFechaRegistro(LocalDateTime.now()); // ⬅️ REGISTRA FECHA Y HORA DE LA VENTA

        logger.info("Total de la venta calculado: {}", totalVenta);
        logger.info("Fecha de la venta: {}", venta.getFechaRegistro());

        return ventaRepository.save(venta);
    }
    
    @Override
    public List<Venta> obtenerVentasPorRangoFecha(String fechaInicio, String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        return ventaRepository.findByFechaRegistroBetween(inicio.atStartOfDay(), fin.atTime(23, 59, 59));
    }


    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }
}
