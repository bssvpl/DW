package com.galeria.estilo.service;

import com.galeria.estilo.model.Producto;
import com.galeria.estilo.repository.IProducto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private IProducto IProducto;

    
    public List<Producto> getProductos() {
        return IProducto.findAll();
    }

    public Optional<Producto> getProducto(Integer id) {
        return IProducto.findById(id);
    }

    public void save(Producto producto) {
        IProducto.save(producto);
    }

    public void deleteById(Integer id) {
        IProducto.deleteById(id);
    }

    public ByteArrayInputStream exportProductos() {
        String[] titulos= {"NOMBRE","DESCRIPCION","PRECIO","TALLA","COLOR"};

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Productos");
        Row row = sheet.createRow(0);

        for (int i = 0; i < titulos.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titulos[i]);
        }

        List<Producto> productos = IProducto.findAll();
        int initRow = 1;
        for (Producto producto : productos) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(producto.getNombre());
            row.createCell(1).setCellValue(producto.getDescripcion());
            row.createCell(2).setCellValue(producto.getPrecio());
            row.createCell(3).setCellValue(producto.getTalla());
            row.createCell(4).setCellValue(producto.getColor());
            initRow++;
        }

        try {
            workbook.write(stream);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayInputStream(stream.toByteArray());
    }

    public Optional<Producto> get(Integer id) {
        
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

}
