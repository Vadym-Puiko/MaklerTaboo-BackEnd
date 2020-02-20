package makler.lv480.java;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("hello")
public class HelloServlet {

    @GetMapping
    public String getAll(){
        return "hello";
    }
}
