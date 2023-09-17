package it.uniroma3.siwcatalog.controller;
import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.repository.FornitoreRepository;
import it.uniroma3.siwcatalog.repository.ProdottoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;






@Controller
public class SearchController {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private FornitoreRepository fornitoreRepository;

    @PostMapping("/cercaProdottoFornitore")
    public String getMethodName(@RequestParam(name = "cerca", required = false) String cerca, Model model) {
        model.addAttribute("cerca", cerca);
        model.addAttribute("prodotti", this.prodottoRepository.findByNomeProdotto(cerca));
        model.addAttribute("fornitori", this.fornitoreRepository.findByNomeFornitore(cerca));
        return "cercaProdottiFornitori.html";
    }
    
    
}
