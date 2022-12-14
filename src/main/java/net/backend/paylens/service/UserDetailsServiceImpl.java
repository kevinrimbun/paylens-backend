package net.backend.paylens.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.backend.paylens.model.entity.User;
import net.backend.paylens.model.entity.UserRole;
import net.backend.paylens.repository.UserRepository;
import net.backend.paylens.repository.UserRoleRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      // TODO Auto-generated method stub
      Optional<User> userOpt = userRepository.findByEmail(email);
      if (userOpt.isEmpty()) {
        throw new UsernameNotFoundException("User not found");
      }
  
      User user = userOpt.get();
      UserRole userRole = userRoleRepository.findByUser(user);
      String role = userRole.getRole().getRoleName().name();
  
      return UserDetailsImpl.build(user, role);
  }
}
