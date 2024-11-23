package com.galeria.estilo.controller;

// import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;


import com.galeria.estilo.model.Categoria;
import com.galeria.estilo.model.Tienda;
import com.galeria.estilo.service.CategoriaService;
import com.galeria.estilo.service.TiendaService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;


import com.galeria.estilo.model.Producto;
// import com.galeria.estilo.model.Usuario;
import com.galeria.estilo.service.ProductoService;
//import com.galeria.estilo.service.UploadFileService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER=LoggerFactory.getLogger(ProductoController.class);


	@Autowired
	private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private TiendaService tiendaService;
//	@Autowired
//	private UploadFileService upload;
    //para alamcenar os detalles de la orden
	

	@GetMapping
	public String show(HttpServletRequest request, Model model) {
		model.addAttribute("productos", productoService.getProductos());

		String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);
		return "productos/show";
	}

	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	@PostMapping("/save")
	public String save(Producto producto)  {
		LOGGER.info("Este es el obj prod de vista{}", producto);
        Optional<Categoria> categoria = categoriaService.getCategoria(1);
        Optional<Tienda> tienda= tiendaService.getTienda(1);
		producto.setTienda(tienda.get());
        producto.setCategoria(categoria.get());
        producto.setMarca("Global");
        LOGGER.info("Este es el obj con tienda y cat{}", producto);

		productoService.save(producto);
		return "redirect:/productos";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model){
		Optional<Producto> producto = productoService.getProducto(id);
		model.addAttribute("producto", producto.get());
		LOGGER.info("Producto buscado: {}", producto);

		return "productos/edit";
	}
	@PostMapping("/update")
	public String update(Producto producto){

		Optional<Categoria> categoria = categoriaService.getCategoria(1);
		Optional<Tienda> tienda= tiendaService.getTienda(1);
		producto.setTienda(tienda.get());
		producto.setCategoria(categoria.get());
		producto.setMarca("Global2");
		productoService.save(producto);
		return "redirect:/productos";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {

		productoService.deleteById(id);

		return "redirect:/productos";
	}
	@GetMapping("/export")
	public ResponseEntity<InputStreamResource> exportProductos() throws IOException {
		ByteArrayInputStream stream = productoService.exportProductos();
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=productos.xls");
	
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}
 


}


