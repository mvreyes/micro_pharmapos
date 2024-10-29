package uisrael.com.ms_auth_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uisrael.com.ms_auth_service.dto.TokenDto;
import uisrael.com.ms_auth_service.dto.UsuarioDto;
import uisrael.com.ms_auth_service.model.AuthUser;
import uisrael.com.ms_auth_service.security.JwtProvider;
import uisrael.com.ms_auth_service.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public TokenDto login(UsuarioDto usuarioDto) {
        AuthUser authUserdb = restTemplate.getForObject("http://localhost:8001/usuarios/buscar/"+usuarioDto.getEmail(), AuthUser.class);
        if (authUserdb == null){
            return null;
        }
        if (passwordEncoder.matches(usuarioDto.getPassword(), authUserdb.getPassword())){
            return new TokenDto(jwtProvider.createToken(authUserdb));
        }
        return null;
    }

    @Override
    public TokenDto validate(String token) {
        if (!jwtProvider.validate(token)){
            return null;
        }

        String email = jwtProvider.getUserEmailFromToken(token);
        AuthUser authUserdb = restTemplate.getForObject("http://localhost:8001/usuarios/buscar/"+email, AuthUser.class);
        if(authUserdb == null){
            return null;
        }
        return new TokenDto(token);
    }
}
