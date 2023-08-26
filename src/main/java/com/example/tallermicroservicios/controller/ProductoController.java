package com.example.tallermicroservicios.controller;

import com.example.tallermicroservicios.controller.response.producto.MessageResponse;
import com.example.tallermicroservicios.domain.Producto;
import com.example.tallermicroservicios.domain.filter.ProductoFilter;
import com.example.tallermicroservicios.service.ProductoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value = "/productos")
public class ProductoController {
    @Value("${app.storage.path}")
    private String STORAGEPATH;
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    @GetMapping
    public List<Producto> getAllProducts(){
        return productoService.findAll();
    }
    @GetMapping(value = "/producto")
    public Producto findProductoById(ProductoFilter productoFilter){
        return productoService.findById(productoFilter.getId());
    }
    /*
    @GetMapping
    public Producto findProductoById(@RequestParam(name="id", required = false, defaultValue = "") Long id){
        return productoService.findById(id);
    }
     */
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception {
        Path path = Paths.get(STORAGEPATH).resolve(filename);
        if(!Files.exists(path)){
            return ResponseEntity.notFound().build();
        }
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+resource.getFilename()+"\"")
                .header(HttpHeaders.CONTENT_TYPE,
                        Files.probeContentType(Paths.get(STORAGEPATH).resolve(filename)))
                .header(HttpHeaders.CONTENT_LENGTH,
                        String.valueOf(resource.contentLength()))
                .body(resource);

    }
    @PostMapping
    public Producto createProduct(@RequestParam(name="imagen", required=false) MultipartFile imagen,
                                  @RequestParam("nombre") String nombre, @RequestParam("precio") Double precio,
                                  @RequestParam("detalles") String detalles) throws Exception{
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDetalles(detalles);
        producto.setPrecio(precio);
        producto.setEstado("1");
        if(imagen != null && !imagen.isEmpty()){
            String filename = System.currentTimeMillis() +
                    imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
            producto.setImagen(filename);
            if(Files.notExists(Paths.get(STORAGEPATH))){
                Files.createDirectories(Paths.get(STORAGEPATH));
            }
            Files.copy(imagen.getInputStream(), Paths.get(STORAGEPATH).resolve(filename));

        }
        productoService.save(producto);
        System.gc();
        return producto;
    }
    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteProduct(ProductoFilter productoFilter){
        productoService.deleteById(productoFilter.getId());
        return ResponseEntity.ok().body(new MessageResponse("ok", HttpStatus.OK));
    }

}
