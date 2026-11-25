package cl.voltech.inventario;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import cl.voltech.inventario.config.AppJwtConfig;
import cl.voltech.inventario.config.SecurityConfig;
import cl.voltech.inventario.controller.CategoriaController;
import cl.voltech.inventario.controller.ProductoController;
import cl.voltech.inventario.service.CategoriaService;
import cl.voltech.inventario.service.ProductoService;

@WebMvcTest({ProductoController.class, CategoriaController.class})
@Import({SecurityConfig.class, AppJwtConfig.class})
@TestPropertySource(properties = "app.jwt.secret=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=")
class ProductoControllerSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecretKey appJwtKey;

    @MockitoBean
    private ProductoService productoServ;

    @MockitoBean
    private CategoriaService categoriaServ;

    @Test
    void productosYCategoriasSonPublicos() throws Exception {
        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarRequiereAdministrador() throws Exception {
        mockMvc.perform(delete("/api/v1/productos/7"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(delete("/api/v1/productos/7")
                        .header("Authorization", "Bearer " + token("CLIENTE")))
                .andExpect(status().isForbidden());
        mockMvc.perform(delete("/api/v1/productos/7")
                        .header("Authorization", "Bearer " + token("ADMINISTRADOR")))
                .andExpect(status().isNoContent());
        verify(productoServ).eliminarProducto(7);
    }

    @Test
    void crearYActualizarRequierenAdministrador() throws Exception {
        String producto = """
                {"nombre":"Cable","precio":1000,"stock":2,"categoriaId":1}
                """;

        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(producto))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/api/v1/productos")
                        .header("Authorization", "Bearer " + token("CLIENTE"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(producto))
                .andExpect(status().isForbidden());
        mockMvc.perform(post("/api/v1/productos")
                        .header("Authorization", "Bearer " + token("ADMINISTRADOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(producto))
                .andExpect(status().isCreated());
        mockMvc.perform(put("/api/v1/productos/7")
                        .header("Authorization", "Bearer " + token("ADMINISTRADOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(producto))
                .andExpect(status().isOk());
    }

    private String token(String rol) {
        Instant ahora = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("voltech-usuarios")
                .subject("admin@voltech.cl")
                .issuedAt(ahora)
                .expiresAt(ahora.plusSeconds(3600))
                .claim("rol", rol)
                .build();
        JwtEncoder encoder = NimbusJwtEncoder.withSecretKey(appJwtKey)
                .algorithm(MacAlgorithm.HS256)
                .build();
        return encoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims))
                .getTokenValue();
    }
}
