

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.service.LoginUserService;

/**
 * ログイン画面の処理を行うコントローラークラス.
 * 
 * @author kumazawa
 *
 */
@Controller
@RequestMapping("/loginUser")
public class LoginUserController {

	@Autowired
	LoginUserService loginUserService;

	@Autowired
	private HttpSession session;

	@RequestMapping("/")
	public String index(LoginUserForm loginUserForm) {
		return "login";
	}

	@PostMapping("/login")
	public String login(String email,String password) {
		User user = loginUserService.findByEmailAndPassword(email, password);
		if (user == null) {
			//model.addAttribute("errormessage", "メールアドレス、またはパスワードが間違っています");
			return "login";
		}
		//session.setAttribute("user", user);
		return "redirect:/";
	}

}
