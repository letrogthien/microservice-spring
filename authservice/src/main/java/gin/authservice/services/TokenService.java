package gin.authservice.services;



import gin.authservice.models.QToken;
import gin.authservice.models.Token;
import gin.authservice.models.User;
import gin.authservice.repositories.TokenRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void saveToken(Token token){
        tokenRepository.save(token);
    }

    public List<Token> getAllTokenOfUser(User user){
        QToken qToken = QToken.token;
        BooleanExpression booleanExpression = qToken.user.eq(user)
                .and(qToken.expired.isFalse())
                .and(qToken.revoked.isFalse());
        return (List<Token>) tokenRepository.findAll(booleanExpression);
    }

    public Optional<Token> getToken(String tok){
        return tokenRepository.findByJwt(tok);
    }
}
