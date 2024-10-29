package uisrael.com.ms_auth_service.service;

import uisrael.com.ms_auth_service.dto.TokenDto;
import uisrael.com.ms_auth_service.dto.UsuarioDto;

public interface AuthService {
    TokenDto login(UsuarioDto usuarioDto);
    TokenDto validate(String token);
}
