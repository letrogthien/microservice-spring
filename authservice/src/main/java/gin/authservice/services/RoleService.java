package gin.authservice.services;

import gin.authservice.models.Role;
import gin.authservice.repositories.RoleRepository;
import gin.authservice.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> getByName(String name){
        return roleRepository.findByName(name);
    }
}
