package gin.authservice.repositories;

import gin.authservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long>, QuerydslPredicateExecutor<Token> {
    Optional<Token> findByJwt(String tok);
}
