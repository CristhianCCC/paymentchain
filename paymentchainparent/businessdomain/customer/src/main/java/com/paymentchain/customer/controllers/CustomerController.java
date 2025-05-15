package com.paymentchain.customer.controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.entities.CustomerProduct;
import com.paymentchain.customer.repositories.CustomerRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.netty.http.client.HttpClient;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/customer")
public class CustomerController {

@Autowired
private CustomerRepository customerRepository;

//se inyecta webclientbuilder desde la instancia de la clase main como un balanceador de carga -------------------------
@Autowired
private WebClient.Builder webClientBuilder;

//ya que se esta definiendo la instancia en la clase main no hay necesidad de tener este bloque de codigo aqui
    /* private final WebClient.Builder webClientBuilder;

    public CustomerController(WebClient.Builder webClientBuilder, Environment env) {
        this.webClientBuilder = webClientBuilder;
        this.env = env;
    }*/

    //se crea el cliente http
    HttpClient client = HttpClient.create()
            //tiempo de espera maximo de 5 segundos para que la conexion se establezca
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            //define la que la conexion este activa y no se cierre
            .option(ChannelOption.SO_KEEPALIVE, true)
            //define el timpo que el aconexion puede estar inactiva antes de enviar paquetes a KEEPALIVE
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            //define el intervalo entre paquetes de KEEPALIVE cuando no hay trafico
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            //establece un tiempo de espera maximo de 1 segundo para recibir una respuesta
            .responseTimeout(Duration.ofSeconds(1))
            //configuracion de manejadores de tiempo de espera
            .doOnConnected(connection -> {
                //cierra la conexion si no se reciben datos en 5 segundos
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                //cierra la conexion si no se pueden enviar datos en 5 segundos
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });



    //inyectando y usando Environment para mostrar si se esta mostrando el ambiente de desarrollador o local
    @Autowired
    private Environment env;

    @GetMapping("/check")
public String profileName (){
    return "El perfil activo es : " + env.getProperty ("custom.activeprofileName");
}



    @GetMapping
public List<Customer> findAll(){
    return customerRepository.findAll();
}

@GetMapping("/{id}")
public ResponseEntity<Customer> findCustomer(@PathVariable("id") Long id) { //("id") se hace asi para que swagger la pueda leer
    Optional<Customer> customerOptional = customerRepository.findById(id);
    return customerOptional.map(customer -> ResponseEntity.ok().body(customer)).orElseGet(() -> ResponseEntity.notFound().build());
}


@PostMapping
    public ResponseEntity<?> postCustomer (@RequestBody Customer customer){
    customer.getProducts().forEach(customerProduct -> customerProduct.setCustomer(customer)); // to add customer id to products that have relation with
    Customer customerCreated = customerRepository.save(customer);
    return ResponseEntity.ok(customerCreated);
}

@PutMapping("/{id}")
public ResponseEntity<Customer> putCustomer (@PathVariable("id") Long id, @RequestBody Customer customer){
    Optional<Customer> customerOptional = customerRepository.findById(id);
    if (customerOptional.isPresent()){
        Customer customerEdited = customerOptional.get();
        customerEdited.setNombre(customer.getNombre());
        customerEdited.setPhone(customer.getPhone());
        customerEdited.setAddress(customer.getAddress());
        customerEdited.setCode(customer.getCode());
        customerEdited.setIban(customer.getIban());
        customerEdited.setTransactions(customer.getTransactions());
        customerRepository.save(customerEdited);
        return ResponseEntity.ok().body(customerEdited);
    }else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/full")
    public Customer getByCode (@RequestParam(name = "code") String code){
        //buscando al cliente por codigo
   Customer customer =  customerRepository.findByCode(code);
     //obteniendo una lista de productos
     List<CustomerProduct> products =  customer.getProducts();
     //recorriendo la lista de id de productos con un foreach
     products.forEach(productId -> {
         //obteniendo al productname del metodo reactivo el cual devolvera el nombre del producto
         String productName = getProductName(productId.getProductId());
         //seteando el producto con el productname
         productId.setProductName(productName);
     });
     List<?> transactions = getTransactionIban(customer.getIban());
     customer.setTransactions(transactions);
     return customer;
    }



    //creando al metodo que utilizara al webclient y llamara al microservicio de products
    //se toma el id del producto y se devuelve el nombre
    private String getProductName(Long id) {
        //creacion de la instancia antes de crearla y usando ReactorNetty(client) como el constructor HTTP
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                //definicion de la url donde esta el servicio de product
                //con la implementacion de netflix eureka ya no se define por medio de localhost sino por el nombre del microservicio
                .baseUrl("http://BUSINESSDOMAIN-PRODUCT/product")
                //indicando que se enviaran y recibiran datos en formato json
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                //definiendo una variable que alamacenara la url
                .defaultUriVariables(Collections.singletonMap("url", "http://BUSINESSDOMAIN-PRODUCT/product"))
                //construccion de la instancia webclient
                .build();
        //indicando que el metodo HTTP SERA GET y concatenando el id de producto con la url, por elemplo http://localhost:8080/product/{id}
        JsonNode block =   build.method(HttpMethod.GET).uri("/" + id)
                //Envía la solicitud al servidor y espera la respuesta
                //.bodyToMono(JsonNode.class) Convierte el cuerpo de la respuesta en un Mono<JsonNode>.
                .retrieve().bodyToMono(JsonNode.class).block();
        //Bloquea el hilo actual hasta que se reciba la respuesta del servidor. se suele evitar el block ya que afecta el rendimiento de la app
        //convirtiendo el formato json a text
        String name = block.get("name").asText();
        return name;
    }


    //a diferencia del metodo que comunica a customer con producto, este metodo no esta guardando esa informacion
    //por lo tanto, no se realiza la iteracion como en el otro metodo
    private List<?> getTransactionIban(String iban){
        WebClient builder = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://BUSINESSDOMAIN-TRANSACTION/transaction")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        //obteniendo el listado de transacciones
        List<?> transactions = builder.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                //el path debe ser el mismo que esta definido en el controller de transactions
                .path("/customer/transactions")
                //el parametro debe ser el mismo que esta definido en el controler de transactions
                .queryParam("ibanAccount", iban)
                .build())
                .retrieve().bodyToFlux(Object.class).collectList().block();
        return transactions;
    }
}
