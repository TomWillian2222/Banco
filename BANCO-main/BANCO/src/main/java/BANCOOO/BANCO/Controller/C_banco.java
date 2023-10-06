package BANCOOO.BANCO.Controller;

import BANCOOO.BANCO.Model.M_banco;
import BANCOOO.BANCO.Service.S_banco;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class C_banco {

    @GetMapping("/")
    public String helloWorld() {
        return "Login/index";
    }

    @PostMapping("/")
    public String postLogin(@RequestParam("usuario") String usuario,
                            @RequestParam("senha") String senha,
                            HttpSession session,
                            RedirectAttributes redirectAttributes){
        M_banco pessoa = S_banco.getPessoaLogin(usuario,senha);
        session.setAttribute("usuario", pessoa);
        if(session.getAttribute("usuario") == null){
            return "Login/login";
        }else{
            redirectAttributes.addFlashAttribute("nome",pessoa.getNome());
            return "/home";
        }
    }

    @GetMapping("/cadastro")
    public String getCadastro(){
        return "Cadastro/cadastro";
    }

    @PostMapping("/cadastro")
    public M_banco postLogin(@RequestParam("id") int id,
                             @RequestParam("nome") String nome,
                             @RequestParam("data_nascimento") String data_nascimento,
                             @RequestParam("CPF") String CPF,
                             @RequestParam("RG") long RG,
                             @RequestParam("numero_celular")  String numero_celular,
                             @RequestParam("email") String email,
                             @RequestParam("CEP") int CEP,
                             @RequestParam("cidade") String cidade,
                             @RequestParam("estado")  String estado,
                             @RequestParam("senha") String senha,
                             @RequestParam("confirmar_senha") String confirmar_senha
                            ){

        return S_banco.cadastrarPessoa(id,nome, data_nascimento, CPF, RG,numero_celular,email,CEP,cidade, Long.parseLong(estado),senha,confirmar_senha);
    }

    @GetMapping("/inicio")
    public String getHome(@ModelAttribute("usuario") String usuario){
        if (usuario != null) {
            // A sessão existe, redirecionar para a página home
            return "Home/inicio";
        } else {
            // A sessão não existe, redirecionar para a página de login
            return "redirect:/";
        }
    }
}

