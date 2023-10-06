package BANCOOO.BANCO.Service;


import BANCOOO.BANCO.Model.M_banco;
import BANCOOO.BANCO.Repository.R_banco;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class S_banco {

    private static R_banco r_banco;

    public S_banco(R_banco r_banco){

        this.r_banco = r_banco;
    }


    public static M_banco getPessoaLogin(String usuario, String senha){
        usuario = NumberCleaner.cleanNumber(usuario);
        if(usuario.equals("")){
            return r_banco.findByUsuarioESenha(null,senha);
        }else{
            return r_banco.findByUsuarioESenha(Long.valueOf(usuario),senha);
        }
    }
    public static M_banco cadastrarPessoa(long id, String nome, String email, String CPF,long RG,String cidade, String estado, long CEP,
                                             String numero_celular, long data_nascimento,
                                             String senha, String confirmar_senha){
        boolean cadastroValido = true;
        String mensagemRetorno = "";
        numero_celular = NumberCleaner.cleanNumber(numero_celular);

        if(numero_celular.equals("")){
            numero_celular = (null);
        }

        if(!senha.equals(confirmar_senha)) {
            mensagemRetorno += "A Senha e a Confirmação de Senha devem ser iguais<br/>";
            cadastroValido = false;
        }
        if(!CpfValidator.validateCPF(CPF)){
            mensagemRetorno += "CPF Inválido<br/>";
            cadastroValido = false;
        }
        if(nome == null || nome.trim() == ""){
            mensagemRetorno += "Deve ser informado o Nome<br/>";
            cadastroValido = false;
        }
        if ((email == null || email.trim() == "")
                && ( numero_celular== null)){
            mensagemRetorno += "e-Mail ou Telefone precisa ser informado<br/>";
            cadastroValido = false;
        }
        if(cadastroValido){
            M_banco m_banco = new M_banco();
            m_banco.setNome(nome);
            m_banco.setCPF(NumberCleaner.cleanNumber(CPF));
            if(numero_celular != null)    {
                m_banco.setNumero_celular(Long.valueOf(numero_celular));
            }else{
                m_banco.setNumero_celular(null);
            }
            m_banco.setEmail(email);
            m_banco.getData_nacimento(data_nascimento);
            m_banco.setSenha(senha);
            try{
                r_banco.save(m_banco);
                mensagemRetorno += "Cadastro efetuado com sucesso";
            }catch (DataIntegrityViolationException e){
                if(e.getMessage().contains("u_cpf")){
                    mensagemRetorno += "O CPF informado já foi cadastrado!";
                }else{
                    mensagemRetorno += "Erro ao cadastrar";
                }
                cadastroValido = false;
            }
        }
        M_banco m_resposta = new M_banco();
        return m_resposta;
    }

}



