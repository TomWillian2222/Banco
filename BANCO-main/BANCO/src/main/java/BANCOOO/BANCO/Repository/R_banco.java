package BANCOOO.BANCO.Repository;
import BANCOOO.BANCO.Model.M_banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface R_banco extends JpaRepository<M_banco, Long> {
    @Query(value = "SELECT * FROM pessoa WHERE cpf = :nome and senha = :senha limit 1", nativeQuery = true)
    M_banco findByUsuarioESenha(@Param("nome") Long usuario, @Param("senha") String senha);
}
